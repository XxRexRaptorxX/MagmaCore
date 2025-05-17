package xxrexraptorxx.magmacore.content;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xxrexraptorxx.magmacore.utils.MiscUtils;

public class ItemHelper {

    public static ResourceKey<Item> getKey(String modId, String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    public static String getPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }


    public static String getPath(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
    }


    public static ResourceKey<Item> getPath(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    public static ResourceLocation getLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name);
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
