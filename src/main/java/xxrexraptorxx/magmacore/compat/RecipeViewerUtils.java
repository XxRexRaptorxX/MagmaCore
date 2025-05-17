package xxrexraptorxx.magmacore.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xxrexraptorxx.magmacore.content.ItemHelper;

import java.util.Collections;
import java.util.List;

public class RecipeViewerUtils {


    /**
     * Registers JEI, REI and EMI entries for item stacks
     */
    public static void registerItemInfo(String modId, ItemStack stack) {
        String name = ItemHelper.getName(stack);

        registerListInfo(modId, Collections.singletonList(stack), name);
    }


    /**
     * Registers JEI, REI and EMI entries for item stack lists
     */
    public static void registerListInfo(String modId, List<ItemStack> list, String name) {
        Component description = Component.translatable("message." + modId + name + "_jei_desc");
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(modId, "info/" + name);

        JEIIntegrationHelper.enqueue(list, description);
        REIIntegrationHelper.enqueue(list, description);
        EMIIntegrationHelper.enqueue(list, description, recipeId);
    }
}