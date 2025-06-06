package xxrexraptorxx.magmacore.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ConfigListHelper {

    /**
     * Validates a string with probability format: "id-probability"
     *
     * @param input The input string (e.g. "minecraft:bat-0.03")
     * @return true if format is valid, false otherwise
     */
    public static boolean hasValidProbabilityFormat(String input) {
        if (input == null || input.trim().isEmpty() || !input.contains("-") || !input.contains(":")) {
            return false;
        }

        try {
            int lastDashIndex = input.trim().lastIndexOf("-");

            if (lastDashIndex <= 0 || lastDashIndex >= input.trim().length() - 1) {
                return false;
            }

            String probabilityPart = input.trim().substring(lastDashIndex + 1);
            double probability = Double.parseDouble(probabilityPart);

            return probability >= 0.0 && probability <= 1.0;

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Validates a string with item amount format: "amount*namespace:item"
     *
     * @param input The input string (e.g. "3*minecraft:diamond")
     * @return true if format is valid, false otherwise
     */
    public static boolean hasValidCountFormat(String input) {
        if (input == null || input.trim().isEmpty() || !input.contains("*") || !input.contains(":")) {
            return false;
        }

        try {
            String trimmed = input.trim();
            int starIndex = trimmed.indexOf("*");

            if (starIndex <= 0 || starIndex >= trimmed.length() - 1) {
                return false;
            }

            String amountPart = trimmed.substring(0, starIndex);

            int amount = Integer.parseInt(amountPart);
            return amount > 0 && amount <= Item.DEFAULT_MAX_STACK_SIZE;

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Extracts the ID part from a probability string
     *
     * @param input The input string (e.g. "minecraft:bat-0.03")
     * @return the ID part (e.g. "minecraft:bat") or null if invalid
     */
    public static String extractId(String input) {
        if (!hasValidProbabilityFormat(input)) {
            return null;
        }

        int lastDashIndex = input.trim().lastIndexOf("-");

        return input.trim().substring(0, lastDashIndex);
    }


    /**
     * Extracts the probability from a probability string
     *
     * @param input The input string (e.g. "minecraft:bat-0.03")
     * @return the probability or -1 if invalid
     */
    public static double extractProbability(String input) {
        if (!hasValidProbabilityFormat(input)) {
            return -1.0;
        }

        try {
            int lastDashIndex = input.trim().lastIndexOf("-");
            String probabilityPart = input.trim().substring(lastDashIndex + 1);

            return Double.parseDouble(probabilityPart);

        } catch (Exception e) {
            return -1.0;
        }
    }


            // ENTITIES //
    /**
     * Validates entity string: "namespace:entity"
     */
    public static boolean isValidEntity(String entityString) {
        if (entityString == null || entityString.trim().isEmpty() || !entityString.contains(":")) {
            return false;
        }

        try {
            ResourceLocation location = ResourceLocation.parse(entityString.trim());
            return BuiltInRegistries.ENTITY_TYPE.containsKey(location);

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Validates entity spawning string: "namespace:entity-probability"
     */
    public static boolean isValidEntityWithProbability(String input) {
        if (!input.contains(":") || !hasValidProbabilityFormat(input)) {
            return false;
        }

        String entityId = extractId(input);
        return entityId != null && isValidEntity(entityId);
    }


            // BLOCKS //
    /**
     * Validates if a block string exists in Minecraft's registry
     *
     * @param blockString The block string to validate (e.g. "minecraft:stone")
     * @return true if the block exists, false otherwise
     */
    public static boolean isValidBlock(String blockString) {
        if (blockString == null || blockString.trim().isEmpty() || !blockString.contains(":")) {
            return false;
        }

        try {
            ResourceLocation location = ResourceLocation.parse(blockString.trim());
            return BuiltInRegistries.BLOCK.containsKey(location);

        } catch (Exception e) {
            return false;
        }
    }



    /**
     * Validates block with probability: "namespace:block-probability"
     */
    public static boolean isValidBlockWithProbability(String input) {
        if (!input.contains(":") || !hasValidProbabilityFormat(input)) {
            return false;
        }

        String blockId = extractId(input);
        return blockId != null && isValidBlock(blockId);
    }


                // ITEMS //
    /**
     * Validates item string: "namespace:item"
     */
    public static boolean isValidItem(String itemString) {
        if (itemString == null || itemString.trim().isEmpty() || !itemString.contains(":")) {
            return false;
        }

        try {
            ResourceLocation location = ResourceLocation.parse(itemString.trim());
            return BuiltInRegistries.ITEM.containsKey(location);

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Validates item with probability: "namespace:item-probability"
     */
    public static boolean isValidItemWithProbability(String input) {
        if (!hasValidProbabilityFormat(input)) {
            return false;
        }

        String itemId = extractId(input);
        return itemId != null && isValidItem(itemId);
    }


    /**
     * Validates loot entry format: "amount*namespace:item"
     *
     * @param input The loot string to validate (e.g. "3*minecraft:diamond")
     * @return true if valid, false otherwise
     */
    public static boolean isValidItemWithCount(String input) {
        if (!hasValidCountFormat(input)) {
            return false;
        }

        try {
            String trimmed = input.trim();
            int starIndex = trimmed.indexOf("*");

            if (starIndex <= 0 || starIndex >= trimmed.length() - 1) {
                return false;
            }

            String amountPart = trimmed.substring(0, starIndex);
            String itemPart = trimmed.substring(starIndex + 1);

            // Validate amount is a valid integer
            int amount = Integer.parseInt(amountPart);
            if (amount <= 0 || amount >= Item.DEFAULT_MAX_STACK_SIZE) {
                return false;
            }

            // Validate item exists
            return ConfigListHelper.isValidItem(itemPart);

        } catch (Exception e) {
            return false;
        }
    }


    public static ItemStack parseItemWithCountList(String entry) {
        String[] parts = entry.split("\\*", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format: " + entry);
        }

        int amount = Integer.parseInt(parts[0]);
        String itemId = parts[1];

        ResourceLocation location = ResourceLocation.parse(itemId);
        Item item = BuiltInRegistries.ITEM.getValue(location);

        if (item == null) {
            throw new IllegalArgumentException("Unknown item: " + itemId);
        }

        return new ItemStack(item, amount);
    }
}
