package me.jameskoehler.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

public class Chemotherapy extends Item {
    public Chemotherapy(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        LoggerFactory.getLogger("tbs4content").info("Chemotherapy used!");
        return super.use(world, user, hand);
    }
}
