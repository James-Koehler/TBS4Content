package me.jameskoehler.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.WitherSkullEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkullEntity.class)
public class WitherDamageMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "onEntityHit")
    private boolean damageGuy(Entity instance, DamageSource source, float amount) {
        return instance.damage(source, 9.2F);
    }
}
