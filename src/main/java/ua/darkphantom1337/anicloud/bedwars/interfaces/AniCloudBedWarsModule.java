package ua.darkphantom1337.anicloud.bedwars.interfaces;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;

public interface AniCloudBedWarsModule {

    String getModuleName();

    Boolean isEnabled();

    void setEnabled(Boolean enabled);

    AniCloudBedWars getAniCloudBedWars();

    void onLoad();

    void onEnable();

    void onDisable();

    void onReload();
}
