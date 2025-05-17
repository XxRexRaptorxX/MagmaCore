package xxrexraptorxx.magmacore.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import xxrexraptorxx.magmacore.main.MagmaCore;

import java.util.function.Supplier;

public abstract class RecipeUtils extends RecipeProvider {

    public RecipeUtils(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected final void helmetItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void chestplateItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void leggingsItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void bootsItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void pickaxeItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, result.get())
                .pattern("###")
                .pattern(" X ")
                .pattern(" X ")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void swordItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, result.get())
                .pattern("#")
                .pattern("#")
                .pattern("X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void axeItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, result.get())
                .pattern("##")
                .pattern("#X")
                .pattern(" X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void shovelItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, result.get())
                .pattern("#")
                .pattern("X")
                .pattern("X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void hoeItem(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, result.get())
                .pattern("##")
                .pattern(" X")
                .pattern(" X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }
}
