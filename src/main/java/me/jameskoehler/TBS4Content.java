package me.jameskoehler;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}