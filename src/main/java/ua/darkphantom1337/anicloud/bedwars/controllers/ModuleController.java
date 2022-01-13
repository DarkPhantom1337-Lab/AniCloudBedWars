package ua.darkphantom1337.anicloud.bedwars.controllers;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.ConfigurationsModule;
import ua.darkphantom1337.anicloud.bedwars.enums.ModuleNames;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;
import ua.darkphantom1337.anicloud.bedwars.messages.HubMessageModule;

import java.util.HashMap;

public class ModuleController {

    private AniCloudBedWars aniCloudBedWars;
    private static ModuleController moduleController;
    private HashMap<String, AniCloudBedWarsModule> allModules = new HashMap<>();

    public ModuleController(AniCloudBedWars aniCloudBedWars) {
        this.aniCloudBedWars = aniCloudBedWars;
        getAniCloudBedWars().info("Loading 'ModuleController' ...");
        moduleController = this;
        initializeAllModules();

    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static ModuleController getModuleController() {
        return moduleController;
    }

    public void initializeAllModules() {
        getAniCloudBedWars().info("Initializing all modules...");
        allModules.put("CONFIGURATIONS", new ConfigurationsModule(getAniCloudBedWars()));
        allModules.put("HubMessage", new HubMessageModule(getAniCloudBedWars()));
        /**
         * other modules
         */
    }

    public void loadAllModules() {
        getAniCloudBedWars().info("Loading all modules...");
        allModules.values().forEach(module -> module.onLoad());
    }

    public void enableAllModules() {
        getAniCloudBedWars().info("Enabling all modules...");
        allModules.values().forEach(module -> module.onEnable());
    }

    public void disableAllModules() {
        getAniCloudBedWars().info("Disabling all modules...");
        allModules.values().forEach(module -> module.onDisable());
    }

    public AniCloudBedWarsModule getModule(ModuleNames moduleName){
        return allModules.get(moduleName.name());
    }

}
