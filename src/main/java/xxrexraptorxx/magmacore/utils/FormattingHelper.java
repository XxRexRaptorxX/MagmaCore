package xxrexraptorxx.magmacore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.Level;
import xxrexraptorxx.magmacore.main.References;

public class FormattingHelper {

    public static final String lineSeperator = "\n";
    public static final String textSeperator = ": ";
    public static final String nameSeperator = "_";
    public static final String separator = ".";


    /**
     * Capitalizes each word in the given registry_name. Words are delineated by spaces or underscores.
     * The first letter of each word is converted to uppercase and the rest to lowercase.
     *
     * @param registry_name the input registry_name to capitalize; may contain spaces or underscores
     * @return a new registry_name with each word capitalized, or the original registry_name if it is null or empty
     */
    public static String transformRegistryNames(String registry_name) {
        if (registry_name == null || registry_name.isEmpty()) {
            return registry_name;
        }

        registry_name = registry_name.replace('_', ' ');
        String[] words = registry_name.split(" ");
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalizedString.toString().trim();
    }


    public static String capitalizeWords(String string) {
        return transformRegistryNames(string);
    }


    /**
     * Constructs a language tag prefix using the Magma Core id.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @return the concatenated prefix in the form "{prefix}.magmacore."
     */
    public static String setCoreTagPrefix(String prefix) {
        return prefix + separator + References.MODID + separator;
    }


    /**
     * Constructs a language tag prefix using the default namespace.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @return the concatenated prefix in the form "{prefix}.minecraft."
     */
    public static String setMCLangTagPrefix(String prefix) {
        return prefix + separator + ResourceLocation.DEFAULT_NAMESPACE + separator;
    }


    /**
     * Constructs a language tag prefix using the provided mod ID.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param modId the mod identifier to include in the prefix
     * @return the concatenated prefix in the form "{prefix}.{modId}."
     */
    public static String setLangTagPrefix(String prefix, String modId) {
        return prefix + separator + modId + separator;
    }


    /**
     * Constructs a full language tag using the MagmaCore id.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.magmacore.{suffix}"
     */
    public static String setCoreLangTag(String prefix, String suffix) {
        return prefix + separator + References.MODID + separator + suffix;
    }


    /**
     * Constructs a full language tag using the MagmaCore id.
     *
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "magmacore.{suffix}"
     */
    public static String setCoreLangTag(String suffix) {
        return References.MODID + separator + suffix;
    }


    /**
     * Constructs a full language tag using the default namespace.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.magmacore.{suffix}"
     */
    public static String setMCLangTag(String prefix, String suffix) {
        return prefix + separator + ResourceLocation.DEFAULT_NAMESPACE + separator + suffix;
    }


    /**
     * Constructs a full language tag using the provided mod ID.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param modId the mod identifier to include
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.{modId}.{suffix}"
     */
    public static String setLangTag(String prefix, String modId, String suffix) {
        return prefix + separator + modId + separator + suffix;
    }


    /**
     * Creates a translatable text component for a Magma Core language tag.
     *
     * @param prefix the base prefix for the language key
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "{prefix}.magmacore.{suffix}"
     */
    public static MutableComponent setCoreLangComponent(String prefix, String suffix) {
        return Component.translatable(setCoreLangTag(prefix, suffix));
    }


    /**
     * Creates a translatable text component for a Magma Core language key,
     * using the provided prefix and suffix, with the given text formatting.
     *
     * @param prefix     the base prefix for the translation key
     * @param suffix     the suffix for the language key (resulting key: "prefix.magmacore.suffix")
     * @param formatting the {@link ChatFormatting} style to apply to the text
     * @return a {@link MutableComponent} that will translate the key "{prefix}.magmacore.{suffix}"
     *          and apply the specified style
     */
    public static MutableComponent setCoreLangComponent(String prefix, String suffix, ChatFormatting formatting) {
        return setCoreLangComponent(prefix, suffix).withStyle(formatting);
    }


    /**
     * Creates a translatable text component for a Magma Core language tag.
     *
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "magmacore.{suffix}"
     */
    public static MutableComponent setCoreLangComponent(String suffix) {
        return Component.translatable(setCoreLangTag(suffix));
    }


    /**
     * Creates a translatable text component for a Magma Core language key,
     * using the provided suffix, with the given text formatting.
     *
     * @param suffix     the suffix for the translation key (resulting key: "magmacore.suffix")
     * @param formatting the {@link ChatFormatting} style to apply to the text
     * @return a {@link MutableComponent} that will translate the key "magmacore.{suffix}"
     *          and apply the specified style
     */
    public static MutableComponent setCoreLangComponent(String suffix, ChatFormatting formatting) {
        return setCoreLangComponent(suffix).withStyle(formatting);
    }


    /**
     * Creates a translatable text component for a mod-specific language tag
     * using the provided mod ID.
     *
     * @param prefix the base prefix for the language key
     * @param modId the mod identifier to include
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "{prefix}.{modId}.{suffix}"
     */
    public static MutableComponent setModLangComponent(String prefix, String modId, String suffix) {
        return Component.translatable(setLangTag(prefix, modId, suffix));
    }


    /**
     * Creates a translatable text component for a mod-specific language key,
     * using the provided prefix, mod ID, and suffix, with the given text formatting.
     *
     * @param prefix     the base prefix for the translation key
     * @param modId      the mod identifier to include in the translation key
     * @param suffix     the suffix for the language key (resulting key: "prefix.modId.suffix")
     * @param formatting the {@link ChatFormatting} style to apply to the text
     * @return a {@link MutableComponent} that will translate the key "{prefix}.{modId}.{suffix}"
     *          and apply the specified style
     */
    public static MutableComponent setModLangComponent(String prefix, String modId, String suffix, ChatFormatting formatting) {
        return setModLangComponent(prefix, modId, suffix).withStyle(formatting);
    }


    /**
     * Creates a translatable chat message component for an item using its description translation key.
     *
     * @param modId the mod ID used to construct the translation key
     * @param item  the item whose description key will be used
     * @return a translatable {@link MutableComponent} for the item's description
     */
    public static MutableComponent setMessageComponent(String modId, Item item) {
        return Component.translatable(setLangTag("message", modId, BuiltInRegistries.ITEM.getKey(item).getPath() + ".desc"));
    }


    /**
     * Creates a translatable and formatted chat message component for an item.
     *
     * @param modId      the mod ID used to construct the translation key
     * @param item       the item whose description key will be used
     * @param formatting the {@link ChatFormatting} style to apply to the message
     * @return a translatable {@link MutableComponent} with the specified formatting
     */
    public static MutableComponent setMessageComponent(String modId, Item item, ChatFormatting formatting) {
        return setMessageComponent(modId, item).withStyle(formatting);
    }


    /**
     * Creates a translatable chat message component for a block using its description translation key.
     *
     * @param modId the mod ID used to construct the translation key
     * @param block the block whose description key will be used
     * @return a translatable {@link MutableComponent} for the block's description
     */
    public static MutableComponent setMessageComponent(String modId, Block block) {
        return Component.translatable(setLangTag("message", modId, BuiltInRegistries.BLOCK.getKey(block).getPath() + ".desc"));
    }


    /**
     * Creates a translatable and formatted chat message component for a block.
     *
     * @param modId      the mod ID used to construct the translation key
     * @param block      the block whose description key will be used
     * @param formatting the {@link ChatFormatting} style to apply to the message
     * @return a translatable {@link MutableComponent} with the specified formatting
     */
    public static MutableComponent setMessageComponent(String modId, Block block, ChatFormatting formatting) {
        return setMessageComponent(modId, block).withStyle(formatting);
    }


    /**
     * Creates a translatable chat message component using a custom translation key suffix.
     *
     * @param modId the mod ID used to construct the translation key
     * @param name  the custom name to append to the translation key (typically an item or block name)
     * @return a translatable {@link MutableComponent} for the given name's description
     */
    public static MutableComponent setMessageComponent(String modId, String name) {
        return Component.translatable(setLangTag("message", modId, name + ".desc"));
    }


    /**
     * Creates a translatable and formatted chat message component using a custom translation key suffix.
     *
     * @param modId      the mod ID used to construct the translation key
     * @param name       the custom name to append to the translation key
     * @param formatting the {@link ChatFormatting} style to apply to the message
     * @return a translatable {@link MutableComponent} with the specified formatting
     */
    public static MutableComponent setMessageComponent(String modId, String name, ChatFormatting formatting) {
        return setMessageComponent(modId, name).withStyle(formatting);
    }


    /**
     * Creates a translatable text component for a mod-specific language tag
     * using the provided mod ID.
     *
     * @param modId the mod identifier to include
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "{modId}.{suffix}"
     */
    public static MutableComponent setLangComponent(String modId, String suffix) {
        return Component.translatable(modId + separator + suffix);
    }


    /**
     * Creates a translatable text component for a mod-specific language key,
     * using the provided mod ID and suffix, with the given text formatting.
     *
     * @param modId     the mod identifier to include in the translation key
     * @param suffix    the suffix for the language key (resulting key: "modId.suffix")
     * @param formatting the {@link ChatFormatting} style to apply to the text
     * @return a {@link MutableComponent} that will translate the key "{modId}.{suffix}"
     *          and apply the specified style
     */
    public static MutableComponent setLangComponent(String modId, String suffix, ChatFormatting formatting) {
        return Component.translatable(modId + separator + suffix).withStyle(formatting);
    }


    /**
     * Creates a translatable component for a mod’s creative tab label.
     * <p>
     * This method constructs a translatable text component using the translation key
     * <code>"itemGroup.&lt;modId&gt;_tap"</code>. This key can be localized in language files to display the
     * appropriate name for the mod’s item group (creative tab).
     * </p>
     *
     * @param modId  The mod identifier used to build the translation key
     * @return       A {@link net.minecraft.network.chat.MutableComponent} representing the translatable text for
     *               the creative tab, which can be resolved at runtime to the localized name.
     */
    public static MutableComponent setTabLangComponent(String modId) {
        return Component.translatable("itemGroup." + modId + "_tap");
    }


    /**
     * Returns either the normal or expanded tooltip component depending on
     * whether the Shift key is held down.
     *
     * @param normal the tooltip to show when Shift is not pressed
     * @param expanded the tooltip to show when Shift is pressed
     * @return {@code expanded} if Shift is down, otherwise {@code normal}
     */
    public static Component setExpandableTooltip(Component normal, Component expanded) {
        return Screen.hasShiftDown() ? expanded : normal;
    }


    /**
     * Converts a decimal number to a percentage value.
     *
     * <p>If the input value is greater than 1, it returns 100. Otherwise, it multiplies the decimal by 100
     * to convert it to a percentage.</p>
     *
     * @param decimal the decimal value to convert (expected range: 0.0 to 1.0)
     * @return the corresponding percentage value, or 100 if the input exceeds 1
     */
    public static double ConvertDecimalToPercentage(double decimal) {
        return decimal > 1 ? 100 : decimal * 100;
    }


    /**
     * Returns a {@link ChatFormatting} color associated with a given log level.
     *
     * <p>This method maps common {@link Level} values to specific colors for debugging output:</p>
     * <ul>
     *   <li>{@code ERROR} → {@code RED}</li>
     *   <li>{@code FATAL} → {@code DARK_RED}</li>
     *   <li>{@code DEBUG} and {@code TRACE} → {@code AQUA}</li>
     *   <li>{@code WARN} → {@code RED}</li>
     *   <li>Others → {@code YELLOW}</li>
     * </ul>
     *
     * @param level the log level to map
     * @return the corresponding {@link ChatFormatting} color for the level
     */
    public static ChatFormatting getDebugColor(Level level) {
        if (level == Level.ERROR) {
            return ChatFormatting.RED;

        } else if (level == Level.FATAL) {
            return ChatFormatting.DARK_RED;

        } else if (level == Level.DEBUG) {
            return ChatFormatting.AQUA;

        } else if (level == Level.TRACE) {
            return ChatFormatting.AQUA;

        } else if (level == Level.WARN) {
            return ChatFormatting.RED;

        } else {
            return ChatFormatting.YELLOW;
        }
    }


    /**
     * Returns a {@link ChatFormatting} color based on the category of a given {@link MobEffect}.
     *
     * <p>Category-to-color mapping:</p>
     * <ul>
     *   <li>{@code HARMFUL} → {@code DARK_RED}</li>
     *   <li>{@code BENEFICIAL} → {@code GREEN}</li>
     *   <li>Neutral or other categories → {@code YELLOW}</li>
     * </ul>
     *
     * @param effect the mob effect to evaluate
     * @return the corresponding {@link ChatFormatting} color based on the effect's category
     */
    public static ChatFormatting getEffectCategoryColor(MobEffect effect) {
        if (effect.getCategory().equals(MobEffectCategory.HARMFUL)) {
            return ChatFormatting.DARK_RED;

        } else if (effect.getCategory().equals(MobEffectCategory.BENEFICIAL)) {
            return ChatFormatting.GREEN;

        } else {
            return ChatFormatting.YELLOW;
        }
    }

}
