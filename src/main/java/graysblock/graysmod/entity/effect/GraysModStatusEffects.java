package graysblock.graysmod.entity.effect;

import graysblock.graysmod.GraysMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class GraysModStatusEffects {

    public static final RegistryEntry<StatusEffect> MAGMA_SPEED = register("magma_speed", new MagmaSpeedStatusEffect());
    public static final RegistryEntry<StatusEffect> SHADOW_FORM = register("shadow_form", new ShadowFormStatusEffect());
    public static final RegistryEntry<StatusEffect> TITAN_FORM = register("titan_form", new TitanFormStatusEffect().addAttributeModifier(
            EntityAttributes.GENERIC_SCALE, Identifier.of(GraysMod.MOD_ID, "effect.graysmod.titan_form"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE
    ));
    public static final RegistryEntry<StatusEffect> SHRIMP_FORM = register("shrimp_form", new ShrimpFormStatusEffect().addAttributeModifier(
            EntityAttributes.GENERIC_SCALE, Identifier.of(GraysMod.MOD_ID, "effect.graysmod.shrimp_form"), -0.5F, EntityAttributeModifier.Operation.ADD_VALUE
    ));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(GraysMod.MOD_ID, id), statusEffect);
    }

    public static void registerModdedStatusEffects() {
        GraysMod.LOGGER.info("Registering GraysMod Status Effects...");
    }
}
