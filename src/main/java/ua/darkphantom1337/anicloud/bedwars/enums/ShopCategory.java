package ua.darkphantom1337.anicloud.bedwars.enums;

import javafx.scene.paint.Material;
import ua.darkphantom1337.anicloud.bedwars.entitys.ACBWItem;

import java.util.List;

public enum ShopCategory {

    BLOCKS(null, null, null, null),

    ARMORS(null, null, null, null),

    WEAPONS(null, null, null, null),

    OTHER(null, null, null, null);

    private String textStatus;

    ShopCategory(String name, List<String> lore, Material material, List<ACBWItem> categoryItems) {
        // this.textStatus = textStatus;
    }
    /*

    public String getDescription() {
        return textStatus;
    }

    public ShopCategory setDescription(String description) {
        this.textStatus = description;
        return this;
    }
*/
}
