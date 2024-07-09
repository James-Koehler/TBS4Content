package me.jameskoehler;

import net.minecraft.nbt.NbtCompound;

public class RadiationData {
    private static final int MAX_EXPOSURE = 3000;

    public static int addExposure(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int exposure = nbt.getInt("exposure");
        if (exposure + amount >= MAX_EXPOSURE) {
            exposure = MAX_EXPOSURE;
        } else {
            exposure += amount;
        }
        nbt.putInt("exposure", exposure);
        //sync the data
        return exposure;
    }

    public static int removeExposure(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int exposure = nbt.getInt("exposure");
        if(exposure - amount < 0) {
            exposure = 0;
        } else {
            exposure -= amount;
        }

        nbt.putInt("exposure", exposure);
        // syncexposure(exposure, (ServerPlayerEntity) player);
        return exposure;
    }

    public static void setExposure(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int exposure = nbt.getInt("exposure");
        nbt.putInt("exposure", amount);

    }
}
