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

    public static ResourceKey<Block> getKey(String modId, String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
    }


    public static String getPath(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }


    public static String getPath(ItemStack stack) {
        return ItemHelper.getPath(stack.getItem());
    }


    public static ResourceKey<Block> getPath(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    public static ResourceLocation getLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name);
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
