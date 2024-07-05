package me.jameskoehler;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static me.jameskoehler.TBS4Content.*;

public class TBS4ContentDataGenerator implements DataGeneratorEntrypoint {

	private static class TBS4RecipeGenerator extends FabricRecipeProvider{

		private TBS4RecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		public void generate(RecipeExporter exporter) {

			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, END_STONE_DUST, 3).input(Items.END_STONE)
					.criterion(FabricRecipeProvider.hasItem(END_STONE_DUST), FabricRecipeProvider.conditionsFromItem(END_STONE_DUST))
					.criterion(FabricRecipeProvider.hasItem(Items.END_STONE), FabricRecipeProvider.conditionsFromItem(Items.END_STONE))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, END_STONE_CLAY).input(END_STONE_DUST).input(Items.WATER_BUCKET)
					.criterion(FabricRecipeProvider.hasItem(END_STONE_CLAY), FabricRecipeProvider.conditionsFromItem(END_STONE_CLAY))
					.criterion(FabricRecipeProvider.hasItem(END_STONE_DUST), FabricRecipeProvider.conditionsFromItem(END_STONE_DUST))
					.criterion(FabricRecipeProvider.hasItem(Items.WATER_BUCKET), FabricRecipeProvider.conditionsFromItem(Items.WATER_BUCKET))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, COATED_EYE).input(END_STONE_CLAY).input(Items.ENDER_EYE)
					.criterion(FabricRecipeProvider.hasItem(COATED_EYE), FabricRecipeProvider.conditionsFromItem(COATED_EYE))
					.criterion(FabricRecipeProvider.hasItem(END_STONE_CLAY), FabricRecipeProvider.conditionsFromItem(END_STONE_CLAY))
					.criterion(FabricRecipeProvider.hasItem(Items.ENDER_EYE), FabricRecipeProvider.conditionsFromItem(Items.ENDER_EYE))
					.offerTo(exporter);

			RecipeProvider.offerSmelting(exporter, List.of(COATED_EYE), RecipeCategory.MISC, CERAMIC_EYE, 1.5f, 500, "TBS4");
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(TBS4RecipeGenerator::new);
	}
}
