package me.jameskoehler.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ImbuerRecipe implements Recipe<RecipeInput> {

    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public ImbuerRecipe(List<Ingredient> ingredients, ItemStack itemStack){

        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(RecipeInput input, World world) {

        if(world.isClient()) return false;

        return recipeItems.get(0).test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {

        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);

        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ImbuerRecipe>{

        public static final Type INSTANCE = new Type();
        public static final String ID = "imbuer_recipe";
    }

    public static class Serializer implements RecipeSerializer<ImbuerRecipe>{

        public static final Codec<ImbuerRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISSALOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(ImbuerRecipe::getIngredients),
                RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, ImbuerRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max){
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)

            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public MapCodec<ImbuerRecipe> codec() {
            return null;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ImbuerRecipe> packetCodec() {
            return null;
        }
    }
}
