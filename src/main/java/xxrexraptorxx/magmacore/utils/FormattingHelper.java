package xxrexraptorxx.magmacore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;
import xxrexraptorxx.magmacore.main.References;

public class FormattingHelper {

    /**
     * Capitalizes each word in the given string. Words are delineated by spaces or underscores.
     * The first letter of each word is converted to uppercase and the rest to lowercase.
     *
     * @param string the input string to capitalize; may contain spaces or underscores
     * @return a new string with each word capitalized, or the original string if it is null or empty
     */
    public static String capitalizeWords(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

        string = string.replace('_', ' ');
        String[] words = string.split(" ");
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


    /**
     * Constructs a language tag prefix using the Magma Core id.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @return the concatenated prefix in the form "{prefix}.magmacore."
     */
    public static String setCoreTagPrefix(String prefix) {
        return prefix + "." + References.MODID + ".";
    }


    /**
     * Constructs a language tag prefix using the default namespace.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @return the concatenated prefix in the form "{prefix}.minecraft."
     */
    public static String setMCLangTagPrefix(String prefix) {
        return prefix + "." + ResourceLocation.DEFAULT_NAMESPACE + ".";
    }


    /**
     * Constructs a language tag prefix using the provided mod ID.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param modId the mod identifier to include in the prefix
     * @return the concatenated prefix in the form "{prefix}.{modId}."
     */
    public static String setLangTagPrefix(String prefix, String modId) {
        return prefix + "." + modId + ".";
    }


    /**
     * Constructs a full language tag using the MagmaCore id.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.magmacore.{suffix}"
     */
    public static String setCoreLangTag(String prefix, String suffix) {
        return prefix + "." + References.MODID + "." + suffix;
    }


    /**
     * Constructs a full language tag using the default namespace.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.magmacore.{suffix}"
     */
    public static String setMCLangTag(String prefix, String suffix) {
        return prefix + "." + ResourceLocation.DEFAULT_NAMESPACE + "." + suffix;
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
        return prefix + "." + modId + "." + suffix;
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
     * Creates a translatable text component for a mod-specific language tag
     * using the provided mod ID.
     *
     * @param modId the mod identifier to include
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "{modId}.{suffix}"
     */
    public static MutableComponent setLangComponent(String modId, String suffix) {
        return Component.translatable(modId + "." + suffix);
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


    public static ChatFormatting getColor(Level level) {
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

}
