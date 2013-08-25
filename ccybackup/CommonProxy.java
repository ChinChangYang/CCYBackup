package ccybackup;

public class CommonProxy {
	
	private int backupInterval;
	
	private String backupRoot;

	public void startBackupThread() {
		// Nothing here as the client doesn't save the world.
	}

	public int getBackupInterval() {
		return backupInterval;
	}

	public void setBackupInterval(int backupInterval) {
		this.backupInterval = backupInterval;
	}

	public String getBackupRoot() {
		return backupRoot;
	}

	public void setBackupRoot(String backupRoot) {
		this.backupRoot = backupRoot;
	}
}
