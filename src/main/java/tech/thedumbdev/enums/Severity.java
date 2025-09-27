package tech.thedumbdev.enums;

import java.io.Serializable;

public enum Severity implements Serializable {
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
