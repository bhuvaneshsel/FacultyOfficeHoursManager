package s25.cs151.application;

public class SemesterSchedule implements Comparable<SemesterSchedule> {

    private String fullName;
    private String date;
    private String timeSlot;
    private String course;
    private String reason;
    private String comment;

    public SemesterSchedule(String fullName, String date, String timeSlot, String course, String reason, String comment) {
        this.fullName = fullName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    public String getFullName() { return fullName; }

    public String getDate() {return date;}

    public String getTimeSlot() {return timeSlot;}

    public String getCourse() {return course;}

    public String getReason() {return reason;}

    public String getComment() {return comment;}

    // To do: sort data ascending on date and time slot
    @Override
    public int compareTo(SemesterSchedule o) {
        return 0;
    }

}
