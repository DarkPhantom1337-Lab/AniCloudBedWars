package ua.darkphantom1337.anicloud.bedwars.messages;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

public class HubMessageModule implements AniCloudBedWarsModule {

    private AniCloudBedWars aniCloudBedWars;

    private static HubMessageModule hubMessageModule;
    private String hubJoinMessage;
    private String hubJoinErrorMessage;

    @Override
    public String getModuleName() {
        return "HubMessage";
    }

    @Override
    public Boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(Boolean enabled) {

    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    @Override
    public void onLoad() {
        hubJoinMessage  = getAniCloudBedWars().getConfigurationsModule().getHubConfigurationFile().getString("HubJoinMessage");
        hubJoinErrorMessage  = getAniCloudBedWars().getConfigurationsModule().getHubConfigurationFile().getString("HubJoinErrorMessage");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onReload() {
        getAniCloudBedWars().info("Reloading '" + getModuleName() + "' module...");
    }

    public HubMessageModule(AniCloudBedWars aniCloudBedWars) {
        this.aniCloudBedWars = aniCloudBedWars;
        getAniCloudBedWars().info("Loading 'HubMessage' module...");
        hubMessageModule = this;
    }

    public static String getHubJoinMessage(){
        return hubMessageModule.hubJoinMessage;
    }

    public static String getHubJoinErrorMessage(){
        return hubMessageModule.hubJoinErrorMessage;
    }
}
