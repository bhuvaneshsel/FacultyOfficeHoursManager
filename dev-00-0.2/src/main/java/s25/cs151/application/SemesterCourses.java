package s25.cs151.application;

public class SemesterCourses implements Comparable<SemesterCourses> {

    private String CodeName;
    private String CourseName;
    private String SecName;

    public SemesterCourses(String CodeName, String CourseName, String SecName)
    {
        this.CodeName = CodeName;
        this.CourseName = CourseName;
        this.SecName = SecName;
    }

    public String getCodeName(){return CodeName;}
    public String getCourseName(){return CourseName;}
    public String getSecName(){return SecName;}

    @Override
    public int compareTo(SemesterCourses o){
        return o.getCodeName().compareTo(this.getCodeName());
    }
}
