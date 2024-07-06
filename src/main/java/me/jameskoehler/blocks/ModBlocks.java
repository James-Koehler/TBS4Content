package me.jameskoehler.blocks;

import me.jameskoehler.TBS4Content;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static Block register(Block block, String name, boolean shouldRegisterItem) {

        Identifier id = Identifier.of(TBS4Content.modID, name);

        if(shouldRegisterItem) {

            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {


    }
}
