package me.jameskoehler.tools;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.SphereEffect;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;

import java.util.Optional;
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
                Vec3d pos = world.getSpawnPos().toCenterPos();
                float spawnAngle = serverUser.getSpawnAngle();
                NbtCompound nbt = serverUser.writeNbt(new NbtCompound());

                int x = nbt.getInt("SpawnX");
                int y = nbt.getInt("SpawnY");
                int z = nbt.getInt("SpawnZ");

                BlockPos spawnPoint = new BlockPos(x,y,z);
                BlockState bs = world.getBlockState(spawnPoint);
                Block block = bs.getBlock();

                if (block instanceof BedBlock && BedBlock.isBedWorking(world)) {
                    Optional op = BedBlock.findWakeUpPosition(EntityType.PLAYER, world, spawnPoint, bs.get(BedBlock.FACING), spawnAngle);
                    if (op.isPresent()) {
                        pos = (Vec3d) op.get();
                    }
                } else {
                    // UPDATE THIS TO A TRANSLATABLE
                    serverUser.sendMessage(Text.literal("You have no home bed, or it was obstructed"));
                }
                Vec3d currPos = serverUser.getPos().add(new Vec3d(0,1,0));

                if (world instanceof ServerWorld serverWorld) {
                    Effect startingPortal = new SphereEffect(serverWorld, ParticleTypes.REVERSE_PORTAL, currPos, 1000, 1.5);
                    startingPortal.runFor(3);

                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 1000));

                    Vec3d finalPos = pos;
                    new Thread(() -> {
                        //this "works" look into other methods of waiting before executing code because I don't think this will work on a server
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

                        user.requestTeleport(finalPos.getX(), finalPos.getY(), finalPos.getZ());
                        user.setAngles(0.0f,0.0f);

                        serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }).start();

                    Effect endingPortal = new SphereEffect(serverWorld, ParticleTypes.REVERSE_PORTAL, pos.add(new Vec3d(0,1,0)), 1000, 1.5);
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
