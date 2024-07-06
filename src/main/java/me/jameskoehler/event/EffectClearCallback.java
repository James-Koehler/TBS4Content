package me.jameskoehler.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.ActionResult;

public interface EffectClearCallback {
    Event<EffectClearCallback> EFFECT_CLEAR_EVENT = EventFactory.createArrayBacked(EffectClearCallback.class, (listeners) -> (context) -> {
        for (EffectClearCallback listener : listeners) {
            ActionResult result = listener.onEffectClear(context);
            if (result != ActionResult.PASS)
                return result;
        }
        return ActionResult.PASS;
    });
    ActionResult onEffectClear(Context context);
    record Context(LivingEntity affected, boolean clearingWithMilk, StatusEffectInstance effectInstance) {
        @Override
        public LivingEntity affected() {
            return affected;
        }
        public boolean withMilk() {
            return clearingWithMilk;
        }
        @Override
        public StatusEffectInstance effectInstance() {
            return effectInstance;
        }
    }
}
