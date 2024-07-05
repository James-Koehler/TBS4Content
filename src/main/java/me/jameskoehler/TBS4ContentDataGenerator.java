package me.jameskoehler;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
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

			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.IRON_BLOCK, 9).input(COMPRESSED_IRON)
					.criterion(FabricRecipeProvider.hasItem(Items.IRON_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.IRON_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_IRON), FabricRecipeProvider.conditionsFromItem(COMPRESSED_IRON))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.GOLD_BLOCK, 9).input(COMPRESSED_GOLD)
					.criterion(FabricRecipeProvider.hasItem(Items.GOLD_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.GOLD_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_GOLD), FabricRecipeProvider.conditionsFromItem(COMPRESSED_GOLD))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.COPPER_BLOCK, 9).input(COMPRESSED_COPPER)
					.criterion(FabricRecipeProvider.hasItem(Items.COPPER_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.COPPER_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_GOLD), FabricRecipeProvider.conditionsFromItem(COMPRESSED_GOLD))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.NETHERITE_BLOCK, 9).input(COMPRESSED_NETHERITE)
					.criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_NETHERITE), FabricRecipeProvider.conditionsFromItem(COMPRESSED_NETHERITE))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.DIAMOND_BLOCK, 9).input(COMPRESSED_DIAMOND)
					.criterion(FabricRecipeProvider.hasItem(Items.DIAMOND_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.DIAMOND_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_DIAMOND), FabricRecipeProvider.conditionsFromItem(COMPRESSED_DIAMOND))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.EMERALD_BLOCK, 9).input(COMPRESSED_EMERALD)
					.criterion(FabricRecipeProvider.hasItem(Items.EMERALD_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.EMERALD_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_EMERALD), FabricRecipeProvider.conditionsFromItem(COMPRESSED_EMERALD))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.AMETHYST_BLOCK, 9).input(COMPRESSED_AMETHYST)
					.criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_AMETHYST), FabricRecipeProvider.conditionsFromItem(COMPRESSED_AMETHYST))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.QUARTZ, 9).input(COMPRESSED_QUARTZ)
					.criterion(FabricRecipeProvider.hasItem(Items.QUARTZ), FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_QUARTZ), FabricRecipeProvider.conditionsFromItem(COMPRESSED_QUARTZ))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.REDSTONE_BLOCK, 9).input(COMPRESSED_REDSTONE)
					.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_REDSTONE), FabricRecipeProvider.conditionsFromItem(COMPRESSED_REDSTONE))
					.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BLOCK, 9).input(COMPRESSED_SLIME)
					.criterion(FabricRecipeProvider.hasItem(Items.SLIME_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.SLIME_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_SLIME), FabricRecipeProvider.conditionsFromItem(COMPRESSED_SLIME))
					.offerTo(exporter);

			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_IRON).pattern("iii").pattern("iii").pattern("iii").input('i', Items.IRON_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_IRON), FabricRecipeProvider.conditionsFromItem(COMPRESSED_IRON))
					.criterion(FabricRecipeProvider.hasItem(Items.IRON_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.IRON_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_COPPER).pattern("iii").pattern("iii").pattern("iii").input('i', Items.COPPER_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_COPPER), FabricRecipeProvider.conditionsFromItem(COMPRESSED_COPPER))
					.criterion(FabricRecipeProvider.hasItem(Items.COPPER_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.COPPER_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_GOLD).pattern("iii").pattern("iii").pattern("iii").input('i', Items.GOLD_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_GOLD), FabricRecipeProvider.conditionsFromItem(COMPRESSED_GOLD))
					.criterion(FabricRecipeProvider.hasItem(Items.GOLD_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.GOLD_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_NETHERITE).pattern("iii").pattern("iii").pattern("iii").input('i', Items.NETHERITE_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_NETHERITE), FabricRecipeProvider.conditionsFromItem(COMPRESSED_NETHERITE))
					.criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_DIAMOND).pattern("iii").pattern("iii").pattern("iii").input('i', Items.DIAMOND_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_DIAMOND), FabricRecipeProvider.conditionsFromItem(COMPRESSED_DIAMOND))
					.criterion(FabricRecipeProvider.hasItem(Items.DIAMOND_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.DIAMOND_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_EMERALD).pattern("iii").pattern("iii").pattern("iii").input('i', Items.EMERALD_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_EMERALD), FabricRecipeProvider.conditionsFromItem(COMPRESSED_EMERALD))
					.criterion(FabricRecipeProvider.hasItem(Items.EMERALD_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.EMERALD_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_AMETHYST).pattern("iii").pattern("iii").pattern("iii").input('i', Items.AMETHYST_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_AMETHYST), FabricRecipeProvider.conditionsFromItem(COMPRESSED_AMETHYST))
					.criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, COMPRESSED_QUARTZ).pattern("iii").pattern("iii").pattern("iii").input('i', Items.QUARTZ)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_QUARTZ), FabricRecipeProvider.conditionsFromItem(COMPRESSED_QUARTZ))
					.criterion(FabricRecipeProvider.hasItem(Items.QUARTZ), FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, COMPRESSED_REDSTONE).pattern("iii").pattern("iii").pattern("iii").input('i', Items.REDSTONE_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_REDSTONE), FabricRecipeProvider.conditionsFromItem(COMPRESSED_REDSTONE))
					.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE_BLOCK))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, COMPRESSED_SLIME).pattern("iii").pattern("iii").pattern("iii").input('i', Items.SLIME_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(COMPRESSED_SLIME), FabricRecipeProvider.conditionsFromItem(COMPRESSED_SLIME))
					.criterion(FabricRecipeProvider.hasItem(Items.SLIME_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.SLIME_BLOCK))
					.offerTo(exporter);

			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, RETURN_ROD).pattern(" c ").pattern("dbd").pattern("nbn").input('c', CERAMIC_EYE).input('d', Items.DIAMOND).input('b', Items.BLAZE_ROD).input('n', Items.NETHERITE_INGOT)
					.criterion(FabricRecipeProvider.hasItem(RETURN_ROD), FabricRecipeProvider.conditionsFromItem(RETURN_ROD))
					.criterion(FabricRecipeProvider.hasItem(CERAMIC_EYE), FabricRecipeProvider.conditionsFromItem(CERAMIC_EYE))
					.criterion(FabricRecipeProvider.hasItem(Items.DIAMOND), FabricRecipeProvider.conditionsFromItem(Items.DIAMOND))
					.criterion(FabricRecipeProvider.hasItem(Items.BLAZE_ROD), FabricRecipeProvider.conditionsFromItem(Items.BLAZE_ROD))
					.criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT), FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_INGOT))
					.offerTo(exporter);

			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, SHAPED_CHARGE).pattern("iti").pattern("iti").pattern("iii").input('i', Items.IRON_INGOT).input('t', Items.TNT)
					.criterion(FabricRecipeProvider.hasItem(SHAPED_CHARGE), FabricRecipeProvider.conditionsFromItem(SHAPED_CHARGE))
					.criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
					.criterion(FabricRecipeProvider.hasItem(Items.TNT), FabricRecipeProvider.conditionsFromItem(Items.TNT))
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
