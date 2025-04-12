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
        try {

            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("mm/dd/yyyy");
            java.time.LocalDate thisDate = java.time.LocalDate.parse(this.getDate(), formatter);
            java.time.LocalDate otherDate = java.time.LocalDate.parse(o.getDate(), formatter);


            int dateComparison = thisDate.compareTo(otherDate);
            if (dateComparison != 0) {
                return dateComparison;
            }


            String thisFromHour = this.getTimeSlot().split("-")[0].trim();
            String otherFromHour = o.getTimeSlot().split("-")[0].trim();

            String[] thisParts = thisFromHour.split(":");
            String[] otherParts = otherFromHour.split(":");

            int thisHour = Integer.parseInt(thisParts[0]);
            int thisMinute = Integer.parseInt(thisParts[1]);

            int otherHour = Integer.parseInt(otherParts[0]);
            int otherMinute = Integer.parseInt(otherParts[1]);


            if (thisHour != otherHour) {
                return thisHour - otherHour;
            } else {
                return thisMinute - otherMinute;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing date or timeslot", e);
        }

    }

}
