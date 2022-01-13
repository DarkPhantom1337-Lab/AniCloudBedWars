package ua.darkphantom1337.anicloud.bedwars.interfaces;

import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsPlayer;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsTeamColor;

import java.util.List;

public interface AniCloudBedWarsTeam {

    AniCloudBedWarsTeamColor getTeamColor();

    List<AniCloudBedWarsPlayer> getTeamPlayers();

    Boolean isAlive();


}
