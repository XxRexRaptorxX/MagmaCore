package xxrexraptorxx.magmacore.content;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.magmacore.utils.MiscUtils;

public class TagHelper {

    public static TagKey<Item> createItemTag(String id, String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(id, name));
    }


    public static TagKey<Item> createModItemTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    public static TagKey<Item> createCItemTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }


    public static TagKey<Block> createBlockTag(String id, String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(id, name));
    }


    public static TagKey<Block> createModBlockTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MiscUtils.detectModId(), name));
    }


    public static TagKey<Block> createCBlockTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
