package me.jameskoehler.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.RedstoneView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.world.RedstoneView.DIRECTIONS;

@Mixin(RedstoneView.class)
public interface RedstoneViewTest extends BlockView {

    @Shadow
    default int getEmittedRedstonePower(BlockPos pos, Direction direction) {

        return 0;
    }

    /**
     * @author Dawn
     * @reason 15+ redstone power
     */
    @Overwrite
    default int getReceivedRedstonePower(BlockPos pos) {
        int i = 0;

        for (Direction direction : DIRECTIONS) {
            int j = this.getEmittedRedstonePower(pos.offset(direction), direction);
            if (j >= 25) {
                return 25;
            }

            if (j > i) {
                i = j;
            }
        }

        return i;
    }

}
