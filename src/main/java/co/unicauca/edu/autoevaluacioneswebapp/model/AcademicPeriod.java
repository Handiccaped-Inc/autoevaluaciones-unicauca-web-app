package co.unicauca.edu.autoevaluacioneswebapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class AcademicPeriod {

    private static LocalDate initDate = LocalDate.parse("06-09-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    private static LocalDate endDate = LocalDate.parse("10-02-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    public static LocalDate getInitDate() {
        return initDate;
    }
    public static LocalDate getEndDate() {
        return endDate;
    }
}
