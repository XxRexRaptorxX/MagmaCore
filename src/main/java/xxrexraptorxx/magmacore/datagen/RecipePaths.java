package xxrexraptorxx.magmacore.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;

public enum RecipePaths {

    STONE_CUTTING(":stonecutting/"),
    CRAFTING(":crafting/"),
    SMELTING(":smelting/"),
    BLASTING(":blasting/"),
    BREWING(":brewing/"),
    COOKING(":cooking/"),
    CRUSHING(":crushing/"),
    CAMPFIRE_COOKING(":campfire/"),
    ENCHANTING(":enchanting/"),
    TRANSFORMING(":transforming/"),
    DYEING(":dyeing/"),
    STAIRS(":stairs/"),
    SLABS(":slabs/");

    private final String suffix;

    RecipePaths(String suffix) {
        this.suffix = suffix;
    }


    public String getSuffix() {
        return suffix;
    }


    /**
     * Builds the full recipe path for the given item.
     * <p>
     * The result follows the format:
     *
     * <pre>
     *     <namespace> + suffix
     * </pre>
     *
     * where the namespace is taken from the item's registry key.
     *
     * @param itemLike the item or block implementing {@link net.minecraft.world.level.ItemLike}
     * @return the full recipe path for that item
     */
    public String getFor(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getNamespace() + suffix;
    }
}
