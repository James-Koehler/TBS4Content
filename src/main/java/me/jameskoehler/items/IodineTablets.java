package me.jameskoehler.items;

import me.jameskoehler.util.IEntityDataSaver;
import me.jameskoehler.util.RadiationData;
import me.jameskoehler.TBS4Content;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

public class IodineTablets extends Item {
    public IodineTablets(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.removeStatusEffect(TBS4Content.RADIATION_POISONING);
        RadiationData.setExposure((IEntityDataSaver) user, 0);
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        return super.use(world, user, hand);
    }
}
