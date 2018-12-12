package com.example.mobapdemp.Course;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;
import com.example.mobapdemp.Database.Entities.Schedule;
import com.example.mobapdemp.MainActivity;
import com.example.mobapdemp.R;

public class ContactHolder extends RecyclerView.ViewHolder {
	final private static int TEXTCOLOR = Color.rgb(0, 0, 0);
	private Context context;
	private ContactAdapter adapter;
	private TextView id, section, room;
	private Button add, info;
	private LinearLayout days;
	private Course course;
	private boolean toggle;
	private ColorStateList color;
	private ColorStateList color_added;

	public ContactHolder(View view, ContactAdapter adapter) {
		super(view);

		context = view.getContext();
		this.adapter = adapter;
		id = view.findViewById(R.id.course_model_id);
		section = view.findViewById(R.id.course_model_section);
		room = view.findViewById(R.id.course_model_room);
		add = view.findViewById(R.id.course_model_add);
		info = view.findViewById(R.id.course_model_info);
		days = view.findViewById(R.id.course_model_days);
		color_added = view.getResources().getColorStateList(R.color.slot_added);

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle(!toggle);
			}
		});

		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	public void toggle(Boolean flag) {
		if (flag == toggle)
			return;

		Schedule schedule = MainActivity.getSelectedSchedule();
		toggle = flag;
		Log.d("CourseActivity", "DID " + toggle + course.getInt("id"));

		if (toggle) {
			schedule.addCourse(course);
			add.setBackgroundTintList(color_added);
			add.setText("-");
		} else {
			schedule.removeCourse(course.getInt("id"));
			add.setBackgroundTintList(color);
			add.setText("+");
		}

		adapter.toggle(this, toggle);
	}

	public void set(Course course) {
		this.course = course;

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

		if (MainActivity.getSelectedSchedule().hasCourse(course.getInt("id")))
			toggle(true);

		if (course.getInt("enrolled") >= course.getInt("enroll_cap")) {
			color = context.getResources().getColorStateList(R.color.slot_full);

			if (!toggle)
				add.setBackgroundTintList(color);
		} else
			color = context.getResources().getColorStateList(R.color.slot_vacant);
	}
}
