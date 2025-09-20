package tech.thedumbdev.enums;

public enum Severity {
    UNDEFINED("undefined"),
    CRITICAL("critical"),
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    WARN("warn");

    private String value;

    Severity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
