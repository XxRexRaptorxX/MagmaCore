package xxrexraptorxx.magmacore.datagen;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xxrexraptorxx.magmacore.utils.FormattingHelper;

public class LootUtils {

    /**
     * Sets the item's name using the default "item" lang prefix and the given translation key.
     *
     * @param name the translation key suffix (e.g., "my_item_name")
     * @return a {@link LootItemFunction.Builder} that applies the translated name to the item
     */
    public static LootItemFunction.Builder setItemName(String name) {
        return SetNameFunction.setName(FormattingHelper.setCoreLangComponent("item", name), SetNameFunction.Target.ITEM_NAME);
    }


    /**
     * Sets the item's name using the default "item" lang prefix and applies a specific text formatting.
     *
     * @param name      the translation key suffix
     * @param formatting the {@link ChatFormatting} style to apply (e.g., bold, italic)
     * @return a {@link LootItemFunction.Builder} that sets and formats the item's name
     */
    public static LootItemFunction.Builder setItemName(String name, ChatFormatting formatting) {
        return SetNameFunction.setName(FormattingHelper.setCoreLangComponent("item", name).withStyle(formatting), SetNameFunction.Target.ITEM_NAME);
    }


    /**
     * Sets the item's lore using the default "lore" lang prefix and the given translation key.
     *
     * @param name the translation key suffix for the lore text
     * @return a {@link LootItemFunction.Builder} that adds the translated lore line
     */
    public static LootItemFunction.Builder setLore(String name) {
        return new SetLoreFunction.Builder().addLine(FormattingHelper.setCoreLangComponent("lore", name));
    }


    /**
     * Sets the item's lore with a specified text formatting.
     *
     * @param name       the translation key suffix for the lore
     * @param formatting the {@link ChatFormatting} style to apply
     * @return a {@link LootItemFunction.Builder} that adds the formatted translated lore line
     */
    public static LootItemFunction.Builder setLore(String name, ChatFormatting formatting) {
        return new SetLoreFunction.Builder().addLine(FormattingHelper.setCoreLangComponent("lore", name).withStyle(formatting));
    }


    /**
     * Sets a randomized item count between a minimum and maximum value.
     *
     * @param min the minimum item count (inclusive)
     * @param max the maximum item count (inclusive)
     * @return a {@link LootItemFunction.Builder} that sets the count using a uniform distribution
     */
    public static LootItemFunction.Builder setCount(int min, int max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }


    /**
     * Sets a fixed item count.
     *
     * @param count the exact item count to set
     * @return a {@link LootItemFunction.Builder} that sets a constant count
     */
    public static LootItemFunction.Builder setCount(int count) {
        return SetItemCountFunction.setCount(ConstantValue.exactly(count));
    }


    /**
     * Sets random item damage using default bounds (0.2 to 0.7).
     *
     * @return a {@link LootItemFunction.Builder} that applies random damage
     */
    public static LootItemFunction.Builder setDamage() {
        return setDamage(0.2f, 0.7f);
    }


    /**
     * Sets item damage to a randomized value between the given bounds.
     *
     * @param min the minimum damage (0.0 = undamaged, 1.0 = fully damaged)
     * @param max the maximum damage
     * @return a {@link LootItemFunction.Builder} that applies damage in the specified range
     */
    public static LootItemFunction.Builder setDamage(float min, float max) {
        return SetItemDamageFunction.setDamage(UniformGenerator.between(min, max));
    }


    /**
     * Adds an enchantment to the item with level 1.
     *
     * @param enchantment the resource key of the enchantment
     * @param lookup      the registry lookup to resolve the enchantment holder
     * @return a {@link LootItemFunction.Builder} that applies the enchantment at level 1
     */
    public static LootItemFunction.Builder setEnchantment(ResourceKey<Enchantment> enchantment, HolderLookup.RegistryLookup<Enchantment> lookup) {
        return setEnchantment(enchantment, 1, lookup);
    }


    /**
     * Adds an enchantment to the item with a fixed level.
     *
     * @param enchantment the resource key of the enchantment
     * @param level       the level to apply
     * @param lookup      the registry lookup to resolve the enchantment holder
     * @return a {@link LootItemFunction.Builder} that applies the enchantment with the specified level
     */
    public static LootItemFunction.Builder setEnchantment(ResourceKey<Enchantment> enchantment, Integer level, HolderLookup.RegistryLookup<Enchantment> lookup) {
        return new SetEnchantmentsFunction.Builder(false).withEnchantment(lookup.getOrThrow(enchantment), ConstantValue.exactly(level));
    }


    /**
     * Adds an enchantment to the item with a randomized level between min and max.
     *
     * @param enchantment the resource key of the enchantment
     * @param minLevel    the minimum enchantment level
     * @param maxLevel    the maximum enchantment level
     * @param lookup      the registry lookup to resolve the enchantment holder
     * @return a {@link LootItemFunction.Builder} that applies the enchantment with a level in the given range
     */
    public static LootItemFunction.Builder setEnchantment(ResourceKey<Enchantment> enchantment, Integer minLevel, Integer maxLevel, HolderLookup.RegistryLookup<Enchantment> lookup) {
        return new SetEnchantmentsFunction.Builder(false).withEnchantment(lookup.getOrThrow(enchantment), UniformGenerator.between(minLevel, maxLevel));
    }


    /**
     * Applies an attribute modifier to the item.
     *
     * @param attribute the attribute to modify (e.g. {@code Attributes.ATTACK_DAMAGE})
     * @param operation the operation type (e.g. {@code ADDITION}, {@code MULTIPLY_BASE})
     * @param value     the numeric value provider for the modifier
     * @param slots     the equipment slots the modifier applies to
     * @return a {@link LootItemFunction.Builder} that adds the specified attribute modifier
     */
    public static LootItemFunction.Builder setAttribute(Holder<Attribute> attribute, AttributeModifier.Operation operation, NumberProvider value, EquipmentSlotGroup slots) {
        return SetAttributesFunction.setAttributes().withModifier(new SetAttributesFunction.ModifierBuilder(ResourceLocation.parse(attribute.getRegisteredName()), attribute, operation, value).forSlot(slots));
    }


    /**
     * Sets whether the item should display the enchantment glint (shiny visual effect).
     *
     * @param value {@code true} to show the glint, {@code false} to hide it
     * @return a {@link LootItemFunction.Builder} that overrides the glint display
     */
    public static LootItemFunction.Builder setGlint(boolean value) {
        return SetComponentsFunction.setComponent(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, value);
    }


    /**
     * Applies an armor trim to the item using the specified material and pattern.
     *
     * @param material        the resource key of the trim material
     * @param pattern         the resource key of the trim pattern
     * @param materialLookup the registry lookup for trim materials
     * @param patternLookup   the registry lookup for trim patterns
     * @return a {@link LootItemFunction.Builder} that applies the armor trim component
     */
    public static LootItemFunction.Builder setArmorTrim(ResourceKey<TrimMaterial> material, ResourceKey<TrimPattern> pattern, HolderLookup.RegistryLookup<TrimMaterial> materialLookup, HolderLookup.RegistryLookup<TrimPattern> patternLookup) {
        return SetComponentsFunction.setComponent(DataComponents.TRIM, new ArmorTrim(materialLookup.getOrThrow(material), patternLookup.getOrThrow(pattern)));
    }


    /**
     * Sets the dyed color of the item using an RGB integer value.
     *
     * @param rgb the RGB color value to apply (e.g. {@code 0xFF0000} for red)
     * @return a {@link LootItemFunction.Builder} that sets the dyed color component
     */
    public static LootItemFunction.Builder setColor(int rgb) {
        return SetComponentsFunction.setComponent(DataComponents.DYED_COLOR, new DyedItemColor(rgb));
    }
}
