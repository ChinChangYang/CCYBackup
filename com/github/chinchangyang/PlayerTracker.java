package com.github.chinchangyang;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		CCYBackup.playerNumber++;
		CCYBackup.saved = false;
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		CCYBackup.playerNumber--;
		CCYBackup.saved = false;
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
