package me.jameskoehler.blocks.customBlocks;

import com.mojang.serialization.MapCodec;
import me.jameskoehler.blocks.customBlocks.blockEntities.CompressorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.jameskoehler.TBS4Content.COMPRESOR_BLOCK_ENTITY;

public class Compressor extends BlockWithEntity implements BlockEntityProvider {

    public Compressor(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    // Entity Behavior
    @Override
    public BlockRenderType getRenderType(BlockState state){

        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if(state.getBlock() != newState.getBlock()){

            BlockEntity blockEntity = world.getBlockEntity(pos);

            if(blockEntity instanceof CompressorBlockEntity) {

                ItemScatterer.spawn(world, pos, (CompressorBlockEntity)blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if(!world.isClient){

            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if(screenHandlerFactory != null) {

                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CompressorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return validateTicker(type, COMPRESOR_BLOCK_ENTITY, CompressorBlockEntity::tick);
    }
}
