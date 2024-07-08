package me.jameskoehler.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ImbueRecipe implements Recipe<ImbuerRecipeInput> {

    private final int mCookingTime;
    private final int mExperience;
    private final List<Ingredient> mRecipeItems;
    private final ItemStack mOutput;

    public enum ImbueRecipeAttributes {
        INGREDIENTS("ingredients"),
        COOKINGTIME("cookingtime"),
        EXPERIENCE("experience"),
        RESULT("result");

        public String value;
        private ImbueRecipeAttributes(String value) {
            this.value = value;
        }
    }

    public static final int mNumberOfInputs = 3;

    public ImbueRecipe(int cookingTime, int experience, List<Ingredient> recipeItems, ItemStack output) {
        mCookingTime = cookingTime;
        mExperience = experience;
        mRecipeItems = recipeItems;
        mOutput = output;
    }

    @Override
    public boolean matches(ImbuerRecipeInput input, World world) {
        if (world.isClient()) { return false; }

        boolean matchA = mRecipeItems.get(0).test(input.getStackInSlot(0)) &&
                         mRecipeItems.get(1).test(input.getStackInSlot(1));
        boolean matchB = mRecipeItems.get(1).test(input.getStackInSlot(1)) &&
                         mRecipeItems.get(0).test(input.getStackInSlot(0));
        boolean matchC = mRecipeItems.get(2).test(input.getStackInSlot(2)) &&
                         mRecipeItems.get(1).test(input.getStackInSlot(1));
        boolean matchD = mRecipeItems.get(1).test(input.getStackInSlot(1)) &&
                         mRecipeItems.get(2).test(input.getStackInSlot(2));
        boolean matchE = mRecipeItems.get(2).test(input.getStackInSlot(2)) &&
                         mRecipeItems.get(0).test(input.getStackInSlot(0));
        boolean matchF = mRecipeItems.get(0).test(input.getStackInSlot(0)) &&
                         mRecipeItems.get(2).test(input.getStackInSlot(2));

        return matchA || matchB || matchC || matchD || matchE || matchF;
    }

    @Override
    public ItemStack craft(ImbuerRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return mOutput;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.mRecipeItems.size());
        list.addAll(mRecipeItems);
        return list;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return mOutput.copy();
    }

    public int getCookingTime() { return mCookingTime; }

    public int getExperience() { return mExperience; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ImbuerRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ImbueRecipeType.INSTANCE;
    }

    public static class ImbueRecipeType implements RecipeType<ImbueRecipe> {
        private ImbueRecipeType() {}
        public static final ImbueRecipeType INSTANCE = new ImbueRecipeType();
        public static final String ID = "imbuer";
    }

    public static class ImbuerRecipeSerializer implements RecipeSerializer<ImbueRecipe> {
        public ImbuerRecipeSerializer() {}
        public static final ImbuerRecipeSerializer INSTANCE = new ImbuerRecipeSerializer();
        public static final String ID = "imbuer";

        public static final MapCodec<ImbueRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf(ImbueRecipe.ImbueRecipeAttributes.COOKINGTIME.value).forGetter(ImbueRecipe::getCookingTime),
                Codec.INT.fieldOf(ImbueRecipe.ImbueRecipeAttributes.EXPERIENCE.value).forGetter(ImbueRecipe::getExperience),
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, ImbueRecipe.mNumberOfInputs)
                        .fieldOf(ImbueRecipe.ImbueRecipeAttributes.INGREDIENTS.value)
                        .forGetter(ImbueRecipe::getIngredients),
                ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf(ImbueRecipe.ImbueRecipeAttributes.RESULT.value).forGetter(r -> r.mOutput)
                ).apply(instance, ImbueRecipe::new));

        public static final PacketCodec<RegistryByteBuf, ImbueRecipe> PACKET_CODEC =
                PacketCodec.ofStatic(ImbuerRecipeSerializer::write, ImbuerRecipeSerializer::read);

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            Codec<List<Ingredient>> validatedResult = Ingredient.DISALLOW_EMPTY_CODEC.listOf().flatXmap(
                    (ingredients) -> {
                        Ingredient[] copyOfIngredients = (Ingredient[])ingredients.stream().filter(ingredient -> {
                            return !ingredient.isEmpty();
                        }).toArray((i) -> {
                            return new Ingredient[i];
                        });

                        if (copyOfIngredients.length == 0) {
                            return DataResult.error(() -> {
                                return "No ingredients for shapeless recipe";
                            });
                        } else {
                            return copyOfIngredients.length > max ? DataResult.error(() -> {
                                return "Too many ingredients for shapeless recipe";
                            }) : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, copyOfIngredients));
                        }
                    },
                    DataResult::success
            );

            return validatedResult;
        }

        @Override
        public MapCodec<ImbueRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ImbueRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ImbueRecipe read(RegistryByteBuf buf) {
            int cookingtime = buf.readInt();
            int experience = buf.readInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.PACKET_CODEC.decode(buf));
            }

            return new ImbueRecipe(cookingtime, experience, inputs, ItemStack.PACKET_CODEC.decode(buf));
        }

        private static void write(RegistryByteBuf buf, ImbueRecipe recipe) {
            buf.writeInt(recipe.getCookingTime());
            buf.writeInt(recipe.getExperience());
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));
        }
    }
}
