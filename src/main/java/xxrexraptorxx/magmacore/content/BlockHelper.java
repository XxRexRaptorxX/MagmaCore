package xxrexraptorxx.magmacore.content;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.magmacore.utils.MiscUtils;

public class BlockHelper {

    /**
     * Creates a {@link ResourceKey} for a {@link Block} given a mod ID and block name.
     *
     * @param modId the namespace (mod identifier) for the block
     * @param name  the path/name of the block resource
     * @return a {@code ResourceKey<Block>} pointing to the given block in the registry
     */
    public static ResourceKey<Block> getKey(String modId, String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    /**
     * Retrieves the path (resource name) of the given block.
     *
     * @param block the block whose registry key to query
     * @return the path portion of the block's {@link ResourceLocation} (e.g. "stone", "oak_planks")
     */
    public static String getPath(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }


    /**
     * Retrieves the path (resource name) of the item inside the given {@link ItemStack}.
     *
     * @param stack the item stack from which to extract the item's resource path
     * @return the resource path of the item (e.g. "diamond_sword")
     */
    public static String getPath(ItemStack stack) {
        return ItemHelper.getPath(stack.getItem());
    }


    /**
     * Creates a {@link ResourceKey} for a {@link Block} using the detected mod ID and the given name.
     *
     * @param name the path/name of the block resource
     * @return a {@code ResourceKey<Block>} pointing to the block under the current mod's namespace
     */
    public static ResourceKey<Block> getPath(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    /**
     * Constructs a {@link ResourceLocation} under the current mod's namespace for the given name.
     *
     * @param name the path/name of the resource
     * @return a {@code ResourceLocation} with namespace = detected mod ID and the given path
     */
    public static ResourceLocation getLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name);
    }


    /**
     * Retrieves the path (resource name) of the given block.
     * Alias for {@link #getPath(Block)}.
     *
     * @param block the block whose name to retrieve
     * @return the path portion of the block's registry key
     */
    public static String getName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }


    /**
     * Retrieves the name (resource path) of the item inside the given {@link ItemStack}.
     * Alias for {@link ItemHelper#getName}.
     *
     * @param stack the item stack to query
     * @return the resource path of the item (e.g. "iron_ingot")
     */
    public static String getName(ItemStack stack) {
        return ItemHelper.getName(stack.getItem());
    }


    /**
     * Retrieves the namespace (mod ID) of the given block.
     *
     * @param block the block whose namespace to retrieve
     * @return the namespace portion of the block's {@link ResourceLocation} (e.g. "minecraft", "mymod")
     */
    public static String getId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
    }


    /**
     * Retrieves the namespace (mod ID) of the item inside the given {@link ItemStack}.
     *
     * @param stack the item stack to query
     * @return the namespace portion of the item's {@link ResourceLocation}
     */
    public static String getId(ItemStack stack) {
        return ItemHelper.getId(stack.getItem());
    }


    /**
     * Checks whether the given {@link Block} and {@link Item} share the same resource path.
     * Useful for identifying block-item pairs (e.g. block and its corresponding item form).
     *
     * @param block the block to compare
     * @param item  the item to compare
     * @return {@code true} if both have identical resource paths, {@code false} otherwise
     */
    public static boolean isMatching(Block block, Item item) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
}
