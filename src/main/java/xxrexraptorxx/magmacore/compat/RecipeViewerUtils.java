package xxrexraptorxx.magmacore.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.magmacore.content.ItemHelper;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.utils.FormattingHelper;
import xxrexraptorxx.magmacore.utils.MiscUtils;

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
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("ItemStack cannot be empty");
        }

        String name = ItemHelper.getName(stack);
        registerListInfo(modId, Collections.singletonList(stack), name);
    }


    /**
     * Registers integration info for an item.
     */
    public static void registerItemInfo(String modId, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        registerItemInfo(modId, new ItemStack(item));
    }



    /**
     * Registers integration info for a block.
     */
    public static void registerItemInfo(String modId, Block block) {
        if (block == null) {
            throw new IllegalArgumentException("Block cannot be null");
        }
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
        if (list.stream().anyMatch(ItemStack::isEmpty)) {
            throw new IllegalArgumentException("List contains empty ItemStacks");
        }

        Component description = FormattingHelper.setModLangComponent("message", modId, name + ".jei_desc");
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(modId, "info/" + name);

        try {
            if (MiscUtils.isModLoaded("jei"))
                JEIIntegrationHelper.enqueue(list, description);
        } catch (Exception e) {
            MagmaCore.LOGGER.error("JEI Integration failed: " + e.getMessage());
        }

        try {
            if (MiscUtils.isModLoaded("roughlyenoughitems"))
                REIIntegrationHelper.enqueue(list, description);
        } catch (Exception e) {
            MagmaCore.LOGGER.error("REI Integration failed: " + e.getMessage());
        }

        try {
            if (MiscUtils.isModLoaded("emi"))
                EMIIntegrationHelper.enqueue(list, description, recipeId);
        } catch (Exception e) {
            MagmaCore.LOGGER.error("EMI Integration failed: " + e.getMessage());
        }
    }
}