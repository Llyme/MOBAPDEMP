package com.example.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TableLayout;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;

import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private Database database;
	private Table table;

	private void sampleTest() {
		Log.d("MainActivity2", "Loading...");

		/* Create a synchronous data scraper which takes the data for the given course name.
		   This has a 'base delay' of 1 second, meaning that it will always wait for at least
		   1 second. This will call a `listener` object that has all the data that you
		   wanted converted into a `Course` entity for convenience.
		 */
		new Scraper("HUMAART", new Scraper.Listener() {
			@Override
			public void call(String term, List<Course> courses) {
				Log.d("MainActivity2", "Term and Year: " + term);


				if (courses != null) {
					// Draw the first course into the table.
					table.draw(courses.get(0));

					for (Course course : courses) {
						// Save it to the database.
						Log.d(
								"MainActivity2",
								"DID SAVE: " + course.save(database)
						);

						// Get the ID.
						Log.d(
								"MainActivity2",
								"ID: " + course.getInt("id")
						);

						// Get the name.
						Log.d(
								"MainActivity2",
								"Name: " + course.getString("name")
						);

						// Get the section.
						Log.d(
								"MainActivity2",
								"Section: " + course.getString("section")
						);

						// Get the `CourseDay` entities.
						Log.d(
								"MainActivity2",
								"Days:"
						);

						// Iterate through the `CourseDay` entities.
						for (CourseDay courseDay : course.getCourseDays())
							Log.d(
									"MainActivity2",
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
								"MainActivity2",
								"Room: " + course.getString("room")
						);

						// Get the enrollment cap.
						Log.d(
								"MainActivity2",
								"Enroll Cap: " + course.getInt("enroll_cap")
						);

						// Get the number of enrolled students.
						Log.d(
								"MainActivity2",
								"Enrolled: " + course.getInt("enrolled")
						);

						// Get the remarks. I have no idea what this is for.
						Log.d(
								"MainActivity2",
								"Remarks: " + course.getString("remarks")
						);
					}
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("YOOOO");


		/* Delete the previous database. This is for testing only since I didn't want to spam
		   the database with junk.
		 */
		deleteDatabase("kunoichi-database");

		// Get the database.
		database = Database.getInstance(this);

		// Draw the table.
		table = new Table(this, (TableLayout) findViewById(R.id.table));


		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawer,
				toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		);

		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav_course:
				startActivity(new Intent(this, CourseActivity.class));
				break;

			case R.id.nav_sched:
				startActivity(new Intent(this, ScheduleActivity.class));
				break;
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}
}
