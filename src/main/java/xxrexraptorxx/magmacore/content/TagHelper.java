package xxrexraptorxx.magmacore.content;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xxrexraptorxx.magmacore.main.MagmaCore;

public class TagHelper {

    /**
     * Creates a {@link TagKey<Item>} for an item tag with the specified namespace and name.
     *
     * @param id   the namespace (mod or domain) for the tag
     * @param name the path/name of the tag
     * @return a {@code TagKey<Item>} representing the created item tag
     */
    public static TagKey<Item> createItemTag(String id, String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(id, name));
    }


    /**
     * Creates a {@link TagKey<Item>} for an item tag under the shared "c" namespace.
     * Commonly used for cross-mod compatibility tags (e.g., "c:ingots/iron").
     *
     * @param name the path/name of the tag
     * @return a {@code TagKey<Item>} representing the created item tag in the "c" namespace
     */
    public static TagKey<Item> createCItemTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }


    /**
     * Creates a {@link TagKey<Block>} for a block tag with the specified namespace and name.
     *
     * @param id   the namespace (mod or domain) for the tag
     * @param name the path/name of the tag
     * @return a {@code TagKey<Block>} representing the created block tag
     */
    public static TagKey<Block> createBlockTag(String id, String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(id, name));
    }


    /**
     * Creates a {@link TagKey<Block>} for a block tag under the shared "c" namespace.
     * Commonly used for cross-mod compatibility tags (e.g., "c:stone").
     *
     * @param name the path/name of the tag
     * @return a {@code TagKey<Block>} representing the created block tag in the "c" namespace
     */
    public static TagKey<Block> createCBlockTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }


    /**
     *  Converts the mining block tags to the old mining level format
     */
    public static int getMiningLevel(TagKey<Block> tag) {
        if (tag.equals(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)) {
            return 4;

        } else if (tag.equals(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)) {
            return 3;

        } else if (tag.equals(BlockTags.INCORRECT_FOR_IRON_TOOL)) {
            return 2;

        } else if (tag.equals(BlockTags.INCORRECT_FOR_STONE_TOOL)) {
            return 1;

        } else if (tag.equals(BlockTags.INCORRECT_FOR_WOODEN_TOOL) || tag.equals(BlockTags.INCORRECT_FOR_GOLD_TOOL)) {
            return 0;

        } else {
            MagmaCore.LOGGER.error("Unknown 'harvest'- block tag: " + tag.location());
            return 2;
        }
    }
}
