package xxrexraptorxx.magmacore.compat;

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.client.BuiltinClientPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import xxrexraptorxx.magmacore.main.MagmaCore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class REIIntegrationHelper {

    private static final List<Consumer<DisplayRegistry>> actions = new ArrayList<>();


    public static void enqueue(List<ItemStack> stacks, Component desc) {
        actions.add(registry -> {
            try {
                var plugin = BuiltinClientPlugin.getInstance();
                EntryIngredient entry = EntryIngredients.ofItemStacks(stacks);
                plugin.registerInformation(entry, Component.empty(), list -> {
                    list.add(desc);

                    return list;
                });

            } catch (Exception e) {
                MagmaCore.LOGGER.error("REI Integration failed: " + e.getMessage());
            }
        });
    }


    public static void apply(DisplayRegistry registry) {
        actions.forEach(action -> action.accept(registry));
        actions.clear();
    }
}