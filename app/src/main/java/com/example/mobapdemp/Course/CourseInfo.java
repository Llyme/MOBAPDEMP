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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);

		Bundle extras = getIntent().getExtras();

		String[] str = new String[]{
				extras.getString("name", ""),
				String.valueOf(extras.getInt("id", 0)),
				extras.getString("section", ""),
				extras.getString("room", ""),
				String.valueOf(extras.getInt("enrolled", 0)),
				String.valueOf(extras.getInt("enroll_cap", 0))
		};

		for (int i = 0; i < RES_ID.length; i++)
			((TextView) findViewById(RES_ID[i])).setText(str[i]);

		LinearLayout days = findViewById(R.id.course_info_days);

		for (int i = 0; i < extras.getInt("course_days", 0); i++) {
			TextView day = new TextView(this);
			day.setTextSize(16);
			day.setTextColor(TEXTCOLOR);
			day.setText(extras.getString("course_day" + i, ""));
			days.addView(day);
		}
	}

}
