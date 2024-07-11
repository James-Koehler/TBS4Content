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

            ItemStack helmet = armor.get(3);
            ItemStack chestplate = armor.get(2);
            ItemStack leggings = armor.get(1);
            ItemStack boots = armor.get(0);

            int helmetLevel = EnchantmentHelper.getLevel(radiationResistance, helmet);
            int chestplateLevel = EnchantmentHelper.getLevel(radiationResistance, chestplate);
            int leggingsLevel = EnchantmentHelper.getLevel(radiationResistance, leggings);
            int bootsLevel = EnchantmentHelper.getLevel(radiationResistance, boots);

            int counter = 0;

            if (helmetLevel == 3 || chestplateLevel == 3 || leggingsLevel == 3 || bootsLevel == 3) {
                counter = 4;
            }
            if (helmet.isOf(TBS4Content.LEAD_HELMET) || helmetLevel == 1) {
                counter+=1;
            }
            if (chestplate.isOf(TBS4Content.LEAD_CHESTPLATE) || chestplateLevel == 1) {
                counter+=1;
            }
            if (leggings.isOf(TBS4Content.LEAD_LEGGINGS) || leggingsLevel == 1) {
                counter+=1;
            }
            if (boots.isOf(TBS4Content.LEAD_BOOTS) || bootsLevel == 1) {
                counter+=1;
            }
            if (counter != 0) {
                exposureAmt = counter >= 4 ? 0 : exposureAmt / (25 * counter);
            }

            exposureAmt = counter == 0 ? exposureAmt : counter == 4 ? 0 : exposureAmt / (25 * counter);
            RadiationData.addExposure((IEntityDataSaver) player, exposureAmt);
            float exposure = ((IEntityDataSaver) player).getPersistentData().getFloat("exposure");
            int duration = Float.floatToIntBits((30 + (10 * (exposure - 1000) / 50)) * 20);

            if (exposure >= 1000) {
                player.addStatusEffect(new StatusEffectInstance(TBS4Content.RADIATION_POISONING, duration, 0, false, false));
            }
        }
    }
}
