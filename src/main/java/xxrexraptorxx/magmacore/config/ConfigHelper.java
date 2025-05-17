package xxrexraptorxx.magmacore.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHelper {

    public static void setCategory(ModConfigSpec.Builder builder, String name) {
        builder.push(name).comment(Character.toUpperCase(name.charAt(0)) + name.substring(1));
    }
}
