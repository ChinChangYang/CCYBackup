package ccybackup;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "CCYBackup", name = "CCYBackup", version = "0.0.4")
@NetworkMod(clientSideRequired = false, serverSideRequired = true)
public class CCYBackup {

	// The instance of your mod that Forge uses.
	@Instance("CCYBackup")
	public static CCYBackup instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "ccybackup.CommonProxy", serverSide = "ccybackup.server.ServerProxy")
	public static CommonProxy proxy;
	
	public static int playerNumber = 0;
	
	public static boolean saved = true;
	
	private boolean backupEnabled;

	private int backupInterval;

	private String backupRoot;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		config.load();

		backupEnabled = config.get(Configuration.CATEGORY_GENERAL, "BackupEnabled", true).getBoolean(true);
		
		backupInterval = config.get(Configuration.CATEGORY_GENERAL,
				"BackupInterval", 15 * 60).getInt();

		backupRoot = config.get(Configuration.CATEGORY_GENERAL, "BackupRoot",
				"CCYBackup").getString();

		config.save();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.setBackupEnabled(backupEnabled);
		proxy.setBackupInterval(backupInterval);
		proxy.setBackupRoot(backupRoot);
		proxy.startBackupThread();
	}
}
