package me.jameskoehler.mixin;

import me.jameskoehler.TBS4Content;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PistonHandler.class)
public class PistonHandlerMixin {

    /**
     * @author Dawn
     * @reason Add implementation for CompressedSlimeBlocks
     */
    @Overwrite
    private static boolean isBlockSticky(BlockState state) {
        return state.isOf(Blocks.SLIME_BLOCK) || state.isOf(Blocks.HONEY_BLOCK) || state.isOf(TBS4Content.COMPRESSED_SLIME);
    }
}
