package com.example.mobapdemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deleteDatabase("kunoichi-database");
		database = Database.getInstance(this);

		Log.d("MainActivity", "Loading...");

		new Scraper("LASARE3", new Scraper.Listener() {
			@Override
			public void call(String term, List<Course> courses) {
				Log.d("MainActivity", "Term and Year: " + term);

				if (courses != null)
					for (Course course : courses) {
						Log.d(
								"MainActivity",
								"DID SAVE: " + course.save(database)
						);
						Log.d(
								"MainActivity",
								"ID: " + course.getInt("id")
						);
						Log.d(
								"MainActivity",
								"Name: " + course.getString("name")
						);
						Log.d(
								"MainActivity",
								"Section: " + course.getString("section")
						);
						Log.d(
								"MainActivity",
								"Days:"
						);

						for (CourseDay courseDay : course.getCourseDays())
							Log.d(
									"MainActivity",
									courseDay.getString("day") + " | " +
											courseDay.readableTime() + " | " +
											courseDay.getString("professor")
							);

						Log.d(
								"MainActivity",
								"Room: " + course.getString("room")
						);
						Log.d(
								"MainActivity",
								"Enroll Cap: " + course.getInt("enroll_cap")
						);
						Log.d(
								"MainActivity",
								"Enrolled: " + course.getInt("enrolled")
						);
						Log.d(
								"MainActivity",
								"Remarks: " + course.getString("remarks")
						);
					}
			}
		});
	}
}
