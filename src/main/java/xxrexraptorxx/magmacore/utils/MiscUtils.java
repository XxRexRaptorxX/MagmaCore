package xxrexraptorxx.magmacore.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.main.References;

import javax.annotation.Nullable;

public class MiscUtils {

    /**
     * Detects the mod ID of the mod that invoked this method by inspecting the call stack.
     * It skips any classes within this coremod's package and matches the caller's ClassLoader
     * against the ClassLoaders of loaded mod instances in ModList.
     *
     * @return The detected mod ID, or this coremod's id as a fallback.
     */
    public static String detectModId() {
        // Iterate through the stack trace to find the first caller outside this coremod package
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            String className = element.getClassName();
            if (!className.startsWith("xxrexraptorxx." + References.MODID)) {
                try {
                    Class<?> callerClass = Class.forName(className);
                    ClassLoader callerLoader = callerClass.getClassLoader();

                    // Iterate through all loaded mod containers
                    for (ModContainer container : ModList.get().getSortedMods()) {
                        // Compare the ClassLoader of the mod's ModInfo implementation with the caller's loader
                        ClassLoader infoLoader = container.getModInfo().getClass().getClassLoader();
                        if (callerLoader == infoLoader) {
                            return container.getModId();
                        }
                    }
                } catch (ClassNotFoundException e) {
                    // Skip classes that cannot be loaded
                }
            }
        }
        // Fallback to this coremod's MODID
        MagmaCore.LOGGER.error("Invalid mod check!");
        return References.MODID;
    }


    @Nullable
    public static MobEffectInstance getOptionalEffect(String effectId, int duration, int amplifier, @Nullable Holder<MobEffect> alternativeEffect) {
        if (effectId == null) return null;

        for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(effect);

            if (key != null && key.getPath().equals(effectId)) {
                return new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration, amplifier);
            }
        }

        if (alternativeEffect == null) {
            return null;
        } else {
            return new MobEffectInstance(alternativeEffect, duration, amplifier);
        }
    }


    @Nullable
    public static MobEffectInstance getOptionalEffect(String effectId, String alternativeEffectId, int duration, int amplifier, @Nullable Holder<MobEffect> fallbackEffect) {
        if (effectId == null && alternativeEffectId == null) return null;

        for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(effect);

            if (key != null && key.getPath().equals(effectId)) {
                return new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration, amplifier);
            }
            if (key != null && key.getPath().equals(alternativeEffectId)) {
                return new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration, amplifier);
            }
        }

        if (fallbackEffect == null) {
            return null;
        } else {
            return new MobEffectInstance(fallbackEffect, duration, amplifier);
        }
    }
}
