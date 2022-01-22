package ua.darkphantom1337.anicloud.bedwars.configurations;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.ModuleNames;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

public class ConfigurationsModule implements AniCloudBedWarsModule {

    private Boolean isEnabled = false;
    private AniCloudBedWars aniCloudBedWars;
    private static ConfigurationsModule configurationsModule;
    private GlobalConfigurationFile globalConfigurationFile;
    private HubConfigurationFile hubConfigurationFile;
    private GlobalGameConfigurationFile globalGameConfigurationFile;

    public ConfigurationsModule(AniCloudBedWars aniCloudBedWars){
        this.aniCloudBedWars = aniCloudBedWars;
        configurationsModule = this;
        AniCloudBedWars.inst().setConfigurationsModule(configurationsModule);
        getAniCloudBedWars().info("Successfully initialized 'CONFIGURATIONS' module.");
    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static ConfigurationsModule getInstance() {
        return configurationsModule;
    }

    public GlobalConfigurationFile getGlobalConfigurationFile() {
        return globalConfigurationFile;
    }

    public HubConfigurationFile getHubConfigurationFile() {
        return hubConfigurationFile;
    }

    public GlobalGameConfigurationFile getGlobalGameConfigurationFile() {
        return globalGameConfigurationFile;
    }


    @Override
    public void onLoad() {
        getAniCloudBedWars().info("Loading '" + getModuleName() + "' module...");
        this.globalConfigurationFile = new GlobalConfigurationFile(getAniCloudBedWars());
        AniCloudBedWars.inst().setWorkType(globalConfigurationFile.getWorkType());
        if (AniCloudBedWars.inst().getWorkType().equals(WorkType.HUB)) {
            this.hubConfigurationFile = new HubConfigurationFile(getAniCloudBedWars());
        }
        if (AniCloudBedWars.inst().getWorkType().equals(WorkType.GAME)) {
            this.globalGameConfigurationFile = new GlobalGameConfigurationFile(getAniCloudBedWars());
        }
        getAniCloudBedWars().info("Successfully loaded '" + getModuleName() +"' module.");
    }

    @Override
    public void onEnable() {
        getAniCloudBedWars().info("Enabling '" + getModuleName() + "' module...");
    }

    @Override
    public void onDisable() {
        getAniCloudBedWars().info("Disabling '" + getModuleName() + "' module...");
    }

    @Override
    public void onReload() {
        getAniCloudBedWars().info("Reloading '" + getModuleName() + "' module...");
    }

    @Override
    public String getModuleName() {
        return "CONFIGURATIONS";
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }

}
