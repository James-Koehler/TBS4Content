package me.jameskoehler.screen;

import com.sun.jna.platform.unix.X11;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<ImbuerScreenHandler> IMBUER_SCREEN_HANDLER;
    public static ScreenHandlerType<CompressorScreenHandler> COMPRESSOR_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){

        IMBUER_SCREEN_HANDLER = new ScreenHandlerType<>(ImbuerScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        COMPRESSOR_SCREEN_HANDLER = new ScreenHandlerType<>(CompressorScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }
}
