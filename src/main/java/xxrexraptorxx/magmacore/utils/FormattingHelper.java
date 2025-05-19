package xxrexraptorxx.magmacore.utils;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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
     * Constructs a language tag prefix using the detected mod ID.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @return the concatenated prefix in the form "{prefix}.{modId}."
     */
    public static String setLangTagPrefix(String prefix) {
        return prefix + "." + MiscUtils.detectModId() + ".";
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
     * Constructs a full language tag using the detected mod ID.
     *
     * @param prefix the base prefix to use (e.g. "item" or "block")
     * @param suffix the suffix to append (e.g. "name" or "description")
     * @return the language tag in the form "{prefix}.{modId}.{suffix}"
     */
    public static String setLangTag(String prefix, String suffix) {
        return prefix + "." + MiscUtils.detectModId() + "." + suffix;
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
     * Creates a translatable text component for a mod-specific language tag.
     * Uses the detected mod ID.
     *
     * @param prefix the base prefix for the language key
     * @param suffix the suffix for the language key
     * @return a {@link MutableComponent} that will translate the key "{prefix}.{modId}.{suffix}"
     */
    public static MutableComponent setModLangComponent(String prefix, String suffix) {
        return Component.translatable(setLangTag(prefix, suffix));
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

}
