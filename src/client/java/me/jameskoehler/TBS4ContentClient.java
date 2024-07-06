package me.jameskoehler;

import me.jameskoehler.screen.ImbuerScreen;
import me.jameskoehler.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class TBS4ContentClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HandledScreens.register(ModScreenHandlers.IMBUER_SCREEN_HANDLER, ImbuerScreen::new);
	}
}