package com.example.mobapdemp.Course;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;
import com.example.mobapdemp.R;

public class CourseInfo extends Activity {
	final private static int TEXTCOLOR = Color.rgb(0, 0, 0);
	final private int[] RES_ID = new int[]{
			R.id.course_info_name,
			R.id.course_info_id,
			R.id.course_info_section,
			R.id.course_info_room,
			R.id.course_info_enrolled,
			R.id.course_info_enroll_cap
	};
	public static Course course;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);

		Log.d("CourseInfo", "HELLO");
		if (course != null)
			return;
		Log.d("CourseInfo", "There");

		String[] str = new String[]{
				course.getString("name"),
				String.valueOf(course.getInt("id")),
				course.getString("section"),
				course.getString("room"),
				String.valueOf(course.getInt("enrolled")),
				String.valueOf(course.getInt("enroll_cap"))
		};

		for (int i = 0; i < RES_ID.length; i++)
			((TextView) findViewById(RES_ID[i])).setText(str[i]);

		LinearLayout days = findViewById(R.id.course_info_days);

		for (CourseDay courseDay : course.getCourseDays()) {
			TextView day = new TextView(this);
			day.setTextSize(12);
			day.setTextColor(TEXTCOLOR);
			day.setText(
					courseDay.getString("day") + " " +
							courseDay.readableTime()
			);
			days.addView(day);
		}
	}

}
