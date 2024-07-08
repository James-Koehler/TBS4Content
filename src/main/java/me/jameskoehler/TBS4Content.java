package me.jameskoehler;

import me.jameskoehler.armor.DaniteArmorItem;
import me.jameskoehler.armor.LeadArmorItem;
import me.jameskoehler.armor.ModArmorMaterials;
import me.jameskoehler.blocks.ModBlocks;
import me.jameskoehler.blocks.customBlocks.blockEntities.CompressorBlockEntity;
import me.jameskoehler.blocks.customBlocks.blockEntities.ImbuerBlockEntity;
import me.jameskoehler.blocks.customBlocks.CompressedRedstoneBlock;
import me.jameskoehler.blocks.customBlocks.CompressedSlimeBlock;
import me.jameskoehler.blocks.customBlocks.Compressor;
import me.jameskoehler.blocks.customBlocks.Imbuer;
import me.jameskoehler.items.Chemotherapy;
import me.jameskoehler.items.IodineTablets;
import me.jameskoehler.potioneffects.Leukemia;
import me.jameskoehler.potioneffects.RadiationPoisoning;
import me.jameskoehler.recipe.ModRecipes;
import me.jameskoehler.screen.ModScreenHandlers;
import me.jameskoehler.tools.ReturnRod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.jameskoehler.screen.ModScreenHandlers.IMBUER_SCREEN_HANDLER;

public class TBS4Content implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("tbs4content");
	public static final String modID = "tbs4content";
	public static Identifier ID(String path) {
		return Identifier.of(modID, path);
	}

	// Status Effects
	public static final Leukemia LEUKEMIA = Registry.register(Registries.STATUS_EFFECT, Identifier.of(modID, "leukemia"), new Leukemia());
	public static final RadiationPoisoning RADIATION_POISONING = Registry.register(Registries.STATUS_EFFECT, Identifier.of(modID, "radiation_poisoning"), new RadiationPoisoning());

	// Materials
	public static final Item END_STONE_DUST = Registry.register(Registries.ITEM, Identifier.of(modID, "end_stone_dust"), new Item(new Item.Settings().maxCount(16)));
	public static final Item END_STONE_CLAY = Registry.register(Registries.ITEM, Identifier.of(modID, "end_stone_clay"), new Item(new Item.Settings().maxCount(16)));
	public static final Item COATED_EYE = Registry.register(Registries.ITEM, Identifier.of(modID, "coated_eye"), new Item(new Item.Settings().maxCount(1)));
	public static final Item CERAMIC_EYE = Registry.register(Registries.ITEM, Identifier.of(modID, "ceramic_eye"), new Item(new Item.Settings().maxCount(1)));

	public static final Item RAW_LEAD = Registry.register(Registries.ITEM, Identifier.of(modID, "raw_lead"), new Item(new Item.Settings()));
	public static final Item LEAD_INGOT = Registry.register(Registries.ITEM, Identifier.of(modID, "lead_ingot"), new Item(new Item.Settings()));
	public static final Item DANITE_CRYSTAL = Registry.register(Registries.ITEM, Identifier.of(modID, "danite_crystal"), new Item(new Item.Settings()));

	// Medicine
	public static final IodineTablets IODINE_TABLETS = Registry.register(Registries.ITEM, Identifier.of(modID, "iodine_tablets"), new IodineTablets(new Item.Settings()));
	public static final Chemotherapy CHEMOTHERAPY = Registry.register(Registries.ITEM, Identifier.of(modID, "chemotherapy"), new Chemotherapy(new Item.Settings()));

	// Armor
	public static final LeadArmorItem LEAD_HELMET = Registry.register(Registries.ITEM, Identifier.of(modID, "lead_helmet"), new LeadArmorItem(ModArmorMaterials.LEAD, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).maxDamage(120)));
	public static final LeadArmorItem LEAD_CHESTPLATE = Registry.register(Registries.ITEM, Identifier.of(modID, "lead_chestplate"), new LeadArmorItem(ModArmorMaterials.LEAD, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1).maxDamage(200)));
	public static final LeadArmorItem LEAD_LEGGINGS = Registry.register(Registries.ITEM, Identifier.of(modID, "lead_leggings"), new LeadArmorItem(ModArmorMaterials.LEAD, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1).maxDamage(180)));
	public static final LeadArmorItem LEAD_BOOTS = Registry.register(Registries.ITEM, Identifier.of(modID, "lead_boots"), new LeadArmorItem(ModArmorMaterials.LEAD, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1).maxDamage(155)));

	public static final DaniteArmorItem DANITE_HELMET = Registry.register(Registries.ITEM, Identifier.of(modID, "danite_helmet"), new DaniteArmorItem(ModArmorMaterials.DANITE, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).maxDamage(450).fireproof().rarity(Rarity.EPIC)));
	public static final DaniteArmorItem DANITE_CHESTPLATE = Registry.register(Registries.ITEM, Identifier.of(modID, "danite_chestplate"), new DaniteArmorItem(ModArmorMaterials.DANITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1).maxDamage(642).fireproof().rarity(Rarity.EPIC)));
	public static final DaniteArmorItem DANITE_LEGGINGS = Registry.register(Registries.ITEM, Identifier.of(modID, "danite_leggings"), new DaniteArmorItem(ModArmorMaterials.DANITE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1).maxDamage(605).fireproof().rarity(Rarity.EPIC)));
	public static final DaniteArmorItem DANITE_BOOTS = Registry.register(Registries.ITEM, Identifier.of(modID, "danite_boots"), new DaniteArmorItem(ModArmorMaterials.DANITE, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1).maxDamage(531).fireproof().rarity(Rarity.EPIC)));

	// Tools
	public static final ReturnRod RETURN_ROD = Registry.register(Registries.ITEM, Identifier.of(modID, "return_rod"), new ReturnRod(new Item.Settings().maxCount(1)));

	// Blocks
	public static final Block DANITE_BLOCK = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(5.0f, 5.0f)), "danite_block", true);
	public static final Block LEAD_BLOCK = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(10.0f, 10.0f)), "lead_block", true);

	public static final Block COMPRESSED_IRON = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_iron", true);
	public static final Block COMPRESSED_COPPER = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_copper", true);
	public static final Block COMPRESSED_GOLD = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_gold", true);
	public static final Block COMPRESSED_NETHERITE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(70.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_netherite", true);
	public static final Block COMPRESSED_DIAMOND = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_diamond", true);
	public static final Block COMPRESSED_EMERALD = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_emerald", true);
	public static final Block COMPRESSED_AMETHYST = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(60.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_amethyst", true);
	public static final Block COMPRESSED_QUARTZ = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(60.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_quartz", true);
	public static final CompressedRedstoneBlock COMPRESSED_REDSTONE = (CompressedRedstoneBlock) ModBlocks.register(new CompressedRedstoneBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(45.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_redstone", true);
	public static final CompressedSlimeBlock COMPRESSED_SLIME = (CompressedSlimeBlock) ModBlocks.register(new CompressedSlimeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME).breakInstantly()), "compressed_slime", true);
	public static final Block COMPRESSED_LEAD = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(60.0f, 60.f).pistonBehavior(PistonBehavior.BLOCK)), "compressed_lead", true);
	public static final Block COMPRESSED_DANITE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.BLOCK)), "compressed_danite", true);

	public static final Block SHAPED_CHARGE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL).breakInstantly()), "shaped_charge", true);

	public static final Imbuer IMBUER = (Imbuer) ModBlocks.register(new Imbuer(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(15.0f, 250.0f)), "imbuer", true);
	public static final Compressor COMPRESSOR = (Compressor) ModBlocks.register(new Compressor(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(20.0f, 250.0f)), "compressor", true);

	// Ores
	public static final Block LEAD_ORE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f)), "lead_ore", true);
	public static final Block DEEPSLATE_LEAD_ORE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(6.0f, 6.0f)), "deepslate_lead_ore", true);
	public static final Block RAW_LEAD_BLOCK = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f)), "raw_lead_block", true);

	// Block Entities
	public static final BlockEntityType<ImbuerBlockEntity> IMBUER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modID, "imbuer_block_entity"), BlockEntityType.Builder.create(ImbuerBlockEntity::new, IMBUER).build());
	public static final BlockEntityType<CompressorBlockEntity> COMPRESOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modID, "compressor_block_entity"), BlockEntityType.Builder.create(CompressorBlockEntity::new, COMPRESSOR).build());


	// Item Groups
	public static final ItemGroup TBS4CONTENT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(END_STONE_DUST))
			.displayName(Text.translatable("itemGroup.tbs4content.tbs4content_group"))
			.entries((context, entries) -> {
				entries.add(END_STONE_DUST);
				entries.add(END_STONE_CLAY);
				entries.add(COATED_EYE);
				entries.add(CERAMIC_EYE);
				entries.add(RETURN_ROD);

				entries.add(RAW_LEAD);

				entries.add(DANITE_CRYSTAL);
				entries.add(LEAD_INGOT);

				entries.add(LEAD_BLOCK);
				entries.add(DANITE_BLOCK);

				entries.add(COMPRESSED_IRON);
				entries.add(COMPRESSED_COPPER);
				entries.add(COMPRESSED_GOLD);
				entries.add(COMPRESSED_NETHERITE);
				entries.add(COMPRESSED_DIAMOND);
				entries.add(COMPRESSED_EMERALD);
				entries.add(COMPRESSED_AMETHYST);
				entries.add(COMPRESSED_QUARTZ);
				entries.add(COMPRESSED_REDSTONE);
				entries.add(COMPRESSED_SLIME);
				entries.add(COMPRESSED_LEAD);
				entries.add(COMPRESSED_DANITE);

				entries.add(IMBUER);
				entries.add(COMPRESSOR);

				entries.add(LEAD_ORE);
				entries.add(DEEPSLATE_LEAD_ORE);
				entries.add(RAW_LEAD_BLOCK);

				entries.add(SHAPED_CHARGE);

				entries.add(LEAD_HELMET);
				entries.add(LEAD_CHESTPLATE);
				entries.add(LEAD_LEGGINGS);
				entries.add(LEAD_BOOTS);
				entries.add(DANITE_HELMET);
				entries.add(DANITE_CHESTPLATE);
				entries.add(DANITE_LEGGINGS);
				entries.add(DANITE_BOOTS);
			}).build();

	public static final ItemGroup TBS4CONTENT_COMPRESSED_BLOCKS_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(COMPRESSED_IRON))
			.displayName(Text.translatable("itemGroup.tbs4content.tbs4content_compressed_blocks_group"))
			.entries((context, entries) -> {
				entries.add(COMPRESSED_IRON);
				entries.add(COMPRESSED_COPPER);
				entries.add(COMPRESSED_GOLD);
				entries.add(COMPRESSED_NETHERITE);
				entries.add(COMPRESSED_DIAMOND);
				entries.add(COMPRESSED_EMERALD);
				entries.add(COMPRESSED_AMETHYST);
				entries.add(COMPRESSED_QUARTZ);
				entries.add(COMPRESSED_REDSTONE);
				entries.add(COMPRESSED_SLIME);
			}).build();

	public static final ItemGroup TBS4CONTENT_MAGIC_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(RETURN_ROD))
			.displayName(Text.translatable("itemGroup.tbs4content.tbs4content_magic_group"))
			.entries((context, entries) -> {
				entries.add(END_STONE_DUST);
				entries.add(END_STONE_CLAY);
				entries.add(COATED_EYE);
				entries.add(CERAMIC_EYE);
				entries.add(RETURN_ROD);

				entries.add(DANITE_CRYSTAL);

				entries.add(IODINE_TABLETS);
				entries.add(CHEMOTHERAPY);
			}).build();

	public static final ItemGroup TBS4CONTENT_COMBAT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(DANITE_HELMET))
			.displayName(Text.translatable("itemGroup.tbs4content.tbs4content_combat_group"))
			.entries((context, entries) -> {
				entries.add(DANITE_HELMET);
				entries.add(DANITE_CHESTPLATE);
				entries.add(DANITE_LEGGINGS);
				entries.add(DANITE_BOOTS);

				entries.add(LEAD_HELMET);
				entries.add(LEAD_CHESTPLATE);
				entries.add(LEAD_LEGGINGS);
				entries.add(LEAD_BOOTS);

				entries.add(SHAPED_CHARGE);
			}).build();


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading TBS4 Content Patch...");

		Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, "tbs4content_group"), TBS4CONTENT_GROUP);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, "tbs4content_compressed_blocks_group"), TBS4CONTENT_COMPRESSED_BLOCKS_GROUP);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, "tbs4content_magic_group"), TBS4CONTENT_MAGIC_GROUP);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(modID, "tbs4content_combat_group"), TBS4CONTENT_COMBAT_GROUP);

		ModScreenHandlers.registerAllScreenHandlers();

		Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modID, "imbuer_handler"), IMBUER_SCREEN_HANDLER);

		ModRecipes.registerRecipes();
		ModArmorMaterials.initialize();
		ModBlocks.initialize();
	}
}