package com.example.mobapdemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private Database database;
	private Table table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* Delete the previous database. This is for testing only since I didn't want to spam
		   the database with junk.
		 */
		deleteDatabase("kunoichi-database");

		// Get the database.
		database = Database.getInstance(this);

		// Draw the table.
		table = new Table(this, (TableLayout) findViewById(R.id.table));

		Log.d("MainActivity", "Loading...");

		/* Create a synchronous data scraper which takes the data for the given course name.
		   This has a 'base delay' of 1 second, meaning that it will always wait for at least
		   1 second. This will call a `listener` object that has all the data that you
		   wanted converted into a `Course` entity for convenience.
		 */
		new Scraper("HUMAART", new Scraper.Listener() {
			@Override
			public void call(String term, List<Course> courses) {
				Log.d("MainActivity", "Term and Year: " + term);


				if (courses != null) {
					// Draw the first course into the table.
					table.draw(courses.get(0));

					for (Course course : courses) {
						// Save it to the database.
						Log.d(
								"MainActivity",
								"DID SAVE: " + course.save(database)
						);

						// Get the ID.
						Log.d(
								"MainActivity",
								"ID: " + course.getInt("id")
						);

						// Get the name.
						Log.d(
								"MainActivity",
								"Name: " + course.getString("name")
						);

						// Get the section.
						Log.d(
								"MainActivity",
								"Section: " + course.getString("section")
						);

						// Get the `CourseDay` entities.
						Log.d(
								"MainActivity",
								"Days:"
						);

						// Iterate through the `CourseDay` entities.
						for (CourseDay courseDay : course.getCourseDays())
							Log.d(
									"MainActivity",
									// Get the day.
									courseDay.getString("day") + " | " +
											/* Get the readable time since it's saved as
											   minutes. Just go to the java class for more
											   info.
											 */
											courseDay.readableTime() + " | " +
											// Get the professor.
											courseDay.getString("professor")
							);

						// Get the room name.
						Log.d(
								"MainActivity",
								"Room: " + course.getString("room")
						);

						// Get the enrollment cap.
						Log.d(
								"MainActivity",
								"Enroll Cap: " + course.getInt("enroll_cap")
						);

						// Get the number of enrolled students.
						Log.d(
								"MainActivity",
								"Enrolled: " + course.getInt("enrolled")
						);

						// Get the remarks. I have no idea what this is for.
						Log.d(
								"MainActivity",
								"Remarks: " + course.getString("remarks")
						);
					}
				}
			}
		});
	}
}
