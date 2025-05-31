package xxrexraptorxx.magmacore.config;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.utils.FormattingHelper;

import javax.annotation.Nullable;

public class ConfigHelper {

    /**
     * Sets a configuration category on the given builder, pushing a new comment header.
     *
     * @param builder the {@link ModConfigSpec.Builder} to configure
     * @param name the category name; used both as the push key and to generate a comment
     */
    public static void setCategory(ModConfigSpec.Builder builder, String name) {
        builder.push(name).comment(Character.toUpperCase(name.charAt(0)) + name.substring(1));
    }


    /**
     * Sets a configuration category on the given builder, pushing a new comment header and Magma Core language tag.
     *
     * @param builder the {@link ModConfigSpec.Builder} to configure
     * @param name the category name; used both as the push key and to generate a comment
     */
    public static void setCoreCategory(ModConfigSpec.Builder builder, String name) {
        builder.push(name).comment(Character.toUpperCase(name.charAt(0)) + name.substring(1)).translation(FormattingHelper.setCoreTagPrefix("configuration." + name));
    }


    /**
     * Registers the in-game configuration screen factory for this mod.
     *
     * @param container the {@link ModContainer} of the mod
     */
    @OnlyIn(Dist.CLIENT)
    public static void registerIngameConfig(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    /**
     * Registers all provided configuration specs for this mod with the loader.
     * Optionally places config files in a subfolder matching the mod ID.
     *
     * @param container the {@link ModContainer} of the mod
     * @param modId the mod identifier, used for file naming and folder paths
     * @param withFolder if true, config files are placed in a folder named after the mod ID
     * @param serverConfig the server-side config spec, or null if not used
     * @param clientConfig the client-side config spec, or null if not used
     * @param commonConfig the common config spec, or null if not used
     * @param startupConfig the startup config spec, or null if not used
     */
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


    /**
     * Convenience overload for registering only server and client configs.
     *
     * @param container the {@link ModContainer} of the mod
     * @param modId the mod identifier, used for file naming and folder paths
     * @param withFolder if true, config files are placed in a folder named after the mod ID
     * @param serverConfig the server-side config spec, or null if not used
     * @param clientConfig the client-side config spec, or null if not used
     */
    public static void registerConfigs(ModContainer container, String modId, boolean withFolder, @Nullable ModConfigSpec serverConfig, @Nullable ModConfigSpec clientConfig) {
        registerConfigs(container, modId, withFolder, serverConfig, clientConfig, null, null);
    }


    /**
     * Checks whether the global debug mode is enabled.
     *
     * This method returns {@code true} if either the MagmaCore debug mode is enabled
     * or the mod-specific debug mode is passed as {@code true}.
     *
     * @param modSpecificDebugMode a mod-specific flag indicating whether debug mode should be enabled
     * @return {@code true} if debug mode is enabled globally or for the specific mod; {@code false} otherwise
     */
    public static boolean isDebugGlobalModeEnabled(boolean modSpecificDebugMode) {
        return Config.getDebugMode() || modSpecificDebugMode;
    }
}
