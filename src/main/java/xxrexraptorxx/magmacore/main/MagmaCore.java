package xxrexraptorxx.magmacore.main;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.magmacore.config.Config;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage <a href="https://www.curseforge.com/minecraft/mc-mods/magma-core">...</a>
 **/
@Mod(References.MODID)
public class MagmaCore {

    public static final Logger LOGGER = LogManager.getLogger();


    public MagmaCore(IEventBus bus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG, References.MODID + "/" + References.MODID + "-server.toml");
        container.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG, References.MODID + "/" + References.MODID + "-client.toml");

        ModRegistry.register(References.MODID, References.NAME, References.URL);
    }


    @Mod(value = References.MODID, dist = Dist.CLIENT)
    public static class MagmaCoreClient {

        public MagmaCoreClient(ModContainer container) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}


