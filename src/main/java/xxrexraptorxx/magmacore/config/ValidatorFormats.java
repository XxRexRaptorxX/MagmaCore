package xxrexraptorxx.magmacore.config;

public enum ValidatorFormats {

    GENERIC_FORMAT("namespace:mame"),
    GENERIC_ID_FORMAT("id:mame"),
    GENERIC_PROBABILITY_FORMAT("namespace-probability"),
    GENERIC_ID_PROBABILITY_FORMAT("id-probability"),

    ITEM_AMOUNT_FORMAT("amount*namespace:item"),
    BLOCK_AMOUNT_FORMAT("amount*namespace:block"),
    ENTITY_AMOUNT_FORMAT("amount*namespace:entity"),

    ENTITY_PROBABILITY_FORMAT("namespace:entity-probability"),
    ITEM_PROBABILITY_FORMAT("namespace:item-probability"),
    BLOCK_PROBABILITY_FORMAT("namespace:block-probability"),
    EFFECT_PROBABILITY_FORMAT("namespace:effect-probability"),
    BIOME_PROBABILITY_FORMAT("namespace:biome-probability");

    private final String format;


    ValidatorFormats(String format) {
        this.format = format;
    }


    public String getFormat() {
        return format;
    }
}
