package me.jameskoehler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.LoggerFactory;

public class ReturnRod extends Item {
    public ReturnRod(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && user instanceof ServerPlayerEntity serverUser) {
            //on server
            if (world.getRegistryKey().equals(serverUser.getSpawnPointDimension())) {
                BlockPos spawnPoint = serverUser.getSpawnPointPosition();
                LoggerFactory.getLogger("tbs4content").info(spawnPoint.toString());

                user.requestTeleport(spawnPoint.getX()+0.5, spawnPoint.getY()+0.7, spawnPoint.getZ()+0.5);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
