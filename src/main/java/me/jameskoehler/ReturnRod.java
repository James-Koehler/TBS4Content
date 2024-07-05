package me.jameskoehler;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.SphereEffect;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.concurrent.Executors;


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

                if (world instanceof ServerWorld serverWorld) {
//
//                    serverWorld.spawnParticles(
//                           ParticleTypes.PORTAL, serverUser.getX(), serverUser.getY()+1.5, serverUser.getZ(),
//                           500, 0.5, 0.5, 0.5, 1);
//                    serverWorld.spawnParticles(
//                            ParticleTypes.PORTAL, spawnPoint.getX(), spawnPoint.getY()+1.5, spawnPoint.getZ(),
//                            500, 0.5, 0.5, 0.5, 1);
                    Vec3d pos = new Vec3d(serverUser.getX(), serverUser.getY()+1, serverUser.getZ());
                    Effect startingPortal = new SphereEffect(serverWorld, ParticleTypes.REVERSE_PORTAL, pos, 1000, 1.5);
                    startingPortal.runFor(3);
                    //implement a waiting period, maybe freeze player during wait
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 1000));
                    new Thread(() -> {
                        //this "works" look into other methods of waiting before executing code because I don't think this will work on a server
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        user.requestTeleport(spawnPoint.getX()+0.5, spawnPoint.getY()+0.7, spawnPoint.getZ()+0.5);
                        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }).start();

//                    user.requestTeleport(spawnPoint.getX()+0.5, spawnPoint.getY()+0.7, spawnPoint.getZ()+0.5);
                    Effect endingPortal = new SphereEffect(serverWorld, ParticleTypes.REVERSE_PORTAL, spawnPoint.toCenterPos().add(new Vec3d(0, 1, 0)), 1000, 1.5);
                    endingPortal.runFor(2);
                }



                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        super.usageTick(world, user, stack, remainingUseTicks);
    }
}
