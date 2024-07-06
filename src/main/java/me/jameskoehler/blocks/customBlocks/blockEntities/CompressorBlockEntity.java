package me.jameskoehler.blocks.customBlocks.blockEntities;

import me.jameskoehler.TBS4Content;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CompressorBlockEntity extends BlockEntity {

    public CompressorBlockEntity(BlockPos pos, BlockState state) {

        super(TBS4Content.COMPRESOR_BLOCK_ENTITY, pos, state);
    }
}

