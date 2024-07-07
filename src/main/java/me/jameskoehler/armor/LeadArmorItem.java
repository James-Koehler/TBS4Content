package me.jameskoehler.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class LeadArmorItem extends ArmorItem {
    public LeadArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    // Figure out how the radiation system will work and possibly implement decreases from here
    // MAYBE MAKE CUSTOM ARMORITEM WITH BOOL USED BY STATUS EFFECT TO CHECK IF ARMOR INTERACTS WITH IT


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

}
