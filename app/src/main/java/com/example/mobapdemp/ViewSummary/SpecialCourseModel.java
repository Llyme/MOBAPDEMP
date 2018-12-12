package com.example.mobapdemp.ViewSummary;

public class SpecialCourseModel {

    private String courseCode;
    private String[] courseDays;
    private String[] courseTime;
    private String[] courseRoom;
    private String[] courseProf;
    private int size;
    private int backgroundColor;

    public SpecialCourseModel(String courseCode, String[] courseDays, String[] courseTime,
                              String[] courseRoom, String[] courseProf, int backgroundColor, int size) {
        this.courseCode = courseCode;
        this.courseDays = courseDays;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
        this.courseProf = courseProf;
        this.backgroundColor = backgroundColor;
        this.size = size;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String[] getCourseDays() {
        return courseDays;
    }

    public String[] getCourseTime() {
        return courseTime;
    }

    public String[] getCourseRoom() {
        return courseRoom;
    }

    public String[] getCourseProf() {
        return courseProf;
    }

    public int getSize() {
        return size;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setCourseCode(String code) {
        courseCode = code;
    }

    public void setCourseDays(String[] days) {
        courseDays = days;
    }

    public void setCourseTime(String[] time) {
        courseTime = time;
    }

    public void setCourseRoom(String[] room) {
        courseRoom = room;
    }

    public void setCourseProf(String[] courseProf) {
        courseProf = courseProf;
    }

    public void setBackgroundColor(int bgColor) {
        backgroundColor = bgColor;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
