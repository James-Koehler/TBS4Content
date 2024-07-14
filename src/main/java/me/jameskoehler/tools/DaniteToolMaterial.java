package me.jameskoehler.tools;

import me.jameskoehler.TBS4Content;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class DaniteToolMaterial implements ToolMaterial {

    public static final DaniteToolMaterial INSTANCE = new DaniteToolMaterial();

    @Override
    public int getDurability() {
        return 3500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 12.0f;
    }

    @Override
    public float getAttackDamage() {
        return 5.0f;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantability() {
        return 25;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(TBS4Content.DANITE_CRYSTAL);
    }
}
