package com.example.mobapdemp;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.mobapdemp.Database.Database;

public class Utility {

    /**
     * TEST METHOD. Adds dummy schedule data to the SQLite database
     *
     * NOTE 1: 'id' in Schedule may have different value from 'id' in Course and CourseDay.
     * NOTE 2: 'id' in Course may have different value from 'id' in CourseDay.
     * NOTE 3: 'id' in Course is the same with 'course_id' in CourseDay.
     * @param db Database instance
     */

    public static void addDummySchedule(Database db) {
        ContentValues contentValues = new ContentValues();

        /* Schedules database */
        contentValues.put("id", 0);
        contentValues.put("enrolled", 30);
        contentValues.put("enroll_cap", 30);
        contentValues.put("name", "MOBAPDE");
        contentValues.put("section", "S19");
        contentValues.put("room", "G301");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 1);
        contentValues.put("enrolled", 41);
        contentValues.put("enroll_cap", 43);
        contentValues.put("name", "PROJMAN");
        contentValues.put("section", "S20");
        contentValues.put("room", "G209");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 2);
        contentValues.put("enrolled", 36);
        contentValues.put("enroll_cap", 42);
        contentValues.put("name", "TREDFOR");
        contentValues.put("section", "S11");
        contentValues.put("room", "G205");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 3);
        contentValues.put("enrolled", 20);
        contentValues.put("enroll_cap", 20);
        contentValues.put("name", "SOFENGG");
        contentValues.put("section", "S18");
        contentValues.put("room", "G203");
        contentValues.put("remarks", "");
        contentValues.put("term", "Third Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 4);
        contentValues.put("enrolled", 32);
        contentValues.put("enroll_cap", 41);
        contentValues.put("name", "TREDFIV");
        contentValues.put("section", "S12");
        contentValues.put("room", "G206");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 5);
        contentValues.put("enrolled", 42);
        contentValues.put("enroll_cap", 43);
        contentValues.put("name", "LASARE3");
        contentValues.put("section", "A51");
        contentValues.put("room", "AG1202");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        contentValues.put("id", 6);
        contentValues.put("enrolled", 41);
        contentValues.put("enroll_cap", 41);
        contentValues.put("name", "TREDSIX");
        contentValues.put("section", "S12");
        contentValues.put("room", "G206");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("schedules", contentValues);

        /* Courses database */
        contentValues.clear();

        contentValues.put("id", 0);
        contentValues.put("enrolled", 30);
        contentValues.put("enroll_cap", 30);
        contentValues.put("name", "MOBAPDE");
        contentValues.put("section", "S19");
        contentValues.put("room", "G301");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 1);
        contentValues.put("enrolled", 41);
        contentValues.put("enroll_cap", 43);
        contentValues.put("name", "PROJMAN");
        contentValues.put("section", "S20");
        contentValues.put("room", "G209");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 2);
        contentValues.put("enrolled", 36);
        contentValues.put("enroll_cap", 42);
        contentValues.put("name", "TREDFOR");
        contentValues.put("section", "S11");
        contentValues.put("room", "G205");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 3);
        contentValues.put("enrolled", 20);
        contentValues.put("enroll_cap", 20);
        contentValues.put("name", "SOFENGG");
        contentValues.put("section", "S18");
        contentValues.put("room", "G203");
        contentValues.put("remarks", "");
        contentValues.put("term", "Third Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 4);
        contentValues.put("enrolled", 32);
        contentValues.put("enroll_cap", 41);
        contentValues.put("name", "TREDFIV");
        contentValues.put("section", "S12");
        contentValues.put("room", "G206");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 5);
        contentValues.put("enrolled", 42);
        contentValues.put("enroll_cap", 43);
        contentValues.put("name", "LASARE3");
        contentValues.put("section", "A51");
        contentValues.put("room", "AG1202");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        contentValues.put("id", 6);
        contentValues.put("enrolled", 41);
        contentValues.put("enroll_cap", 41);
        contentValues.put("name", "TREDSIX");
        contentValues.put("section", "S12");
        contentValues.put("room", "G206");
        contentValues.put("remarks", "");
        contentValues.put("term", "First Trimester, AY 2018-2019");
        db.createEntity("courses", contentValues);

        /* CourseDay database */
        contentValues.clear();

        contentValues.put("id", 0);
        contentValues.put("course_id", 0);
        contentValues.put("start", 12*60 + 45);
        contentValues.put("length", 14*60 + 15 - 12*60 + 45);
        contentValues.put("professor", "TIGHE, EDWARD");
        contentValues.put("day", "TH");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 1);
        contentValues.put("course_id", 1);
        contentValues.put("start", 7*60 + 30);
        contentValues.put("length", 9*60 - 7*60 + 30);
        contentValues.put("professor", "MAGPANTAY, LISSA");
        contentValues.put("day", "WF");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 2);
        contentValues.put("course_id", 2);
        contentValues.put("start", 14*60 + 30);
        contentValues.put("length", 16*60 - 14*60 + 30);
        contentValues.put("professor", "MOZOL, ALVENIO");
        contentValues.put("day", "TH");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 3);
        contentValues.put("course_id", 3);
        contentValues.put("start", 11*60);
        contentValues.put("length", 12*60 + 30 - 11*60);
        contentValues.put("professor", "DIMAUNAHAN, RYAN");
        contentValues.put("day", "WF");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 4);
        contentValues.put("course_id", 4);
        contentValues.put("start", 14*60 + 30);
        contentValues.put("length", 16*60 - 14*60 + 30);
        contentValues.put("professor", "LOZOL, ALVENIO");
        contentValues.put("day", "TH");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 5);
        contentValues.put("course_id", 5);
        contentValues.put("start", 11*60);
        contentValues.put("length", 12*60 + 30 - 11*60);
        contentValues.put("professor", "MIRANDA, GODORICO VIVAS");
        contentValues.put("day", "JAN07");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 6);
        contentValues.put("course_id", 5);
        contentValues.put("start", 16*60 + 30);
        contentValues.put("length", 23*60 + 59 - 16*60 + 30);
        contentValues.put("professor", "MIRANDA, GODORICO VIVAS");
        contentValues.put("day", "JAN18");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 7);
        contentValues.put("course_id", 5);
        contentValues.put("start", 0*60 + 1);
        contentValues.put("length", 19*60 + 30 - 0*60 + 1);
        contentValues.put("professor", "MIRANDA, GODORICO VIVAS");
        contentValues.put("day", "JAN19");
        db.createEntity("course_days", contentValues);

        contentValues.put("id", 8);
        contentValues.put("course_id", 6);
        contentValues.put("start", 14*60 + 30);
        contentValues.put("length", 16*60 - 14*60 + 30);
        contentValues.put("professor", "DELA CRUZ, JUAN");
        contentValues.put("day", "TH");
        db.createEntity("course_days", contentValues);
    }

    /**
     * Get strings for information regarding a specific course in a schedule.
     *
     * @param db          Database instance
     * @param courseName  Course whose info you want to get.
     * @return courseInfo - The string array containing 'courseName' info.
     */
    public static String[] getCoursesInfo(Database db, String courseName) {

        /* Query database to get course indicated in parameter courseName */
        Cursor cursor = db.queryEntity(
                "SELECT " + "schedules.enrolled, schedules.enroll_cap, schedules.name, " +
                        "schedules.section, schedules.room, course_days.start, course_days.length, " +
                        "course_days.professor, course_days.day " +
                "FROM schedules INNER JOIN courses ON schedules.name = courses.name " +
                "INNER JOIN course_days ON courses.id = course_days.course_id WHERE " +
                "schedules.name = '" + courseName + "'");

        /* Get info from query and place it in String array */
        String[] courseInfo = new String[cursor.getColumnCount() + (cursor.getCount() - 1) * 4];

        boolean isFirst = true; //used for special courses
        int lastIndex = cursor.getColumnCount();
        while(cursor.moveToNext()) {
            if(isFirst) {
                courseInfo[1] = cursor.getString(0) + ""; //Schedule.enrolled
                courseInfo[2] = cursor.getString(1) + ""; //Schedule.enroll_cap
                courseInfo[3] = cursor.getString(2) + ""; //Schedule.name
                courseInfo[4] = cursor.getString(3) + ""; //Schedule.section
                courseInfo[5] = cursor.getString(4) + ""; //Schedule.room
                courseInfo[6] = readableTime(cursor.getInt(5), cursor.getInt(6)); //Course time range
                courseInfo[7] = cursor.getString(7) + ""; //CourseDay.professor
                courseInfo[8] = regularDayFormat(cursor.getString(8) + ""); //CourseDay.day formatted
            }

            /* Check if regular course (1) or special course (> 1) */
            if(cursor.getCount() == 1 && !(courseInfo[8].startsWith("JAN")) || courseInfo[8].startsWith("FEB") ||
                    courseInfo[8].startsWith("MAR") || courseInfo[8].startsWith("APR") ||
                    courseInfo[8].startsWith("JUN") || courseInfo[8].startsWith("JUL") ||
                    courseInfo[8].startsWith("AUG") || courseInfo[8].startsWith("SEP") ||
                    courseInfo[8].startsWith("OCT") || courseInfo[8].startsWith("NOV") ||
                    courseInfo[8].startsWith("DEC")) {

                courseInfo[0] = -1 + "";
            } else if (cursor.getCount() > 1) {
                courseInfo[0] = cursor.getCount() + "";
            }

            if(!isFirst) {
                courseInfo[lastIndex] = readableTime(cursor.getInt(5), cursor.getInt(6));
                courseInfo[lastIndex + 1] = regularDayFormat(cursor.getString(8) + "");
                courseInfo[lastIndex + 2] = cursor.getString(4);
                courseInfo[lastIndex + 3] = cursor.getString(7);
                lastIndex += 4;
            } else {
                isFirst = false;
            }
        }

        return courseInfo;
    }

    /**
     * Get all course name within a given term and academic year
     * @param db - Database instance
     * @param term - A certain schedule's term, in String format (as taken from my.dlsu)
     * @return courses - String array containing names of all courses inside a term schedule.
     */
    public static String[] getCoursesInSchedule(Database db, String term) {

        /* Get first substring of parameter 'term' (First, Second, Third)
         * and last substring (AY 20xx-20xx) */
        String[] termSplit = term.split(" ");

        Cursor cursor = db.queryEntity("SELECT name FROM schedules WHERE term = '" +
                termSplit[0] + " Trimester, AY " + termSplit[3] + "'");

        /* Get info from query and place it in String array */
        String[] courses = new String[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            courses[i] = cursor.getString(0) + "";
        }

        return courses;
    }

    /**
     * Delete course from database
     *
     * @param db - Database instance
     * @param courseName - Name of course to be deleted
     * @return true if course is successfully deleted; otherwise, false
     */
    public static boolean deleteCourse(Database db, String courseName) {

        Cursor cursor = db.queryEntity("SELECT schedules.id, courses.id " +
                "FROM schedules INNER JOIN courses ON schedules.name = courses.name " +
                "INNER JOIN course_days ON courses.id = course_days.course_id WHERE " +
                "schedules.name = '" + courseName + "'");

        int[] id = new int[] {-1, -1};

        while(cursor.moveToNext()) {
            id[0] = Integer.parseInt(cursor.getString(0)); //schedules id
            id[1] = Integer.parseInt(cursor.getString(1)); //course id
        }

        if(id[0] == -1 || id[1] == -1) { //if id cannot be found
            return false;
        }

        db.deleteEntity("schedules", "id = " + id[0]);
        db.deleteEntity("courses", "id = " + id[1]);
        db.deleteEntity("course_days", "course_id = " + id[1]);

        return true;
    }

    /**
     * Taken from Schedule class; more generalized (can take any int)
     *
     * @param start - Start time
     * @param length - Length of time (End time - Start time)
     * @return String containing time, in DLSU format
     */
    public static String readableTime(int start, int length) {

        int hr0 = start / 60;
        int mn0 = start - hr0 * 60;
        int hr1 = (start + length) / 60;
        int mn1 = (start + length) - hr1 * 60;

        return String.format("%02d", hr0) + String.format("%02d", mn0) + " - " +
                String.format("%02d", hr1) + String.format("%02d", mn1);
    }

    /**
     * Add a '-' to the day format if class happens 2 times a week. Purely aesthetic.
     *
     * @param day - Class day
     * @return String class day with '-' in between
     */
    public static String regularDayFormat(String day) {
        if(day.length() == 2) {
            String[] daySplit = day.split("");
            return daySplit[1] + "-" + daySplit[2];
        }
        return day;
    }
}
