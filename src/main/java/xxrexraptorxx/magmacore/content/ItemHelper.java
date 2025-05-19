package xxrexraptorxx.magmacore.content;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xxrexraptorxx.magmacore.utils.MiscUtils;

public class ItemHelper {

    /**
     * Creates a {@link ResourceKey} for an {@link Item} given a mod ID and item name.
     *
     * @param modId the namespace (mod identifier) of the item
     * @param name  the path/name of the item resource
     * @return a {@code ResourceKey<Item>} pointing to the given item in the registry
     */
    public static ResourceKey<Item> getKey(String modId, String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    /**
     * Retrieves the path (resource name) of the given item.
     *
     * @param item the item whose registry key to query
     * @return the path portion of the item's {@link ResourceLocation} (e.g. "iron_ingot")
     */
    public static String getPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }


    /**
     * Retrieves the path (resource name) of the item inside the given {@link ItemStack}.
     *
     * @param stack the item stack from which to extract the item's resource path
     * @return the path portion of the item's {@link ResourceLocation}
     */
    public static String getPath(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
    }


    /**
     * Creates a {@link ResourceKey} for an {@link Item} using the detected mod ID and the given name.
     *
     * @param name the path/name of the item resource
     * @return a {@code ResourceKey<Item>} pointing to the item under the current mod's namespace
     */
    public static ResourceKey<Item> getPath(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    /**
     * Constructs a {@link ResourceLocation} under the current mod's namespace for the given resource name.
     *
     * @param name the path/name of the resource
     * @return a {@code ResourceLocation} with namespace = detected mod ID and the given path
     */
    public static ResourceLocation getLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name);
    }


    /**
     * Retrieves the path (resource name) of the given item.
     * Alias for {@link #getPath(Item)}.
     *
     * @param item the item whose name to retrieve
     * @return the path portion of the item's registry key
     */
    public static String getName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }


    /**
     * Retrieves the path (resource name) of the item inside the given {@link ItemStack}.
     * Alias for {@link #getPath(ItemStack)}.
     *
     * @param stack the item stack to query
     * @return the path portion of the item's registry key
     */
    public static String getName(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
    }


    /**
     * Retrieves the namespace (mod ID) of the given item.
     *
     * @param item the item whose namespace to retrieve
     * @return the namespace portion of the item's {@link ResourceLocation} (e.g. "minecraft", "mymod")
     */
    public static String getId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getNamespace();
    }


    /**
     * Retrieves the namespace (mod ID) of the item inside the given {@link ItemStack}.
     *
     * @param stack the item stack to query
     * @return the namespace portion of the item's {@link ResourceLocation}
     */
    public static String getId(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
    }
}
