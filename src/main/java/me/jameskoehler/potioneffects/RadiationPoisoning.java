package me.jameskoehler.potioneffects;

import me.jameskoehler.damagetypes.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RadiationPoisoning extends StatusEffect {

    public RadiationPoisoning() {
        super(StatusEffectCategory.HARMFUL, 0x66e362);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }

    // functionality
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(ModDamageTypes.of(entity.getWorld(), ModDamageTypes.RADIATION_POISONING_DAMAGE_TYPE), 2);
        return super.applyUpdateEffect(entity, amplifier);
    }
}
