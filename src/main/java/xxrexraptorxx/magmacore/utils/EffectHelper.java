package xxrexraptorxx.magmacore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.List;

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


    /**
     * Applies status effects to a player based on the number of matching armor pieces they are wearing.
     * <p>
     * This method checks if it is executing on the server side, if the given flag is true, and if the specified
     * entity is a Player. It then counts how many pieces of the player's equipped armor match the provided
     * head, chest, legs, and feet items. Depending on the count of matching pieces (from 1 to 4), it applies
     * the corresponding list of MobEffectInstance to the player.
     * </p>
     *
     * @param level    The server-level context in which to apply the effects. Must not be {@code null}. Effects
     *                 are only applied if {@code level.isClientSide()} returns {@code false}.
     * @param entity   The entity to check and potentially apply effects to. Only instances of {@link net.minecraft.world.entity.player.Player}
     *                 will receive effects when {@code flag} is {@code true}.
     * @param flag     A boolean guard that must be {@code true} for any effects to be applied.
     * @param head     The {@link net.minecraft.world.item.Item} instance representing the helmet to match against
     *                 the player's currently equipped helmet.
     * @param chest    The {@link net.minecraft.world.item.Item} instance representing the chestplate to match against
     *                 the player's currently equipped chestplate.
     * @param legs     The {@link net.minecraft.world.item.Item} instance representing the leggings to match against
     *                 the player's currently equipped leggings.
     * @param feet     The {@link net.minecraft.world.item.Item} instance representing the boots to match against
     *                 the player's currently equipped boots.
     * @param effects1 A list of {@link net.minecraft.world.effect.MobEffectInstance} to apply if exactly one piece
     *                 of the specified armor is worn. May be {@code null} if no effects should be applied at count 1.
     * @param effects2 A list of {@link net.minecraft.world.effect.MobEffectInstance} to apply if exactly two pieces
     *                 of the specified armor are worn. May be {@code null} if no effects should be applied at count 2.
     * @param effects3 A list of {@link net.minecraft.world.effect.MobEffectInstance} to apply if exactly three pieces
     *                 of the specified armor are worn. May be {@code null} if no effects should be applied at count 3.
     * @param effects4 A list of {@link net.minecraft.world.effect.MobEffectInstance} to apply if all four pieces
     *                 of the specified armor are worn. May be {@code null} if no effects should be applied at count 4.
     *
     * <p><b>Behavior Details:</b></p>
     * <ul>
     *   <li>Checks {@code !level.isClientSide()} to ensure execution on the server side only.</li>
     *   <li>Verifies {@code flag == true} and that {@code entity} is a {@link net.minecraft.world.entity.player.Player}.</li>
     *   <li>Retrieves the player's currently equipped armor items (helmet, chestplate, leggings, boots) via
     *       {@link net.minecraft.world.entity.EquipmentSlot} lookups.</li>
     *   <li>Compares each equipped armor piece to the corresponding {@code head}, {@code chest}, {@code legs},
     *       and {@code feet} parameters. Increments an internal counter for each match.</li>
     *   <li>Based on the final count of matching armor pieces (1 through 4), iterates over the corresponding
     *       list of effects ({@code effects1}, {@code effects2}, {@code effects3}, or {@code effects4}), and
     *       applies each {@link net.minecraft.world.effect.MobEffectInstance} to the player via
     *       {@link net.minecraft.world.entity.LivingEntity#addEffect}.</li>
     *   <li>If the matching effects list for the computed count is {@code null}, no effects are applied for that count.</li>
     *   <li>No effects are applied if fewer than one or more than four pieces match, or if {@code flag} is
     *       {@code false}, or if the entity is not a player.</li>
     * </ul>
     */
    public static void setArmorEffects(ServerLevel level, Entity entity, boolean flag, Item head, Item chest, Item legs, Item feet, @Nullable List<MobEffectInstance> effects1, @Nullable List<MobEffectInstance> effects2, @Nullable List<MobEffectInstance> effects3, @Nullable List<MobEffectInstance> effects4) {
        if (!level.isClientSide() && flag && entity instanceof Player player) {

            int armorCounter = 0;
            Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
            Item chestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
            Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
            Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();

            if (helmet == head) armorCounter++;
            if (chestplate == chest) armorCounter++;
            if (leggings == legs) armorCounter++;
            if (boots == feet) armorCounter++;

            switch (armorCounter) {

                case 1:
                    if (effects1 != null) {
                        for (MobEffectInstance effect : effects1) {
                            player.addEffect(effect);
                        }
                    }
                    break;

                case 2:
                    if (effects2 != null) {

                        for (MobEffectInstance effect : effects2) {
                            player.addEffect(effect);
                        }
                    }
                    break;

                case 3:
                    if (effects3 != null) {
                        for (MobEffectInstance effect : effects3) {
                            player.addEffect(effect);
                        }
                    }
                    break;

                case 4:
                    if (effects4 != null) {
                        for (MobEffectInstance effect : effects4) {
                            player.addEffect(effect);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
