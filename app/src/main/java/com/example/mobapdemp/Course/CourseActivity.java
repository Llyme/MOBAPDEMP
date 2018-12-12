package com.example.mobapdemp.Course;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.R;
import com.example.mobapdemp.Scraper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CourseActivity extends AppCompatActivity {
	private static String header_text;
	private RecyclerView recycler;
	private ContactAdapter adapter;
	private TextView header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);

		recycler = findViewById(R.id.course_recycler);
		adapter = new ContactAdapter(this);
		header = findViewById(R.id.activity_course_header);

		if (header_text != null)
			header.setText(header_text);

		recycler.setLayoutManager(new LinearLayoutManager(this));
		recycler.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_course_menu, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

		if (searchView != null) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				final private int DELAY = 500;
				private Timer timer;
				private String debounce;

				@Override
				public boolean onQueryTextSubmit(String s) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String s) {
					final String course_name = s.toUpperCase();

					if (timer != null)
						timer.cancel();

					timer = null;

					if (s.length() > 1) {
						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								debounce = course_name;

								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										header.setText("Requesting " + course_name + "...");
									}
								});

								new Scraper(course_name, new Scraper.Listener() {
									@Override
									public void call(final String term,
									                 final List<Course> courses) {

										if (debounce == null || !debounce.equals(course_name))
											return;

										debounce = null;

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												adapter.clear();

												if (term == null) {
													header_text = "Oh no! The server is offline!";
													header.setText(header_text);

													return;
												}

												if (courses != null) {
													header_text =
															term + "\n" + course_name + " - " +
															courses.size() + " slot(s)";

													for (Course course : courses)
														adapter.add(course);
												} else
													header_text =
															term + "\n" + course_name +
																	" - no slots";

												header.setText(header_text);
												adapter.notifyDataSetChanged();
											}
										});
									}
								});
							}
						}, DELAY);
					}

					return false;
				}

			});
		}

		return true;
	}
}
