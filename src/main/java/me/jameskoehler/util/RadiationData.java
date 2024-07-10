package me.jameskoehler.util;

import net.minecraft.nbt.NbtCompound;

public class RadiationData {
    private static final int MAX_EXPOSURE = 3000;

    public static float addExposure(IEntityDataSaver player, float amount) {
        NbtCompound nbt = player.getPersistentData();
        float exposure = nbt.getFloat("exposure");
        if (exposure + amount >= MAX_EXPOSURE) {
            exposure = MAX_EXPOSURE;
        } else {
            exposure += amount;
        }
        nbt.putFloat("exposure", exposure);
        //sync the data
        return exposure;
    }

    public static float removeExposure(IEntityDataSaver player, float amount) {
        NbtCompound nbt = player.getPersistentData();
        float exposure = nbt.getFloat("exposure");
        if(exposure - amount < 0) {
            exposure = 0;
        } else {
            exposure -= amount;
        }

        nbt.putFloat("exposure", exposure);
        // syncexposure(exposure, (ServerPlayerEntity) player);
        return exposure;
    }

    public static void setExposure(IEntityDataSaver player, float amount) {
        NbtCompound nbt = player.getPersistentData();
        float exposure = nbt.getInt("exposure");
        nbt.putFloat("exposure", amount);

    }
}
