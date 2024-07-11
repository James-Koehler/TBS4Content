package me.jameskoehler.blocks.customBlocks.blockEntities;

import me.jameskoehler.enchantments.EnchantmentIds;
import me.jameskoehler.util.IEntityDataSaver;
import me.jameskoehler.util.RadiationData;
import me.jameskoehler.TBS4Content;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CompressedRedstoneBlockEntity extends BlockEntity {

    public CompressedRedstoneBlockEntity(BlockPos pos, BlockState state) {
        super(TBS4Content.COMPRESSED_REDSTONE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CompressedRedstoneBlockEntity entity) {
        RegistryEntry<Enchantment> radiationResistance = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(EnchantmentIds.RADIATION_RESISTANCE);
        if (world.isClient()) {
            return;
        }
        double range = 20.0;
        Vec3d origin = blockPos.toCenterPos();
        ServerWorld serverWorld = (ServerWorld) world;
        List<? extends PlayerEntity> list = serverWorld.getPlayers(
                player -> origin.isInRange(player.getPos(), range) &&
                        player.interactionManager.isSurvivalLike()
        );
        float exposureAmt = 1.0f;

        for (PlayerEntity player : list) {

            List<ItemStack> armor = player.getInventory().armor;

            ItemStack boots = armor.get(0);
            ItemStack leggings = armor.get(1);
            ItemStack chestplate = armor.get(2);
            ItemStack helmet = armor.get(3);
            int counter = 0;
            if (helmet.isOf(TBS4Content.LEAD_HELMET) || EnchantmentHelper.getLevel(radiationResistance, helmet) != 0) {
                counter++;
            }
            if (chestplate.isOf(TBS4Content.LEAD_CHESTPLATE) || EnchantmentHelper.getLevel(radiationResistance, chestplate) != 0) {
                counter++;
            }
            if (leggings.isOf(TBS4Content.LEAD_LEGGINGS) || EnchantmentHelper.getLevel(radiationResistance, leggings) != 0) {
                counter++;
            }
            if (boots.isOf(TBS4Content.LEAD_BOOTS) || EnchantmentHelper.getLevel(radiationResistance, boots) != 0) {
                counter++;
            }
            exposureAmt = counter == 0 ? exposureAmt : counter == 4 ? 0 : exposureAmt / (25 * counter); // hehe gross nested ternary operators >:)
            RadiationData.addExposure((IEntityDataSaver) player, exposureAmt);
            int exposure = ((IEntityDataSaver) player).getPersistentData().getInt("exposure");
            int duration = (30 + (10 * (exposure - 1000) / 50)) * 20;
            if (exposure >= 1000) {
                player.addStatusEffect(new StatusEffectInstance(TBS4Content.RADIATION_POISONING, duration, 0, false, false));
            }
            player.sendMessage(Text.literal("Exposure gained: " + exposureAmt));
        }
    }
}
