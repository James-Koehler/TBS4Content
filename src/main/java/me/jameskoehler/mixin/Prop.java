package me.jameskoehler.mixin;

import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Properties.class)
public class Prop {
    @Shadow
    public static final IntProperty POWER = IntProperty.of("power", 0, 25);
}
