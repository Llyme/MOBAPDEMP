package com.example.mobapdemp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;

import java.util.ArrayList;

/*
 *The code works but needs to be modified to adapt to the current system since I just used dummy data for testing
 *
 * courses ArrayList are equivalent to schedule. They just need to be changed to reflect the Schedule class
 * Further testing needed to make sure everything works
 * It is assumed that there are no conflicts in the schedule that will be passed on to this class
 * Implementation of Database within this class to get the schedule is neccesary 
 *
 */

public class TimetableView extends AppCompatActivity {
    //DUMMY DATA DELETE WHEN MERGE
    /*private Course course = new Course();
    private Course course2 = new Course();
    private Course course3 = new Course();
    private Course course4 = new Course();
    private ArrayList<Course> courses = new ArrayList<>();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_view);
        TableLayout tl = (TableLayout)findViewById(R.id.TL1);
        Log.d("Here", "BITCH");



        //Initialization of Schedule
        int hour=7;
        int min=30;

        //Splits Time (MODIFY BASED OFF MERGE)
        Log.d("Courses", courses.get(0).getTime() + " AND " + courses.get(1).getTime());





        //These allow the system to know timeslots on what time a course ends
        boolean ToF_Tues = false;
        boolean ToF_Wednesday = false;
        boolean ToF_Thursday = false;
        boolean ToF_Friday = false;

        boolean DayApplicableT = false;
        boolean DayApplicableW = false;
        boolean DayApplicableH = false;
        boolean DayApplicableF = false;

        boolean NoliT = false;
        boolean NoliW = false;
        boolean NoliH = false;
        boolean NoliF = false;

        for(int i=0; i<56; i++) { //So it Ends at 9:15 PM, 56 Timeslots between 0730-2115
            StringBuilder sb = new StringBuilder();
            //Creates the Time to be used for the row
            if(hour < 10)
                sb.append("0" + hour);
            else
                sb.append(hour);

            if(min==0)
                sb.append("0");
            sb.append(min);

            //Time SB
            Log.d("Current Time", sb.toString());

            //Time and Row
            TableRow row = new TableRow(this);
            TextView Time = new TextView(this);

            //Days
            TextView Tuesday = new TextView(this);
            TextView Wednesday = new TextView(this);
            TextView Thursday = new TextView(this);
            TextView Friday = new TextView(this);

            //Reset
            NoliT = false;
            NoliW = false;
            NoliH = false;
            NoliF = false;

            for(int j=0; j<courses.size(); j++) {
                //Splits into Start and End time
                String[] times = courses.get(j).getTime().split(" - ");
                Log.d("MainActivity", times[0] + " up to " + times[1]);

                //Variables that change wheather the date is applicable or not
                DayApplicableT = false;
                DayApplicableW = false;
                DayApplicableH = false;
                DayApplicableF = false;

                //Checks if current course date is applicable
                if(courses.get(j).getDays().contentEquals("TH")){
                    DayApplicableT = true;
                    DayApplicableH = true;
                } else if(courses.get(j).getDays().contentEquals("WF")){
                    DayApplicableW = true;
                    DayApplicableF = true;
                }

                //Time Slot
                Time.setBackgroundColor(Color.rgb(220, 220, 220));
                //Time.setPadding(0, 5, 0 ,5);
                Time.setGravity(Gravity.CENTER);
                Time.setText(sb.toString());

                //Tuesday Time Slot
                //Logic to check if subject is applicable to Slot
                if (sb.toString().contentEquals(times[0]) && !ToF_Tues && DayApplicableT) { //Time Start
                    Tuesday.setText(courses.get(j).getSubject());
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Tuesday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Tuesday.setBackgroundColor(Color.rgb(255, 0, 0));
                    ToF_Tues = true;
                    NoliT = true;
                } else if (!sb.toString().contentEquals(times[1]) && ToF_Tues && DayApplicableT) { //Time Middle
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Tuesday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Tuesday.setBackgroundColor(Color.rgb(255, 0, 0));
                    NoliT = true;
                } else if (sb.toString().contentEquals(times[1]) && ToF_Tues && DayApplicableT) { //Time End
                    Tuesday.setBackgroundColor(Color.rgb(211, 211, 211));
                    ToF_Tues = false;
                    NoliT = true;
                } else if(!NoliT){
                    Log.d("Set BG Tuesday", courses.get(j).getSubject());
                    Tuesday.setBackgroundColor(Color.rgb(211, 211, 211));
                }
                Tuesday.setGravity(Gravity.CENTER);

                //Wednesday Time Slot
                //Logic to check if subject is applicable to Slot
                if (sb.toString().contentEquals(times[0]) && !ToF_Wednesday && DayApplicableW) { //Time Start
                    Wednesday.setText(courses.get(j).getSubject());
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Wednesday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Wednesday.setBackgroundColor(Color.rgb(255, 0, 0));
                    ToF_Wednesday = true;

                } else if (!sb.toString().contentEquals(times[1]) && ToF_Wednesday && DayApplicableW) { //Time Middle
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Wednesday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Wednesday.setBackgroundColor(Color.rgb(255, 0, 0));
                    NoliW=true;
                } else if (sb.toString().contentEquals(times[1]) && ToF_Wednesday && DayApplicableW) { //Time End
                    Wednesday.setBackgroundColor(Color.rgb(202, 201, 201));
                    ToF_Wednesday = false;
                    NoliW=true;
                } else if(!NoliW){
                    Wednesday.setBackgroundColor(Color.rgb(202, 201, 201));
                }
                Wednesday.setGravity(Gravity.CENTER);

                //Thursday Time Slot
                //Logic to check if subject is applicable to Slot
                if (sb.toString().contentEquals(times[0]) && !ToF_Thursday && DayApplicableH) {
                    Thursday.setText(courses.get(j).getSubject());
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Thursday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Thursday.setBackgroundColor(Color.rgb(255, 0, 0));
                    ToF_Thursday = true;
                    NoliH = true;
                } else if (!sb.toString().contentEquals(times[1]) && ToF_Thursday && DayApplicableH) {
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Thursday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Thursday.setBackgroundColor(Color.rgb(255, 0, 0));
                    NoliH = true;
                } else if (sb.toString().contentEquals(times[1]) && ToF_Thursday && DayApplicableH) {
                    Thursday.setBackgroundColor(Color.rgb(211, 211, 211));
                    ToF_Thursday = false;
                    NoliH = true;
                } else if(!NoliH){
                    Thursday.setBackgroundColor(Color.rgb(211, 211, 211));
                }
                Thursday.setGravity(Gravity.CENTER);

                //Friday Time Slot
                //Logic to check if subject is applicable to Slot
                if (sb.toString().contentEquals(times[0]) && !ToF_Friday && DayApplicableF) { //Time Start
                    Friday.setText(courses.get(j).getSubject());
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Friday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Friday.setBackgroundColor(Color.rgb(255, 0, 0));
                    ToF_Friday = true;
                    NoliF = true;
                } else if (!sb.toString().contentEquals(times[1]) && ToF_Friday && DayApplicableF) { //Time Middle
                    if(courses.get(j).getEnrolled() < courses.get(j).getEnrollCap())
                        Friday.setBackgroundColor(Color.rgb(157, 254, 151));
                    else
                        Friday.setBackgroundColor(Color.rgb(255, 0, 0));
                    NoliF = true;
                } else if (sb.toString().contentEquals(times[1]) && ToF_Friday && DayApplicableF) { //Time End
                    Friday.setBackgroundColor(Color.rgb(202, 201, 201));
                    ToF_Friday = false;
                    NoliF = true;
                } else if(!NoliF){
                    Friday.setBackgroundColor(Color.rgb(202, 201, 201));
                    Log.d("Set BG Friday", courses.get(j).getSubject());
                }
                Friday.setGravity(Gravity.CENTER);

            }
            tl.addView(row);
            row.addView(Time);
            row.addView(Tuesday);
            row.addView(Wednesday);
            row.addView(Thursday);
            row.addView(Friday);

            min=min+15;
            if(min==60){
                hour++;
                min=0;
            }
        }

    }
}
