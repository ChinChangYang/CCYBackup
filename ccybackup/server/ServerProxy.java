package ccybackup.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.logging.Logger;

import ccybackup.CCYBackup;
import ccybackup.CommonProxy;
import cpw.mods.fml.server.FMLServerHandler;

public class ServerProxy extends CommonProxy {
		
	/**
	 * Minimum sleep time for fool-proofing interval configuration
	 */
	public final static long MINIMUM_SLEEP_TIME = 10 * 1000;

	@Override
	public void startBackupThread() {
		final long currentTime = System.currentTimeMillis();
		final long interval = getBackupInterval() * 1000;

		new Thread() {
			private long startTime = currentTime;

			public void run() {
				while (true) {
					boolean shouldBackup = (System.currentTimeMillis() > startTime
							+ Math.round(0.5 * interval))
							&& (CCYBackup.playerNumber > 0 || !CCYBackup.saved);

					if (shouldBackup) {

						FMLServerHandler.instance().getServer().executeCommand("save-all");
						String backupRootName = getBackupRoot();
						File backupRoot = new File(backupRootName);

						if (!backupRoot.exists()) {
							backupRoot.mkdirs();
						}

						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						int month = c.MONTH;
						int day = c.DAY_OF_MONTH;
						int hour = c.HOUR_OF_DAY;
						int minute = c.MINUTE;
						int second = c.SECOND;

						StringBuffer backupFolderName = new StringBuffer(
								backupRootName + "/");
						backupFolderName.append(String.format(
								"%1$tY-%1$tm-%1$td-%1$tH-%1$tM-%1$tS", c));

						File backupFolder = new File(
								backupFolderName.toString());
						File srcFolder = new File("world");

						try {
							copyFolder(srcFolder, backupFolder);
						} catch (IOException e) {
							e.printStackTrace();
						}

						Logger.getLogger("CCYBackup")
								.finest("Processed backup");

						startTime = System.currentTimeMillis();
						CCYBackup.saved = true;
					}

					try {
						long sleepTime = Math.max(interval, MINIMUM_SLEEP_TIME);
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

}
