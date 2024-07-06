package me.jameskoehler.screen;

import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<ImbuerScreenHandler> IMBUER_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){

        IMBUER_SCREEN_HANDLER = new ScreenHandlerType<>(ImbuerScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }
}
