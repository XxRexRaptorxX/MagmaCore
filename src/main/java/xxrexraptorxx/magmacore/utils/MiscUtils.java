package xxrexraptorxx.magmacore.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.main.References;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class MiscUtils {

    /**
     * Detects the ID of the mod that invoked this method by inspecting the call stack and
     * matching the originating JAR file name against the list of loaded mod files.
     * <p>
     * It finds the first caller outside this coremod's package, determines the JAR location of
     * that caller class via its ProtectionDomain, and then compares the JAR file name with
     * those in the loaded mod files list.
     *
     * @return the detected mod ID, or this coremod's ID as a fallback if detection fails
     */
    public static String detectModId() {
        // 1) Find the first caller class outside of this coremod's package
        Class<?> caller = StackWalker
                .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .filter(c -> !c.getPackageName().startsWith("xxrexraptorxx." + References.MODID))
                        .findFirst()
                ).orElse(null);

        if (caller != null) {
            // 2) Retrieve the code source location (JAR) of the caller class
            ProtectionDomain domain = caller.getProtectionDomain();
            CodeSource source = domain.getCodeSource();
            if (source != null) {
                try {
                    URL location = source.getLocation();            // e.g., file:/.../mods/myMod-1.0.jar
                    URI uri = location.toURI();
                    String jarName = Paths.get(uri).getFileName().toString();

                    // 3) Compare the JAR name against all loaded mod file names
                    for (IModFileInfo fileInfo : ModList.get().getModFiles()) {
                        if (fileInfo.getFile().getFileName().equals(jarName)) {
                            // Return the first mod ID in this JAR
                            IModInfo mod = fileInfo.getMods().getFirst();
                            return mod.getModId();
                        }
                    }
                } catch (Exception e) {
                    MagmaCore.LOGGER.warn("detectModId: could not resolve JAR path", e);
                }
            }
        }

        // Fallback: log an error and return this coremod's ID
        MagmaCore.LOGGER.error("detectModId: unable to determine calling mod; falling back to own ID");
        return References.MODID;
    }


    /**
     * Retrieves a {@link MobEffectInstance} for the given effect ID if it exists in the registry,
     * or returns an instance using the provided alternative effect holder.
     *
     * @param effectId the string ID (path) of the desired mob effect; may be null
     * @param duration the duration (in ticks) for the effect instance
     * @param amplifier the amplifier (level minus one) for the effect instance
     * @param alternativeEffect an optional fallback {@link Holder} of {@link MobEffect} to use if the
     *                          effectId is not found; may be null
     * @return a new {@link MobEffectInstance} for the found effect or the alternativeEffect, or
     *         null if neither is available or effectId is null
     */
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


    /**
     * Retrieves a {@link MobEffectInstance} by matching either of two possible effect IDs in the registry,
     * or returns an instance using the provided fallback effect holder.
     *
     * @param effectId the primary string ID (path) of the desired mob effect; may be null
     * @param alternativeEffectId the secondary string ID (path) of an alternative effect; may be null
     * @param duration the duration (in ticks) for the effect instance
     * @param amplifier the amplifier (level minus one) for the effect instance
     * @param fallbackEffect an optional fallback {@link Holder} of {@link MobEffect} to use if neither
     *                       effectId nor alternativeEffectId is found; may be null
     * @return a new {@link MobEffectInstance} for the first found effect among effectId or alternativeEffectId,
     *         or for fallbackEffect if neither ID is found, or null if no valid ID or fallback is available
     */
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
