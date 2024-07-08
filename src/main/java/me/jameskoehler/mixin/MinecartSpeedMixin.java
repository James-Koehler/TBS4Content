package me.jameskoehler.mixin;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractMinecartEntity.class)
public class MinecartSpeedMixin {

    @Unique
    public boolean touchingWater;

    /**
     * @author Dawn
     * @reason Changing Minecarts' Max Speed
     */
    @Overwrite
    public double getMaxSpeed() {
        return (this.isTouchingWater() ? 4.0 : 8.0) / 15.0;
    }

    public boolean isTouchingWater() {
        return this.touchingWater;
    }
}
