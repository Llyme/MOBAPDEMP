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
	private RecyclerView recycler;
	private ContactAdapter adapter;
	private TextView header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);

		recycler = findViewById(R.id.recycler);
		adapter = new ContactAdapter();
		header = findViewById(R.id.activity_course_header);

		recycler.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.VERTICAL,
				false
		));
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
			searchView.setOnCloseListener(new SearchView.OnCloseListener() {
				@Override
				public boolean onClose() {
					//TODO: Reset your views
					return false;
				}
			});
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
									public void call(String a, List<Course> b) {
										final String term = a;
										final List<Course> courses = b;

										if (debounce == null || !debounce.equals(course_name))
											return;

										debounce = null;
										adapter.clear();

										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												if (term == null) {
													header.setText("Oh no! No internet access!");

													return;
												}

												if (courses != null) {
													header.setText(
															term + "\n" + course_name + " - " +
																	courses.size() + " slot(s)"
													);

													for (Course course : courses)
														adapter.add(course);
												} else {
													header.setText(
															term + "\n" + course_name +
																	" - no slots"
													);
												}

												adapter.notifyDataSetChanged();
											}
										});
									}
								});
							}
						}, DELAY);
					} else if (s.length() == 0) {
					}
					return false;
				}

			});
		}

		return true;
	}
}
