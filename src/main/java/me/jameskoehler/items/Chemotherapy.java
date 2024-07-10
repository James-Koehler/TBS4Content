package me.jameskoehler.items;

import me.jameskoehler.TBS4Content;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Chemotherapy extends Item {
    public Chemotherapy(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.removeStatusEffect(TBS4Content.LEUKEMIA);
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        return super.use(world, user, hand);
    }
}
