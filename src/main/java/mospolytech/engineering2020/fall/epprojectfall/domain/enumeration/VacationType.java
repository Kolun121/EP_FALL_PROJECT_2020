package mospolytech.engineering2020.fall.epprojectfall.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VacationType {
    ANNUAL_VACATION("Ежегодный отпуск"), 
    ADDITIONAL_VACATION("Дополнительный отпуск"), 
    STUDY_VACATION("Учебный отпуск");
    
    private final String value;
    
    VacationType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
