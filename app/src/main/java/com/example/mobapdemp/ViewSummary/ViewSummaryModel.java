package com.example.mobapdemp.ViewSummary;

public class ViewSummaryModel {

    private String courseCode;
    private String courseDays;
    private String courseTime;
    private String courseRoom;
    private String courseProf;
    private int backgroundColor;

    public ViewSummaryModel(String code, String days, String time, String room, String professor,
                            int bgColor) {
        courseCode = code;
        courseDays = days;
        courseTime = time;
        courseRoom = room;
        courseProf = professor;
        backgroundColor = bgColor; //background color used to represent a course
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseDays() {
        return courseDays;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public String getCourseProf() {
        return courseProf;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setCourseCode(String code) {
        courseCode = code;
    }

    public void setCourseDays(String days) {
        courseDays = days;
    }

    public void setCourseTime(String time) {
        courseTime = time;
    }

    public void setCourseRoom(String room) {
        courseRoom = room;
    }

    public void setCourseProf(String courseProf) {
        courseProf = courseProf;
    }

    public void setBackgroundColor(int bgColor) {
        backgroundColor = bgColor;
    }
}
