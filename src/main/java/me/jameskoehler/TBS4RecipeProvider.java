package me.jameskoehler;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;

public class TBS4RecipeProvider extends FabricRecipeProvider {

    private TBS4RecipeProvider(FabricDataOutput generator) {

        super(generator);
    }

    @Override
    public void generate(RecipeExporter exporter) {

    }
}
