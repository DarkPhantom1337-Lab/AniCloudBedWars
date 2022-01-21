package ua.darkphantom1337.anicloud.bedwars.enums;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;

public enum AniCloudBedWarsTeamColor {

    BLACK,
    WHITE,
    PURPLE,
    MAGENTA,
    LIGHT_YELLOW,
    LIGHT_GREEN,
    LIGHT_BLUE,
    LIGHT_RED,
    LIGHT_TURQUOISE,
    LIGHT_GRAY,
    DARK_YELLOW,
    DARK_GREEN,
    DARK_BLUE,
    DARK_RED,
    DARK_TURQUOISE,
    DARK_GRAY,
    RAINBOW,
    PHANTOM,
    MAGIC,
    DARK,
    DARK_PHANTOM;

    public String getTeamName(){
        return AniCloudBedWars.getInstance().getConfigurationsModule().getGlobalGameConfigurationFile().getSValue("Team." + this.name() + ".Name");
    }

}
