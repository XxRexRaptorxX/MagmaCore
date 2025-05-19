package xxrexraptorxx.magmacore.world;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EventUtils {

    /**
     * Applies a status effect and/or damage to a player or living entity.
     * Only executes on the server side.
     *
     * @param entity         the entity to affect; if not a player or living entity, no action is taken
     * @param effectInstance the MobEffectInstance to apply, or null if no effect should be applied
     * @param damageAmount   the amount of damage to inflict; negative values deal magic damage, non-negative values inflict no damage
     */
    public static void addPlayerEffects(Entity entity, @Nullable MobEffectInstance effectInstance, float damageAmount) {
        Level level = entity.level();

        if (!level.isClientSide) {
            if (entity instanceof Player entityIn) {
                if (effectInstance != null) entityIn.addEffect(effectInstance);
                if (damageAmount < 0) entityIn.hurt(level.damageSources().magic(), damageAmount);

            } else if (entity instanceof LivingEntity entityIn) {
                if (effectInstance != null) entityIn.addEffect(effectInstance);
                if (damageAmount < 0) entityIn.hurt(level.damageSources().magic(), damageAmount);
            }
        }
    }
}
