package me.jameskoehler.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;

import java.util.ArrayList;
import java.util.List;


public record ImbuerRecipeInput(ItemStack slotA, ItemStack slotB, ItemStack slotC) implements RecipeInput {

    public ImbuerRecipeInput(ItemStack slotA, ItemStack slotB, ItemStack slotC) {
        this.slotA = slotA;
        this.slotB = slotB;
        this.slotC = slotC;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemStack result;
        switch (slot) {
            case 0:
                result = this.slotA;
                break;
            case 1:
                result = this.slotB;
                break;
            case 2:
                result = this.slotC;
                break;
            default:
                throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }
        return result;
    }

    public boolean isEmpty() {
        return this.slotA.isEmpty() && this.slotB.isEmpty() && this.slotC.isEmpty();
    }

    public ItemStack slotA() {
        return this.slotA.copy();
    }

    public ItemStack slotB() {
        return this.slotB.copy();
    }

    public ItemStack slotC() {
        return this.slotC.copy();
    }
}
