package ua.darkphantom1337.anicloud.bedwars.enums;

public enum AniCloudBedWarsGameStatus {

    LOADING(""),
    ENABLING(""),
    LOADING_MAP(""),
    WAIT_PLAYERS(""),
    WAIT_START_GAME(""),
    IN_THE_GAME(""),
    DEATHMATCH_START(""),
    DISABLING(""),
    RELOADING(""),
    DISABLED("");

    private String textStatus;

    AniCloudBedWarsGameStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public String getTextStatus() {
        return textStatus;
    }

}
