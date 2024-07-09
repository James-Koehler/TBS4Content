package me.jameskoehler.event;

import me.jameskoehler.IEntityDataSaver;
import me.jameskoehler.RadiationData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (new Random().nextFloat() <= 0.005f) {
                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                RadiationData.removeExposure(dataPlayer, 20);
                player.sendMessage(Text.literal("exposure level: " + dataPlayer.getPersistentData()));
            }
        }
    }
}
