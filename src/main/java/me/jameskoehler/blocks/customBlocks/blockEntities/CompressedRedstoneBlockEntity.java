package me.jameskoehler.blocks.customBlocks.blockEntities;

import me.jameskoehler.IEntityDataSaver;
import me.jameskoehler.RadiationData;
import me.jameskoehler.TBS4Content;
import me.jameskoehler.potioneffects.RadiationPoisoning;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CompressedRedstoneBlockEntity extends BlockEntity {

    public CompressedRedstoneBlockEntity(BlockPos pos, BlockState state) {
        super(TBS4Content.COMPRESSED_REDSTONE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CompressedRedstoneBlockEntity entity) {
        if (world.isClient()) {

            return;
        }
        double range = 20.0;
        Vec3d origin = blockPos.toCenterPos();
        ServerWorld serverWorld = (ServerWorld) world;
        List<? extends PlayerEntity> list = serverWorld.getPlayers(
                player -> origin.isInRange(player.getPos(), range) &&
                        player.interactionManager.isSurvivalLike()
        );
        // need to figure out a way to slow this down, currently reaches 1000 very quickly
        list.forEach(player -> RadiationData.addExposure((IEntityDataSaver) player, 1));
        for(PlayerEntity player : list) {
            int exposure = ((IEntityDataSaver) player).getPersistentData().getInt("exposure");
            int duration = (30 + (10 * (exposure - 1000) / 50)) * 20;
            RadiationData.addExposure((IEntityDataSaver) player, 200);
            if (exposure >= 1000) {
                player.addStatusEffect(new StatusEffectInstance(TBS4Content.RADIATION_POISONING, duration, 0, false, false));
            }
        }
    }
}
