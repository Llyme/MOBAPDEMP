package com.example.mobapdemp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobapdemp.Course.CourseActivity;
import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.Schedule;
import com.example.mobapdemp.Schedule.ScheduleActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	final private static String DEFAULT_NAME = "New Schedule";

	public static Database database;
	private Table table;

	private static String currentTerm;
	private static Schedule selectedSchedule;
	private TextView header;

	public static Schedule getSelectedSchedule() {
		return selectedSchedule;
	}

	public static void showDialog(final AppCompatActivity activity,
	                              String question,
	                              View view,
	                              String yes,
	                              String no,
	                              final Runnable runnable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		if (view != null)
			builder.setView(view);

		builder.setTitle(question)
				.setPositiveButton(yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						activity.runOnUiThread(runnable);
					}
				})
				.setNegativeButton(no, null)
				.show();
	}

	public void createNewSchedule() {
		header.setText(getString(R.string.content_main_header));

		selectedSchedule = null;

		if (currentTerm != null) {
			selectedSchedule = new Schedule(DEFAULT_NAME, currentTerm);

			header.setText(currentTerm);
			setTitle(DEFAULT_NAME);
		} else
			new Scraper("", new Scraper.Listener() {
				@Override
				public void call(final String term, List<Course> courses) {
					if (selectedSchedule != null)
						return;

					currentTerm = term;

					selectedSchedule = new Schedule(DEFAULT_NAME, term);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							header.setText(term);
						}
					});

					setTitle(DEFAULT_NAME);
				}
			});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		/* Load database. */

		/* Delete the previous database. This is for testing only since I didn't want to spam
		   the database with junk.
		 */
		//deleteDatabase("kunoichi-database");

		// Get the database.
		if (database == null) {
			database = Database.getInstance(this);
		}

		// Draw the table.
		table = new Table(this, (TableLayout) findViewById(R.id.table));


		/* Load schedule. */

		header = findViewById(R.id.content_main_term);

		if (selectedSchedule != null) {
			header.setText(selectedSchedule.getString("term"));
			setTitle(selectedSchedule.getString("name"));

			selectedSchedule.draw(table);
		} else
			createNewSchedule();


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
			case R.id.nav_new:
				if (selectedSchedule == null)
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"Already created new schedule.",
									Toast.LENGTH_SHORT
							).show();
						}
					});
				else
					showDialog(
							this,
							"Are you sure you want to create a new schedule?",
							null,
							"Yes",
							"No",
							new Runnable() {
								@Override
								public void run() {
									createNewSchedule();

									Toast.makeText(
											getApplicationContext(),
											"Created new schedule.",
											Toast.LENGTH_LONG
									).show();
								}
							}
					);
				break;

			case R.id.nav_save:
				if (selectedSchedule == null)
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"System is still loading. Please wait.",
									Toast.LENGTH_SHORT
							).show();
						}
					});
				else {
					selectedSchedule.save(database);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"Schedule successfully saved.",
									Toast.LENGTH_SHORT
							).show();
						}
					});
				}

				break;

			case R.id.nav_sched:
				startActivity(new Intent(this, ScheduleActivity.class));
				break;

			case R.id.nav_rename:
				if (selectedSchedule == null)
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"System is still loading. Please wait.",
									Toast.LENGTH_SHORT
							).show();
						}
					});
				else {
					LayoutInflater inflater = getLayoutInflater();
					final View view = inflater.inflate(R.layout.schedule_rename, null);

					showDialog(
							this,
							"Rename",
							view,
							"Rename",
							"Cancel",
							new Runnable() {
								@Override
								public void run() {
									String name = ((TextView) view
											.findViewById(R.id.schedule_rename_text))
											.getText().toString();

									selectedSchedule.set("name", name);
									selectedSchedule.save(database);
									setTitle(name);

									Toast.makeText(
											getApplicationContext(),
											"Successfully renamed schedule.",
											Toast.LENGTH_LONG
									).show();
								}
							}
					);
				}

				break;

			case R.id.nav_course:
				if (selectedSchedule == null)
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"System is still loading. Please wait.",
									Toast.LENGTH_SHORT
							).show();
						}
					});
				else
					startActivity(new Intent(this, CourseActivity.class));
				break;


			case R.id.nav_ChangeTrimester:
				startActivity(new Intent(this, ChangeTrimester.class));
				break;
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}
}
