package me.jameskoehler.event;

import me.jameskoehler.util.IEntityDataSaver;
import me.jameskoehler.util.RadiationData;
import me.jameskoehler.TBS4Content;
import me.jameskoehler.damagetypes.ModDamageTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
            if (new Random().nextFloat() <= 0.005f) {
                RadiationData.removeExposure(dataPlayer, 20);
            }
            if (new Random().nextFloat() <= 0.002f) {
                int currExpo = dataPlayer.getPersistentData().getInt("exposure");
                if(currExpo > 100 && !player.hasStatusEffect(TBS4Content.LEUKEMIA)) {
                    player.addStatusEffect(new StatusEffectInstance(TBS4Content.LEUKEMIA, -1, 0, false, false));

                }
            }
            if (new Random().nextFloat() <= 0.00003125f) {
                if (player.hasStatusEffect(TBS4Content.LEUKEMIA)) {
                    player.damage(ModDamageTypes.of(player.getWorld(), ModDamageTypes.LEUKEMIA_DAMAGE_TYPE), 1000);
                }
            }
        }
    }
}
