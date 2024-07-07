package me.jameskoehler.enchantments;

import me.jameskoehler.TBS4Content;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentIds {
    public static final List<Identifier> ENCHANTMENT_IDS = new ArrayList<>();

    public static final RegistryKey<Enchantment> HOLLOW_PURPLE = EnchantmentIds.of("hollow_purple");

    public static final RegistryKey<Enchantment> RADIATION_RESISTANCE = EnchantmentIds.of("radiation_resistance");

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = TBS4Content.ID(path);
        ENCHANTMENT_IDS.add(id);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }
}
