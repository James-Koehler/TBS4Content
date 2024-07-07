package me.jameskoehler.enchantments;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.jameskoehler.TBS4Content;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record ConfigEnchantmentEnabledCondition(Identifier enchant) implements ResourceCondition {

    private static final MapCodec<ConfigEnchantmentEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                        Identifier.CODEC.fieldOf("enchant").forGetter(ConfigEnchantmentEnabledCondition::enchant)
                    ).apply(instance, ConfigEnchantmentEnabledCondition::new)
    );

    private static final ResourceConditionType<ConfigEnchantmentEnabledCondition> TYPE = ResourceConditionType.create(TBS4Content.ID("enchant_enabled"), CODEC);

    public static void register(){
        ResourceConditions.register(TYPE);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return false;
    }
}
