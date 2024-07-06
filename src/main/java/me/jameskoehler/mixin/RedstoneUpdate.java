package me.jameskoehler.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;

@Mixin(RedstoneWireBlock.class)
public class RedstoneUpdate {

    @Shadow
    private boolean wiresGivePower = true;

    @Shadow
    private static final Vec3d[] COLORS = Util.make(new Vec3d[26], colors -> {

        for (int i = 0; i <= 25; i++) {
            float f = MathHelper.clamp((float)i, 0.0F, 15.0F) / 15.0F;
            float g = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            float h = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float j = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            colors[i] = new Vec3d((double)g, (double)h, (double)j);
        }
    });

    @Shadow
    private int increasePower(BlockState state) {
        return 0;
    }

    /**
     * @author Dawn
     * @reason Implementing higher than 15 redstone level
     */
    @Overwrite
    private int getReceivedRedstonePower(World world, BlockPos pos) {
        this.wiresGivePower = false;
        int i = world.getReceivedRedstonePower(pos);
        this.wiresGivePower = true;
        int j = 0;
        if (i < 25) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                j = Math.max(j, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    j = Math.max(j, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    j = Math.max(j, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }
        }

        return Math.max(i, j - 1);
    }
}
