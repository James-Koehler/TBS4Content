package me.jameskoehler.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.jameskoehler.IGettableMilkingPlayers;
import me.jameskoehler.TBS4Content;
import me.jameskoehler.event.EffectClearCallback;
import me.jameskoehler.potioneffects.Leukemia;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(LivingEntity.class)
@Debug(export = true)
public class LivingEntityMixin {

    @Unique
    Boolean shouldClearNextEffect = null;

    @WrapWithCondition(method = "clearStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onStatusEffectRemoved(Lnet/minecraft/entity/effect/StatusEffectInstance;)V"))
    public boolean shouldClearEffect(LivingEntity instance, StatusEffectInstance effect) {
        ActionResult res = EffectClearCallback.EFFECT_CLEAR_EVENT.invoker().onEffectClear(new EffectClearCallback.Context((LivingEntity) (Object) this, ((IGettableMilkingPlayers)Items.MILK_BUCKET).getMilkingPlayers().contains(instance), effect));
        if (res == ActionResult.FAIL) {
            shouldClearNextEffect = false;
            return false;
        }
        // This works for keeping Leukemia and Radiation Poisoning from being removed by milk, but also by effect clear I do not think this is the correct way to implement this behaviour...
        // but as it stands these mixin files are from a Thread on the fabric Discord that has not been completed and was started Yesterday (7/5/24) I will keep my eye on it to see what conclusion they come to
        if (effect.getEffectType().value().equals(TBS4Content.LEUKEMIA.value()) && instance.isHolding(Items.MILK_BUCKET)) {
            shouldClearNextEffect = false;
            return false;
        }
        if (effect.getEffectType().value().equals(TBS4Content.RADIATION_POISONING.value()) && instance.isHolding(Items.MILK_BUCKET)) {
            shouldClearNextEffect = false;
            return false;
        }
        shouldClearNextEffect = true;
        return true;
    }

    @WrapWithCondition(method = "clearStatusEffects", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
    public boolean shouldRemove(Iterator<StatusEffectInstance> instance) {
        return shouldClearNextEffect;
    }

    @Inject(method = "clearStatusEffects", at = @At("RETURN"))
    public void reset(CallbackInfoReturnable<Boolean> cir) {
        shouldClearNextEffect = null;
    }
}
