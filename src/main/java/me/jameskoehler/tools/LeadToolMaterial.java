package me.jameskoehler.tools;

import me.jameskoehler.TBS4Content;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class LeadToolMaterial implements ToolMaterial {

    public static final LeadToolMaterial INSTANCE = new LeadToolMaterial();

    @Override
    public int getDurability() {
        return 200;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 6.0f;
    }

    @Override
    public float getAttackDamage() {
        return 2.0f;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_IRON_TOOL;
    }

    @Override
    public int getEnchantability() {
        return 14;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(TBS4Content.LEAD_INGOT);
    }
}
