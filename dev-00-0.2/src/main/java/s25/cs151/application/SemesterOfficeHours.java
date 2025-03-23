package s25.cs151.application;

public class SemesterOfficeHours implements Comparable<SemesterOfficeHours> {

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

    //converts the semester into an int for easier comparisons
    public int getSemesterInt(SemesterOfficeHours sem) {
        if (sem.getSemester().equals("Spring")) {
            return 1;
        }
        else if (sem.getSemester().equals("Summer")){
            return 2;
        }
        else if (sem.getSemester().equals("Fall")) {
            return 3;
        }
        else if (sem.getSemester().equals("Winter")){
            return 4;
        }
        return 0;
    }

    @Override
    public int compareTo(SemesterOfficeHours o) {
        try {
            if (Integer.parseInt(this.getYear()) != Integer.parseInt(o.getYear())) {
                return Integer.parseInt(o.getYear()) - Integer.parseInt(this.getYear());
            }
            else {
                int thisSeasonInt = getSemesterInt(this);
                int oSeasonInt = getSemesterInt(o);
                return oSeasonInt - thisSeasonInt;
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
