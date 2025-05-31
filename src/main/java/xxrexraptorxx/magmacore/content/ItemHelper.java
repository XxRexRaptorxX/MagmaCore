package xxrexraptorxx.magmacore.content;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Set;

public class ItemHelper {

    public static final Set<String> TOOL_KEYWORDS = Set.of("pickaxe", "axe", "sword", "shovel", "hoe");


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
    public static ResourceKey<Item> getPath(String modId, String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    /**
     * Constructs a {@link ResourceLocation} under the current mod's namespace for the given resource name.
     *
     * @param name the path/name of the resource
     * @return a {@code ResourceLocation} with namespace = detected mod ID and the given path
     */
    public static ResourceLocation getLocation(String modId, String name){
        return ResourceLocation.fromNamespaceAndPath(modId, name);
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


    /**
     * Checks if the given path string indicates a tool item based on known tool-related keywords.
     *
     * @param path the string path of the item, typically derived from its registry name or ID
     * @return {@code true} if the path contains any known tool-related keyword (e.g., "pickaxe", "axe", etc.),
     *         {@code false} otherwise
     */
    public static boolean isToolType(String path) {
        return TOOL_KEYWORDS.stream().anyMatch(path::contains);
    }


    /**
     * Determines whether the given {@link Item} is considered a tool, optionally checking the item's name.
     *
     * <p>This method checks whether the item has a {@code TOOL} component. If {@code withNameCheck} is set to
     * {@code true}, it also verifies that the item's path (usually its registry ID) contains any known tool-related
     * keyword (e.g., "pickaxe", "axe", "sword", etc.).</p>
     *
     * @param item the item to check
     * @param withNameCheck if {@code true}, performs an additional name-based check; if {@code false} or {@code null},
     *                      only the presence of the tool component is considered
     * @return {@code true} if the item is considered a tool based on the specified checks; {@code false} otherwise
     */
    public static boolean isToolType(Item item, @Nullable Boolean withNameCheck) {
        boolean hasToolComponent = item.components().has(DataComponents.TOOL);

        if (Boolean.TRUE.equals(withNameCheck)) {
            String path = getPath(item);
            return hasToolComponent && TOOL_KEYWORDS.stream().anyMatch(path::contains);
        }

        return hasToolComponent;
    }

}
