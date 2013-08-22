package com.github.chinchangyang;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "CCYBackup", name = "CCYBackup", version = "0.0.1")
@NetworkMod(clientSideRequired = false, serverSideRequired = true)
public class CCYBackup {

	// The instance of your mod that Forge uses.
	@Instance("CCYBackup")
	public static CCYBackup instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "com.github.chinchangyang.CommonProxy", serverSide = "com.github.chinchangyang.server.ServerProxy")
	public static CommonProxy proxy;

	private int backupInterval;

	private String backupRoot;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		config.load();

		backupInterval = config.get(Configuration.CATEGORY_GENERAL,
				"BackupInterval", 15 * 60).getInt();

		backupRoot = config.get(Configuration.CATEGORY_GENERAL, "BackupRoot",
				"CCYBackup").getString();

		config.save();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.setBackupInterval(backupInterval);
		proxy.setBackupRoot(backupRoot);
		proxy.startBackupThread();
	}
}
