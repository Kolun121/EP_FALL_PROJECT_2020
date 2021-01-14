package mospolytech.engineering2020.fall.epprojectfall.domain.enumeration;

public enum VacationType {
    ANNUAL_VACATION("Ежегодный отпуск"), 
    ADDITIONAL_VACATION("Дополнительный отпуск"), 
    STUDY_VACATION("Учебный отпуск");
    
    private final String value;
    
    VacationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
