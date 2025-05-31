package xxrexraptorxx.magmacore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import javax.annotation.Nullable;

public class EffectHelper {

    /**
     * Retrieves a {@link MobEffectInstance} for the given effect ID if it exists in the registry,
     * or returns an instance using the provided alternative effect holder.
     *
     * @param effectId          the string ID (path) of the desired mob effect; may be null
     * @param duration          the duration (in ticks) for the effect instance
     * @param amplifier         the amplifier (level minus one) for the effect instance
     * @param alternativeEffect an optional fallback {@link Holder} of {@link MobEffect} to use if the
     *                          effectId is not found; may be null
     * @return a new {@link MobEffectInstance} for the found effect or the alternativeEffect, or
     * null if neither is available or effectId is null
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
     * @param effectId            the primary string ID (path) of the desired mob effect; may be null
     * @param alternativeEffectId the secondary string ID (path) of an alternative effect; may be null
     * @param duration            the duration (in ticks) for the effect instance
     * @param amplifier           the amplifier (level minus one) for the effect instance
     * @param fallbackEffect      an optional fallback {@link Holder} of {@link MobEffect} to use if neither
     *                            effectId nor alternativeEffectId is found; may be null
     * @return a new {@link MobEffectInstance} for the first found effect among effectId or alternativeEffectId,
     * or for fallbackEffect if neither ID is found, or null if no valid ID or fallback is available
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


    public static ChatFormatting getEffectCategoryColor(MobEffect effect) {
        return FormattingHelper.getEffectCategoryColor(effect);
    }

}
