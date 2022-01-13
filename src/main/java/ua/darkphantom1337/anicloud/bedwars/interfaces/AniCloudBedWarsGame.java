package ua.darkphantom1337.anicloud.bedwars.interfaces;

import org.bukkit.entity.Player;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.GameType;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkStatus;

import java.util.List;

public interface AniCloudBedWarsGame {

    String getGameID();

    GameType getGameType();

    WorkStatus getGameStatus();

    String serverName();

    Boolean joinToGame(Player player);

    Boolean quitGame(Player player);

    Boolean kickOutOfTheGame(Player player);



    int getGameOnline();

    List<Player> getInGamePlayers();

    void setEnabled(Boolean enabled);

    AniCloudBedWars getAniCloudBedWars();

    void setGameStatus(WorkStatus workStatus);


}
