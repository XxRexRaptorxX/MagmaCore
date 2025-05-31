package xxrexraptorxx.magmacore.config;

import xxrexraptorxx.magmacore.main.References;

public enum ConfigCategories {

    GENERAL ("general"),
    LOGGER ("logger"),
    ITEMS ("items"),
    BLOCKS ("blocks"),
    WORLD ("world"),
    TOOLS ("tools"),
    ARMOR ("armor"),
    SPAWNING ("spawning"),
    MOBS ("mobs"),
    ENERGY ("energy");

    private final String categoryName;


    ConfigCategories(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }


    public String getCategoryLangTag() {
        return References.MODID + ".configuration." + categoryName;
    }


    @Override
    public String toString() {
        return categoryName;
    }


    public static ConfigCategories fromString(String categoryName) {
        for (ConfigCategories category : ConfigCategories.values()) {
            if (category.categoryName.equals(categoryName)) {

                return category;
            }
        }

        throw new IllegalArgumentException("Unknown Category: " + categoryName);
    }
}
