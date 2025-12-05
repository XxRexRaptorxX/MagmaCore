package xxrexraptorxx.magmacore.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xxrexraptorxx.magmacore.main.MagmaCore;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class RecipeUtils extends RecipeProvider {

    public RecipeUtils(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }


    protected final void helmetRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, result.get()).pattern("###").pattern("# #").define('#', material.get()).unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void chestplateRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, result.get()).pattern("# #").pattern("###").pattern("###").define('#', material.get())
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void leggingsRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, result.get()).pattern("###").pattern("# #").pattern("# #").define('#', material.get())
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void bootsRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, result.get()).pattern("# #").pattern("# #").define('#', material.get()).unlockedBy("has_item", has(material.get()))
                .save(consumer);
    }


    protected final void pickaxeRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, result.get()).pattern("###").pattern(" X ").pattern(" X ").define('#', material.get()).define('X', handle)
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void swordRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, result.get()).pattern("#").pattern("#").pattern("X").define('#', material.get()).define('X', handle)
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void axeRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, result.get()).pattern("##").pattern("#X").pattern(" X").define('#', material.get()).define('X', handle)
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void shovelRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, result.get()).pattern("#").pattern("X").pattern("X").define('#', material.get()).define('X', handle)
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void hoeRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, result.get()).pattern("##").pattern(" X").pattern(" X").define('#', material.get()).define('X', handle)
                .unlockedBy("has_item", has(material.get())).save(consumer);
    }


    protected final void mossyRecipes(RecipeOutput consumer, Supplier<? extends Block> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get()).requires(input.get()).requires(Blocks.MOSS_BLOCK)
                .unlockedBy(getHasName(input.get()), has(input.get())).group("mossy").save(consumer, BuiltInRegistries.BLOCK.getKey(result.get()) + "_with_moss");

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get()).requires(input.get()).requires(Blocks.VINE)
                .unlockedBy(getHasName(input.get()), has(input.get())).group("mossy").save(consumer, BuiltInRegistries.BLOCK.getKey(result.get()) + "_with_vines");
    }


    protected final void slabRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 6).pattern("###").define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void slabDecraftingRecipe(RecipeOutput consumer, Supplier<? extends Block> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate decrafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 2).pattern("#").pattern("#").define('#', input.get())
                .unlockedBy(getHasName(result.get()), has(result.get())).save(consumer, BuiltInRegistries.BLOCK.getKey(result.get()) + "_from_slabs");
    }


    protected final void stairsRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 4).pattern("#  ").pattern("## ").pattern("###").define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void wallRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 6).pattern("###").pattern("###").define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void polishedStoneRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 4).pattern("##").pattern("##").define('#', input.get())
                .unlockedBy(getHasName(result.get()), has(result.get())).save(consumer);
    }


    protected final void bricksRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 4).pattern("##").pattern("##").define('#', input.get())
                .unlockedBy(getHasName(result.get()), has(result.get())).save(consumer);
    }


    protected final void pillarRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 2).pattern("#").pattern("#").define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void buttonRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).pattern("#").define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void leverRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).pattern("I").pattern("#").define('I', Tags.Items.RODS_WOODEN).define('#', input.get())
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer);
    }


    protected final void fenceRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input, Supplier<? extends ItemLike> secondInput) {
        MagmaCore.LOGGER.info("Generate crafting recipe for " + getItemName(result.get().asItem()));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).pattern("I=I").pattern("I=I").define('I', input.get()).define('=', secondInput.get())
                .unlockedBy(getHasName(secondInput.get()), has(secondInput.get())).save(consumer);
    }


    protected final void stoneCuttingRecipe(RecipeOutput consumer, Supplier<? extends Block> result, int resultCount, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate stonecutting recipe for " + getItemName(result.get().asItem()));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result.get(), resultCount)
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer, RecipePaths.STONE_CUTTING.getFor(result.get()));
    }


    protected final void stoneCuttingRecipe(RecipeOutput consumer, Supplier<? extends Block> result, int resultCount, TagKey<Item> input) {
        MagmaCore.LOGGER.info("Generate stonecutting recipe for " + getItemName(result.get().asItem()));
        SingleItemRecipeBuilder.stonecutting(convertTag(input), RecipeCategory.BUILDING_BLOCKS, result.get(), resultCount).unlockedBy("has_" + input, has(input)).save(consumer,
                RecipePaths.STONE_CUTTING.getFor(result.get()));
    }


    protected final void simpleSmeltingRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> input) {
        MagmaCore.LOGGER.info("Generate smelting recipe for " + getItemName(result.get().asItem()));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, result.get(), 0.1F, 200)
                .unlockedBy(getHasName(input.get()), has(input.get())).save(consumer, RecipePaths.SMELTING.getFor(result.get()));
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput, TagKey<Item> secondInput,
            String group) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput)
                .unlockedBy(getHasName(firstInput.get()), has(firstInput.get())).group(group).save(consumer);
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput, TagKey<Item> secondInput) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput)
                .unlockedBy(getHasName(firstInput.get()), has(firstInput.get())).save(consumer);
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput,
            Supplier<? extends ItemLike> secondInput, String group) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput.get())
                .unlockedBy(getHasName(secondInput.get()), has(secondInput.get())).group(group).save(consumer);
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput,
            Supplier<? extends ItemLike> secondInput) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput.get())
                .unlockedBy(getHasName(secondInput.get()), has(secondInput.get())).save(consumer);
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput,
            Supplier<? extends ItemLike> secondInput, Supplier<? extends ItemLike> thirdInput, String group) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput.get()).requires(thirdInput.get())
                .unlockedBy(getHasName(secondInput.get()), has(secondInput.get())).group(group).save(consumer);
    }


    protected final void simpleShapelessRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> firstInput,
            Supplier<? extends ItemLike> secondInput, Supplier<? extends ItemLike> thirdInput) {
        MagmaCore.LOGGER.info("Generate shapeless crafting recipe for " + getItemName(result.get().asItem()));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(firstInput.get()).requires(secondInput.get()).requires(thirdInput.get())
                .unlockedBy(getHasName(secondInput.get()), has(secondInput.get())).save(consumer);
    }


    protected final void colorRecipes(RecipeOutput consumer, Supplier<? extends ItemLike> result, TagKey<Item> input) {
        MagmaCore.LOGGER.info("Generate color recipes for " + getItemName(result.get().asItem()));

        for (DyeColor color : DyeColor.values()) {
            MagmaCore.LOGGER.info("With color: " + color);

            Item dye = DyeItem.byColor(color);
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(input).requires(dye).group(input.toString()).save(consumer);
        }
    }


    protected final void dyeRecipe(RecipeOutput consumer, Supplier<? extends ItemLike> result, TagKey<Item> input, TagKey<Item> dye) {
        MagmaCore.LOGGER.info("Generate dye recipe for " + getItemName(result.get().asItem()));

        ResourceLocation location = BuiltInRegistries.ITEM.getKey(result.get().asItem());

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, result.get(), 1).requires(input).requires(dye).unlockedBy("has_" + input, has(input))
                .group(location.getPath() + "_dyed").save(consumer, location + "_from_dye");
    }


    public final void generateSlabRecipes(RecipeOutput consumer, String modId) {
        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

            if (id.getNamespace().equals(modId)) {
                if (BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(id + "_slab")).isPresent()) {
                    MagmaCore.LOGGER.info("Create slab recipes for " + id);

                    Block slab = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(id + "_slab")).get();

                    slabRecipe(consumer, () -> slab, () -> block);
                    slabDecraftingRecipe(consumer, () -> block, () -> slab);
                    stoneCuttingRecipe(consumer, () -> slab, 2, () -> block);
                }
            }
        }
    }


    protected static Block getBlockOrThrow(String namespace, String path) {
        ResourceLocation location = ResourceLocation.parse(namespace + ":" + path);
        Optional<Holder.Reference<Block>> holder = BuiltInRegistries.BLOCK.get(location);
        return holder.map(Holder::value).orElseThrow(() -> new IllegalStateException("Missing required Block in registry: " + location));
    }


    protected static Item getItemOrThrow(String namespace, String path) {
        ResourceLocation location = ResourceLocation.parse(namespace + ":" + path);
        Optional<Holder.Reference<Item>> holder = BuiltInRegistries.ITEM.get(location);
        return holder.map(Holder::value).orElseThrow(() -> new IllegalStateException("Missing required Item in registry: " + location));
    }


    protected static Ingredient convertTag(TagKey<Item> tag) {
        return Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(tag));
    }

}
