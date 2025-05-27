package xxrexraptorxx.magmacore.content.items;

import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.equipment.trim.*;
import net.minecraft.world.level.Level;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.utils.FormattingHelper;

import java.util.List;

public class RewardItems {

    public static ItemStack getCertificate() {
        ItemStack certificate = new ItemStack(Items.PAPER);

        certificate.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        certificate.set(DataComponents.RARITY, Rarity.EPIC);
        certificate.set(DataComponents.CUSTOM_NAME, FormattingHelper.setCoreLangComponent("item", "certificate", ChatFormatting.GOLD));
        certificate.set(DataComponents.LORE, new ItemLore(List.of(FormattingHelper.setCoreLangComponent("item", "certificate.lore", ChatFormatting.YELLOW)
                .append(Component.literal(FormattingHelper.lineSeperator + "- XxRexRaptorxX").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY)))));

        return certificate;
    }


    public static ItemStack getPlayerHead(Player player) {
        ItemStack reward = new ItemStack(Items.PLAYER_HEAD);
        var profile = new GameProfile(player.getUUID(), player.getName().getString());
        reward.set(DataComponents.PROFILE, new ResolvableProfile(profile));

        return reward;
    }


    public static ItemStack getChestplate(Level level, Player player) {
        ItemStack reward = new ItemStack(Items.IRON_CHESTPLATE);
        try {
            HolderLookup.RegistryLookup<TrimMaterial> lookupTrimMaterials = level.registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL);
            HolderLookup.RegistryLookup<TrimPattern> lookupTrimPatterns = level.registryAccess().lookupOrThrow(Registries.TRIM_PATTERN);

            reward.set(DataComponents.TRIM, new ArmorTrim(lookupTrimMaterials.getOrThrow(TrimMaterials.NETHERITE), lookupTrimPatterns.getOrThrow(TrimPatterns.SILENCE)));
            reward.set(DataComponents.RARITY, Rarity.EPIC);
            reward.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            reward.set(DataComponents.CUSTOM_NAME, FormattingHelper.setCoreLangComponent("item", "legendary_chestplate", ChatFormatting.GOLD));
            reward.set(DataComponents.LORE, new ItemLore(List.of(Component.empty(), FormattingHelper.setCoreLangComponent("item", "legendary_chestplate.lore").append(player.getName().getString()))));

        } catch (IllegalStateException e) {
            MagmaCore.LOGGER.error("Failed to create ArmorTrim for reward: {}", e.getMessage(), e);
        } catch (Exception e) {
            MagmaCore.LOGGER.error("Unexpected error in getArmor(): {}", e.getMessage(), e);
        }

        return reward;
    }


    public static ItemStack getLeggings(Level level, Player player) {
        ItemStack reward = new ItemStack(Items.IRON_LEGGINGS);
        try {
            HolderLookup.RegistryLookup<TrimMaterial> lookupTrimMaterials = level.registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL);
            HolderLookup.RegistryLookup<TrimPattern> lookupTrimPatterns = level.registryAccess().lookupOrThrow(Registries.TRIM_PATTERN);

            reward.set(DataComponents.TRIM, new ArmorTrim(lookupTrimMaterials.getOrThrow(TrimMaterials.NETHERITE), lookupTrimPatterns.getOrThrow(TrimPatterns.DUNE)));
            reward.set(DataComponents.RARITY, Rarity.EPIC);
            reward.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            reward.set(DataComponents.CUSTOM_NAME, FormattingHelper.setCoreLangComponent("item", "legendary_leggings", ChatFormatting.GOLD));
            reward.set(DataComponents.LORE, new ItemLore(List.of(Component.empty(), FormattingHelper.setCoreLangComponent("item", "legendary_leggings.lore").append(player.getName().getString()))));

        } catch (IllegalStateException e) {
            MagmaCore.LOGGER.error("Failed to create ArmorTrim for reward: {}", e.getMessage(), e);
        } catch (Exception e) {
            MagmaCore.LOGGER.error("Unexpected error in getArmor(): {}", e.getMessage(), e);
        }

        return reward;
    }
}
