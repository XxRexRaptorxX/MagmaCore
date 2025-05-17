package xxrexraptorxx.magmacore.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemHelper {

    public static ResourceKey<Item> getKey(String modId, String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    public static String getKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }


    public static String getKey(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
    }


    public static String getName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }


    public static String getName(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
    }


    public static String getId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getNamespace();
    }


    public static String getId(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
    }
}
