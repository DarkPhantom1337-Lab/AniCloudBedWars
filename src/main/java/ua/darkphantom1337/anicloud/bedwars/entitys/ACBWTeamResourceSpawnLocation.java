package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.Location;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsResource;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsTeamColor;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ACBWTeamResourceSpawnLocation {

    private AniCloudBedWarsTeamColor teamColor;
    private HashMap<AniCloudBedWarsResource, Location> resourceSpawnLocation;

    public ACBWTeamResourceSpawnLocation(AniCloudBedWarsTeamColor teamColor) {
        this.teamColor = teamColor;
        resourceSpawnLocation = new HashMap<AniCloudBedWarsResource, Location>();
    }

    public void addLocation(AniCloudBedWarsResource resource, Location location) {
        resourceSpawnLocation.put(resource, location);
    }

    public Location getLocation(AniCloudBedWarsResource resource) {
        return resourceSpawnLocation.get(resource);
    }

    public void remLocation(AniCloudBedWarsResource resource) {
        resourceSpawnLocation.remove(resource);
    }

    public AniCloudBedWarsTeamColor getTeamColor(){
        return teamColor;
    }

    public List<AniCloudBedWarsResource> getAvailableResources(){
        return resourceSpawnLocation.keySet().stream().collect(Collectors.toList());
    }

}
