package me.jameskoehler;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TBS4Content implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("tbs4content");
	public static final String modID = "tbs4content";

	// Items
	public static final Item END_STONE_DUST = Registry.register(Registries.ITEM, Identifier.of(modID, "endstonedust"), new Item(new Item.Settings()));
	public static final Item END_STONE_CLAY = Registry.register(Registries.ITEM, Identifier.of(modID, "endstoneclay"), new Item(new Item.Settings()));
	public static final Item COATED_EYE = Registry.register(Registries.ITEM, Identifier.of(modID, "coatedeye"), new Item(new Item.Settings()));
	public static final Item CERAMIC_EYE = Registry.register(Registries.ITEM, Identifier.of(modID, "ceramiceye"), new Item(new Item.Settings()));
	public static final ReturnRod RETURN_ROD = Registry.register(Registries.ITEM, Identifier.of(modID, "returnrod"), new ReturnRod(new Item.Settings()));

	// Blocks
	public static final Block COMPRESSED_IRON = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressediron", true);
	public static final Block COMPRESSED_COPPER = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedcopper", true);
	public static final Block COMPRESSED_GOLD = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedgold", true);
	public static final Block COMPRESSED_NETHERITE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(70.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressednetherite", true);
	public static final Block COMPRESSED_DIAMOND = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compresseddiamond", true);
	public static final Block COMPRESSED_EMERALD = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(65.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedemerald", true);
	public static final Block COMPRESSED_AMETHYST = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(60.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedamethyst", true);
	public static final Block COMPRESSED_QUARTZ = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(60.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedquartz", true);
	public static final Block COMPRESSED_REDSTONE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(45.0f, 1200.0f).pistonBehavior(PistonBehavior.BLOCK)), "compressedredstone", true);
	public static final Block COMPRESSED_SLIME = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME).breakInstantly()), "compressedslime", true);

	public static final Block SHAPED_CHARGE = ModBlocks.register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL).breakInstantly()), "shapedcharge", true);

	public static final ItemGroup TBS4CONTENT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(END_STONE_DUST))
			.displayName(Text.translatable("itemGroup.tbs4content.tbs4content_group"))
			.entries((context, entries) -> {
				entries.add(END_STONE_DUST);
				entries.add(END_STONE_CLAY);
				entries.add(COATED_EYE);
				entries.add(CERAMIC_EYE);
				entries.add(RETURN_ROD);

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

				entries.add(SHAPED_CHARGE);
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

		ModBlocks.initialize();
	}
}