package ua.darkphantom1337.anicloud.bedwars.configurations;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

public class ConfigurationsModule implements AniCloudBedWarsModule {

    private Boolean isEnabled = false;
    private AniCloudBedWars aniCloudBedWars;
    private static ConfigurationsModule configurationsModule;
    private GlobalConfigurationFile globalConfigurationFile;
    private HubConfigurationFile hubConfigurationFile;
    //private GameConfigurationFile gameConfigurationFile;

    public ConfigurationsModule(AniCloudBedWars aniCloudBedWars){
        this.aniCloudBedWars = aniCloudBedWars;
        configurationsModule = this;
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

  /*  public GameConfigurationFile getGameConfigurationFile() {
        return gameConfigurationFile;
    }
*/

    @Override
    public void onLoad() {
        getAniCloudBedWars().info("Loading '" + getModuleName() + "' module...");
        this.globalConfigurationFile = new GlobalConfigurationFile(getAniCloudBedWars());
        this.hubConfigurationFile = new HubConfigurationFile(getAniCloudBedWars());
        //this.gameConfigurationFile = new GameConfigurationFile(getAniCloudBedWars());
        getAniCloudBedWars().info("Successfully loaded '" + getModuleName() +"' module.");
    }

    @Override
    public void onEnable() {
        getAniCloudBedWars().info("Enabling '" + getModuleName() + "' module...");
        if (globalConfigurationFile.getWorkType().equals(WorkType.HUB)){
            getAniCloudBedWars().info("Initializing variables for WorkType 'HUB'...");
            getAniCloudBedWars().setHubPrefix(getHubConfigurationFile().getHubPrefix());
            /**
             * initialize all hub variables
             */

        }
        if (globalConfigurationFile.getWorkType().equals(WorkType.GAME)){
            getAniCloudBedWars().info("Initializing variables for WorkType 'GAME'...");
        //    getAniCloudBedWars().setInGamePrefix(getGameConfigurationFile().getGamePrefix());
        }
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
