package ua.darkphantom1337.anicloud.bedwars.enums;

public enum OperationResult {

    SUCCESSFULLY("Operation successfully complete. No errors and warnings."),
    UNSUCCESSFUL("Operation not complete. No errors and warnings."),
    PERFORMANCE_ERROR("Error in run operation. Error printed.");

    private String textStatus;

    OperationResult(String textStatus) {
        this.textStatus = textStatus;
    }

    public String getDescription() {
        return textStatus;
    }

    public OperationResult setDescription(String description) {
        this.textStatus = description;
        return this;
    }

}
