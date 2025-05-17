package xxrexraptorxx.magmacore.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModRegistry {

    private static final List<ModEntry> REGISTRY = new ArrayList<>();

    private ModRegistry() {}

    public static void register(String modId, String modName, String updateUrl) {
        REGISTRY.add(new ModEntry(modId, modName, updateUrl));
    }

    public static List<ModEntry> getEntries() {
        return Collections.unmodifiableList(REGISTRY);
    }

    public record ModEntry(String modId, String modName, String updateUrl) {}
}
