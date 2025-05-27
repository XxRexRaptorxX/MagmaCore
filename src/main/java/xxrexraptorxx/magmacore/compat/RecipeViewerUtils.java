package xxrexraptorxx.magmacore.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.magmacore.content.ItemHelper;

import java.util.Collections;
import java.util.List;

public class RecipeViewerUtils {

    /**
     * Registers integration info entries (tooltips/descriptions) for a single {@link ItemStack}
     * in JEI, REI, and EMI under the specified mod ID.
     * <p>
     * This method automatically constructs the translation key and registers the
     * item stack with all supported integration systems.
     *
     * @param modId the mod ID used for generating the translation key and recipe ID
     * @param stack the item stack to register integration info for
     */
    public static void registerItemInfo(String modId, ItemStack stack) {
        String name = ItemHelper.getName(stack);

        registerListInfo(modId, Collections.singletonList(stack), name);
    }


    public static void registerItemInfo(String modId, Item item) {
        registerItemInfo(modId, new ItemStack(item));
    }


    public static void registerItemInfo(String modId, Block block) {
        registerItemInfo(modId, new ItemStack(block));
    }


    /**
     * Registers integration info entries (tooltips/descriptions) for a list of {@link ItemStack}s
     * in JEI, REI, and EMI under the specified mod ID.
     * <p>
     * It uses a shared translation key and recipe ID for all stacks in the list.
     *
     * @param modId the mod ID used for generating the translation key and recipe ID
     * @param list  a list of item stacks to register
     * @param name  the resource name used for the translation key and recipe path (e.g. "copper_ingot")
     */
    public static void registerListInfo(String modId, List<ItemStack> list, String name) {
        Component description = Component.translatable("message." + modId + name + "_jei_desc");
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(modId, "info/" + name);

        JEIIntegrationHelper.enqueue(list, description);
        REIIntegrationHelper.enqueue(list, description);
        EMIIntegrationHelper.enqueue(list, description, recipeId);
    }
}