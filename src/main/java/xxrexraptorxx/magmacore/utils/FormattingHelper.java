package xxrexraptorxx.magmacore.utils;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class FormattingHelper {

    public static String capitalizeWords(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

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


    public static String setLangTagPrefix(String prefix) {
        return prefix + "." + MiscUtils.detectModId() + ".";
    }


    public static String setLangTag(String prefix, String suffix) {
        return prefix + "." + MiscUtils.detectModId() + "." + suffix;
    }


    public static MutableComponent setModLangComponent(String prefix, String suffix) {
        return Component.translatable(setLangTag(prefix, suffix));
    }


    public static Component setExpandableTooltip(Component normal, Component expanded) {
        return Screen.hasShiftDown() ? expanded : normal;
    }

}
