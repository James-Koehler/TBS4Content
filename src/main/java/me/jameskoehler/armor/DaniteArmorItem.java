package me.jameskoehler.armor;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

public class DaniteArmorItem extends ArmorItem {

    public DaniteArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        LoggerFactory.getLogger("tbs4content").info("{} has been equipped", user.getStackInHand(hand).toString());

        ItemStack inHand = user.getStackInHand(hand);
        Registry<Enchantment> enchantmentRegistry = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT);

        if (!inHand.isEnchantable()) {
            RegistryEntry.Reference<Enchantment> fireProtection = enchantmentRegistry.entryOf(Enchantments.FIRE_PROTECTION);
            RegistryEntry.Reference<Enchantment> explosionProtection = enchantmentRegistry.entryOf(Enchantments.BLAST_PROTECTION);

            inHand.addEnchantment(fireProtection, 5);
            inHand.addEnchantment(explosionProtection, 5);
        } else {
            LoggerFactory.getLogger("tbs4content").info("{} is not enchantable", inHand);
        }

        return super.use(world, user, hand);
    }
}
