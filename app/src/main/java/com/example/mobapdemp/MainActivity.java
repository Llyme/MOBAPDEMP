package com.example.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.mobapdemp.Course.CourseActivity;
import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.Schedule;

import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	final private static String DEFAULT_NAME = "New Schedule";

	private Database database;
	private Table table;

	private static Schedule selectedSchedule;
	private TextView header;

	public static Schedule getSelectedSchedule() {
		return selectedSchedule;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		/* Load database. */

		/* Delete the previous database. This is for testing only since I didn't want to spam
		   the database with junk.
		 */
		deleteDatabase("kunoichi-database");

		// Get the database.
		database = Database.getInstance(this);

		// Draw the table.
		table = new Table(this, (TableLayout) findViewById(R.id.table));


		/* Load schedule. */

		header = findViewById(R.id.content_main_term);

		if (selectedSchedule != null) {
			header.setText(selectedSchedule.getString("term"));
			setTitle(selectedSchedule.getString("name"));
		} else
			new Scraper("", new Scraper.Listener() {
				@Override
				public void call(String term, List<Course> courses) {
					final String txt = term;

					selectedSchedule = new Schedule(DEFAULT_NAME, term);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							header.setText(txt);
						}
					});

					setTitle(DEFAULT_NAME);
				}
			});


		/* Setup drawer navigation */

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
