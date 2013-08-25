package ccybackup;

public class CommonProxy {
	
	private boolean backupEnabled;
	
	private int backupInterval;
	
	private String backupRoot;

	public void startBackupThread() {
		// Nothing here as the client doesn't save the world.
	}

	public boolean isBackupEnabled() {
		return backupEnabled;
	}

	public void setBackupEnabled(boolean backupEnabled) {
		this.backupEnabled = backupEnabled;
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
