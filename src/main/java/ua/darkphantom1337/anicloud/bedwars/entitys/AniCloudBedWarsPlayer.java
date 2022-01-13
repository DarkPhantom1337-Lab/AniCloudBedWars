package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AniCloudBedWarsPlayer {

    private UUID playerUUID;

    public AniCloudBedWarsPlayer(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public AniCloudBedWarsPlayer(Player p) {
        this.playerUUID = p.getUniqueId();
    }

    public AniCloudBedWarsPlayer(String playerName) {
        this.playerUUID = Bukkit.getPlayer(playerName).getUniqueId();
    }

    public Boolean joinToHub(){

        return true;
    }


}
