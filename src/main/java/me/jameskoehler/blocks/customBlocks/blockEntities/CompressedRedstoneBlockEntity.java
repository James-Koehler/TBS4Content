package me.jameskoehler.blocks.customBlocks.blockEntities;

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
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
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
        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(TBS4Content.RADIATION_POISONING, 500, 0, false, false);
        List<ServerPlayerEntity> list = StatusEffectUtil.addEffectToPlayersWithinDistance(
                (ServerWorld)world, null, blockPos.toCenterPos(), 20.0, statusEffectInstance, 12000
        );
    }
}
