package me.jameskoehler;

import me.jameskoehler.enchantments.ConfigEnchantmentEnabledCondition;
import me.jameskoehler.enchantments.EnchantmentIds;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.block.Block;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static me.jameskoehler.TBS4Content.*;

public class TBS4ContentDataGenerator implements DataGeneratorEntrypoint {

	private static class TBS4EnchantmentGenerator extends FabricDynamicRegistryProvider {

		public TBS4EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
			RegistryEntryLookup<DamageType> damageTypeLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE);
			RegistryEntryLookup<Enchantment> enchantmentLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
			RegistryEntryLookup<Item> itemLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.ITEM);
			RegistryEntryLookup<Block> blockLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.BLOCK);

			register(
					entries,
					EnchantmentIds.HOLLOW_PURPLE,
					Enchantment.builder(
							Enchantment.definition(
									itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
									1,
									3,
									Enchantment.leveledCost(1,10),
									Enchantment.leveledCost(16, 10),
									2,
									AttributeModifierSlot.ANY
							)
					).exclusiveSet(
							enchantmentLookup.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
					)
			);
			//add effect logic https://github.com/chronosacaria/MCDungeonsWeapons/blob/092ad8852a12fc7e9747a1b0e5c06516e2b67ca2/src/main/java/dev/timefall/mcdw/data/McdwEnchantmentGenerator.java#L284
			register(
					entries,
					EnchantmentIds.RADIATION_RESISTANCE,
					Enchantment.builder(
							Enchantment.definition(
									itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
									1,
									3,
									Enchantment.leveledCost(1, 10),
									Enchantment.leveledCost(16, 10),
									2,
									AttributeModifierSlot.ARMOR
							)
					).addEffect(
							EnchantmentEffectComponentTypes.ARMOR_EFFECTIVENESS,
							new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3f))
					)
			);
		}

		private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
			register(entries, key, builder, new ConfigEnchantmentEnabledCondition(key.getValue()));
		}
		private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition...resourceConditions) {
			entries.add(key, builder.build(key.getValue()), resourceConditions);
		}

		@Override
		public String getName() {
			return "TBS4Content Enchantment Generation";
		}
	}

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

	private static class TBS4LootTables extends FabricBlockLootTableProvider {

		public TBS4LootTables(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture){

			super(dataOutput, registriesFuture);
		}

		@Override
		public void generate() {

			addDrop(COMPRESSED_IRON, drops(COMPRESSED_IRON));
			addDrop(COMPRESSED_COPPER, drops(COMPRESSED_COPPER));
			addDrop(COMPRESSED_GOLD, drops(COMPRESSED_GOLD));
			addDrop(COMPRESSED_NETHERITE, drops(COMPRESSED_NETHERITE));
			addDrop(COMPRESSED_DIAMOND, drops(COMPRESSED_DIAMOND));
			addDrop(COMPRESSED_EMERALD, drops(COMPRESSED_EMERALD));
			addDrop(COMPRESSED_QUARTZ, drops(COMPRESSED_QUARTZ));
			addDrop(COMPRESSED_AMETHYST, drops(COMPRESSED_AMETHYST));
			addDrop(COMPRESSED_SLIME, drops(COMPRESSED_SLIME));
			addDrop(COMPRESSED_REDSTONE, drops(COMPRESSED_REDSTONE));

			addDrop(SHAPED_CHARGE, drops(SHAPED_CHARGE));

			addDrop(IMBUER, drops(IMBUER));
			addDrop(COMPRESSOR, drops(COMPRESSOR));
		}
	}

	private static class TBS4ModelGenerator extends FabricModelProvider {

		public TBS4ModelGenerator(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_IRON);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_GOLD);
			blockStateModelGenerator.registerSimpleCubeAll(TBS4Content.COMPRESSED_COPPER);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_NETHERITE);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_DIAMOND);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_EMERALD);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_AMETHYST);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_QUARTZ);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_REDSTONE);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_LEAD);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_DANITE);

			blockStateModelGenerator.registerSimpleCubeAll(DANITE_BLOCK);
			blockStateModelGenerator.registerSimpleCubeAll(LEAD_BLOCK);

			blockStateModelGenerator.registerSimpleCubeAll(LEAD_ORE);
			blockStateModelGenerator.registerSimpleCubeAll(DEEPSLATE_LEAD_ORE);
			blockStateModelGenerator.registerSimpleCubeAll(RAW_LEAD_BLOCK);
			blockStateModelGenerator.registerSimpleCubeAll(COMPRESSED_SLIME);
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_IRON), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_GOLD), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_COPPER), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_NETHERITE), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_DIAMOND), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_EMERALD), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_AMETHYST), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_QUARTZ), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_REDSTONE), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_LEAD), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(COMPRESSED_DANITE), Models.GENERATED);

			itemModelGenerator.register(Item.fromBlock(DANITE_BLOCK), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(LEAD_BLOCK), Models.GENERATED);

			itemModelGenerator.register(Item.fromBlock(LEAD_ORE), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(DEEPSLATE_LEAD_ORE), Models.GENERATED);
			itemModelGenerator.register(Item.fromBlock(RAW_LEAD_BLOCK), Models.GENERATED);

			itemModelGenerator.register(Item.fromBlock(COMPRESSED_SLIME), Models.GENERATED);
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(TBS4EnchantmentGenerator::new);
		pack.addProvider(TBS4RecipeGenerator::new);
		pack.addProvider(TBS4LootTables::new);
		pack.addProvider(TBS4ModelGenerator::new);
	}
}
