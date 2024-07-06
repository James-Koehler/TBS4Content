package me.jameskoehler.mixin;

import me.jameskoehler.IGettableMilkingPlayers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(MilkBucketItem.class)
public class MilkBucketMixin implements IGettableMilkingPlayers {
    @Unique
    Set<LivingEntity> milkingPlayers = new HashSet<>();

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void startEnd(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        milkingPlayers.add(user);
    }

    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void endEnd(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        milkingPlayers.remove(user);
    }

    @Unique
    public Set<LivingEntity> getMilkingPlayers() {
        return milkingPlayers;
    }
}
