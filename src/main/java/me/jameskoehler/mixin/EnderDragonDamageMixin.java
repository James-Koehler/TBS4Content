package me.jameskoehler.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(EnderDragonEntity.class)
public class EnderDragonDamageMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "damageLivingEntities")
    private boolean dmg(Entity instance, DamageSource source, float amount) {
        return instance.damage(source, 12.0F);
    }
}
