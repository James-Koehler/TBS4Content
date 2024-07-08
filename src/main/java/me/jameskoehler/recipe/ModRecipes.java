package me.jameskoehler.recipe;

import me.jameskoehler.TBS4Content;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

public class ModRecipes {
    public static void registerRecipes() {
        LoggerFactory.getLogger("tbs4content").debug("Registering recipes for " + TBS4Content.modID);

        Registry.register(
                Registries.RECIPE_SERIALIZER,
                Identifier.of(TBS4Content.modID, ImbueRecipe.ImbuerRecipeSerializer.ID),
                ImbueRecipe.ImbuerRecipeSerializer.INSTANCE
        );

        Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.of(TBS4Content.modID, ImbueRecipe.ImbueRecipeType.ID),
                ImbueRecipe.ImbueRecipeType.INSTANCE
        );
    }
}
