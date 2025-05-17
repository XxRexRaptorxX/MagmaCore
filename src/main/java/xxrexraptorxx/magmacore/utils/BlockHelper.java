package xxrexraptorxx.magmacore.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BlockHelper {

    public static ResourceKey<Block> getKey(String modId, String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    public static String getKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }


    public static String getKey(ItemStack stack) {
        return ItemHelper.getKey(stack.getItem());
    }


    public static String getName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }


    public static String getName(ItemStack stack) {
        return ItemHelper.getName(stack.getItem());
    }


    public static String getId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
    }


    public static String getId(ItemStack stack) {
        return ItemHelper.getId(stack.getItem());
    }


    public static boolean isMatching(Block block, Item item) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath().equals(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
}
