package xxrexraptorxx.magmacore.utils;

import net.neoforged.fml.ModList;
import xxrexraptorxx.magmacore.main.MagmaCore;

public class MiscUtils {

    /**
     * Helper method for safe mod checking
     */
    public static boolean isModLoaded(String modId) {
        try {
            return ModList.get().isLoaded(modId);

        } catch (Exception e) {
            MagmaCore.LOGGER.error(e);
            return false;
        }
    }

}
