package xxrexraptorxx.magmacore.main;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xxrexraptorxx.magmacore.config.Config;
import xxrexraptorxx.magmacore.config.ConfigHelper;

/**
 * @author XxRexRaptorxX (RexRaptor)
 * @projectPage <a href="https://www.curseforge.com/minecraft/mc-mods/magma-core">...</a>
 **/
@Mod(References.MODID)
public class MagmaCore {

    public static final Logger LOGGER = LogManager.getLogger();


    public MagmaCore(IEventBus bus, ModContainer container) {
        ConfigHelper.registerConfigs(container, References.MODID, true, Config.SERVER_CONFIG, Config.CLIENT_CONFIG, null, Config.STARTUP_CONFIG);
        ModRegistry.register(References.MODID, References.NAME, References.URL);
    }


    @Mod(value = References.MODID, dist = Dist.CLIENT)
    public static class MagmaCoreClient {

        public MagmaCoreClient(ModContainer container) {
            ConfigHelper.registerIngameConfig(container);
        }
    }
}


