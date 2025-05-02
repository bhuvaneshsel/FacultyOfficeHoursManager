package Model;

public class SemesterTimeSlot implements Comparable<SemesterTimeSlot>{
    private String fromHour;
    private String toHour;


    public String getFromHour() {
        return fromHour;
    }

    public String getToHour() {
        return toHour;
    }

    public SemesterTimeSlot(String fromHour, String toHour) {
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    @Override
    public int compareTo(SemesterTimeSlot o) {
        String[] thisFromValues = this.getFromHour().split(":");
        int thisHour = 0;
        int thisMinute = 0;
        if (thisFromValues.length > 1) {
            thisHour = Integer.parseInt(thisFromValues[0]);
            thisMinute = Integer.parseInt(thisFromValues[1]);
        }

        String [] oFromValues = o.getFromHour().split(":");
        int oHour = 0;
        int oMinute = 0;
        if (oFromValues.length > 1) {
            oHour = Integer.parseInt(oFromValues[0]);
            oMinute = Integer.parseInt(oFromValues[1]);
        }

        if (thisHour != oHour) {
            return thisHour - oHour;
        }
        else {
            return thisMinute-oMinute;
        }
    }
}
