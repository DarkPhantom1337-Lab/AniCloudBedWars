package ua.darkphantom1337.anicloud.bedwars;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsGame;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsGameStatus;

public class ACBWGamePlaceholder extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "DarkPhantom1337";
    }

    @Override
    public String getIdentifier() {
        return "acbw";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        AniCloudBedWarsGame game = AniCloudBedWars.getInstance().getGameController().getCurrentBedWarsGame();
        if (params.equals("bossbar")){
            if (AniCloudBedWars.getInstance().getGameController().getCurrentBedWarsGame() != null) {
                if (game.getGameStatus().equals(AniCloudBedWarsGameStatus.WAIT_PLAYERS))
                    return "§eОжидание игроков...";
                if (game.getGameStatus().equals(AniCloudBedWarsGameStatus.STARTING_WAIT_PLAYERS))
                    return "§eИгра начинается через " + game.getTimeToStartGame() + " с.";
                if (game.getGameStatus().equals(AniCloudBedWarsGameStatus.STARTING))
                    return "§eИгра начинается через " + game.getTimeToStartGame() + " с.";
            }
                return "§7Игра идёт.";
       }
       int ln = Integer.parseInt(params.replace("l",""));
        if (AniCloudBedWars.getInstance().getGameController().getCurrentBedWarsGame() != null){
            if (ln <= game.getScoreboard().size()-1)
                return game.getScoreboard().get(ln-1);
       }
       return null;
    }
}