package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.Location;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsResource;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsTeamColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ACBWBedSpawnLocation {

    //forBed

    private AniCloudBedWarsTeamColor teamColor;
    private List<String> bedLocation;

    public ACBWBedSpawnLocation(AniCloudBedWarsTeamColor teamColor) {
        this.teamColor = teamColor;
        bedLocation = new ArrayList<String>();
    }

    public void addLocation(Location location) {
        bedLocation.add(getLString(location));
    }

    public Boolean isTeamBed(Location loc){
        return bedLocation.contains(getLString(loc));
    }

    public void remLocation(Location location) {
        bedLocation.remove(getLString(location));
    }

    public AniCloudBedWarsTeamColor getTeamColor(){
        return teamColor;
    }

    public String getLString(Location location){
        return location.getWorld()+";"+ location.getBlockX() + ";" + location.getBlockY() +";" +  location.getBlockZ();
    }

}
