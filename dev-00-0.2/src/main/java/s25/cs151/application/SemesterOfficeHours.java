package s25.cs151.application;

public class SemesterOfficeHours {

    private String semester;
    private String year;
    private String days;

    public SemesterOfficeHours(String semester, String year, String days) {
        this.semester = semester;
        this.year = year;
        this.days = days;
    }

    public String getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }

    public String getDays() {
        return days;
    }
}
