package com.example.mobapdemp.Course;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;
import com.example.mobapdemp.R;

public class ContactHolder extends RecyclerView.ViewHolder {
	final private static int TEXTCOLOR = Color.rgb(0, 0, 0);
	private Context context;
	private TextView id, section, room;
	private Button add, info;
	private LinearLayout days;

	public ContactHolder(View view) {
		super(view);

		context = view.getContext();
		id = view.findViewById(R.id.course_model_id);
		section = view.findViewById(R.id.course_model_section);
		room = view.findViewById(R.id.course_model_room);
		add = view.findViewById(R.id.course_model_add);
		info = view.findViewById(R.id.course_model_info);
		days = view.findViewById(R.id.course_model_days);

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	public void set(Course course) {
		id.setText(String.valueOf(course.getInt("id")));
		section.setText(course.getString("section"));
		room.setText(course.getString("room"));

		if (days.getChildCount() > 0)
			days.removeAllViews();

		for (CourseDay courseDay : course.getCourseDays()) {
			TextView day = new TextView(context);
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
