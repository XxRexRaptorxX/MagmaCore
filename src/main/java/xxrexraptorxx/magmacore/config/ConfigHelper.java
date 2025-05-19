package xxrexraptorxx.magmacore.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import xxrexraptorxx.magmacore.main.MagmaCore;

import javax.annotation.Nullable;

public class ConfigHelper {

    public static void setCategory(ModConfigSpec.Builder builder, String name) {
        builder.push(name).comment(Character.toUpperCase(name.charAt(0)) + name.substring(1));
    }


    public static void registerIngameConfig(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }


    public static void registerConfigs(ModContainer container, String modId, boolean withFolder, @Nullable ModConfigSpec serverConfig, @Nullable ModConfigSpec clientConfig, @Nullable ModConfigSpec commonConfig, @Nullable ModConfigSpec startupConfig) {

        String path = modId;

        if (withFolder) {
            path = modId + "/" + modId;
        }

        if (serverConfig != null) container.registerConfig(ModConfig.Type.SERVER, serverConfig, path + "-server.toml");
        if (clientConfig != null) container.registerConfig(ModConfig.Type.CLIENT, clientConfig, path + "-client.toml");
        if (commonConfig != null) container.registerConfig(ModConfig.Type.COMMON, commonConfig, path + "-common.toml");
        if (startupConfig != null) container.registerConfig(ModConfig.Type.STARTUP, startupConfig, path + "-startup.toml");

        if (Config.getDebugMode()) MagmaCore.LOGGER.info("Configs registered for " + modId);
    }


    public static void registerConfigs(ModContainer container, String modId, boolean withFolder, @Nullable ModConfigSpec serverConfig, @Nullable ModConfigSpec clientConfig) {
        registerConfigs(container, modId, withFolder, serverConfig, clientConfig, null, null);
    }
}
