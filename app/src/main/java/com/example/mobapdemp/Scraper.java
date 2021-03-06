package com.example.mobapdemp;

import android.os.AsyncTask;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scraper extends AsyncTask<Void, Void, Void> {
	private static String proxy = "https://cors.io/?";
	private String course_name, term;
	private Listener listener;

	public interface Listener {
		void call(String term, List<Course> courses);
	}

	/**
	 * Create a new scraper for the given course name.
	 *
	 * @param course   The course's name.
	 * @param listener The function that will be called.
	 */
	public Scraper(String term, String course, Listener listener) {
		super();

		this.course_name = course;
		this.term = term;
		this.listener = listener;

		this.execute();
	}

	/**
	 * Grab the data that you need and trim off the fancy stuff.
	 *
	 * @param line Text that contains your data.
	 * @return The data.
	 */
	private String getRelevantData(String line) {
		int a = 0, b;

		while ((b = line.indexOf('>', a) + 1) != 0 &&
				line.substring(b).length() > 0)
			a = b;

		b = line.indexOf('<', a);

		if (b == -1)
			b = line.length();

		return line.substring(a, b);
	}

	protected Void doInBackground(Void... params) {
		try {
			URL url = new URL(
					proxy +
							"http://enroll.dlsu.edu.ph/dlsu/view_actual_count?p_course_code=" +
							course_name
			);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();


			/* Load from database. */

			if ((term != null && !term.equals(MainActivity.currentTerm)) ||
					/* No internet connection. Check database. */
					connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				List<Course> courses = Course.getAll(
						MainActivity.database,
						course_name,
						term
				);

				if (courses.size() > 0)
					listener.call(term, courses);
				else
					listener.call(null, null);

				return null;
			}


			/* Let the request construct itself. */

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			/* Read request. */

			BufferedReader reader =
					new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			// Course slots collected.
			List<Course> courses = new ArrayList<>();
			// Course being edited.
			Course course = null;
			List<CourseDay> courseDays = new ArrayList<>();
			// Table data.
			List<String> td = new ArrayList<>();

			Boolean tr = false;
			int table = 10; // The data that we need is in the 10th `table`.
			int skip = 1; // Skip the first set of data. These are the headers.
			while ((line = reader.readLine()) != null && table >= 0) {
				// Grab the current term and year.
				if (line.contains("\"content_title\"")) {
					term = getRelevantData(line);

					// User just wants the term and year.
					if (course_name.length() == 0) {
						listener.call(term, null);

						break;
					}
				} else if (line.contains("<TABLE") || line.contains("<table"))
					table--;
				else if (table == 0) {
					if (!tr) {
						if (line.contains("<TR") || line.contains("<tr")) {
							if (skip > 0)
								skip--;
							else
								tr = true;
						}
					} else {
						if (line.equals("</TR>") || line.contains("</tr>")) {
							if (td.size() > 1) {
								// General data.
								String id = td.get(0);

								if (id.equals("&nbsp;")) {
									if (course != null) {
										// This is probably a course with specific day and time.
										String day = td.get(3),
												time = td.get(4);

										CourseDay courseDay = new CourseDay();
										courseDays = new ArrayList<>();

										courseDays.add(courseDay);
										course.addCourseDay(courseDay);

										if (!day.equals("&nbsp;"))
											courseDay.set("day", day);

										if (!time.equals("&nbsp;"))
											courseDay.setTime(time);
									}
								} else {
									if (course != null)
										course.save(MainActivity.database);

									course = new Course(course_name);
									courses.add(course);

									course.set("term", term);

									course.set("id", Integer.valueOf(id));

									course.set(
											"section",
											td.get(2).replaceFirst("\\s++$", "")
									);

									String days = td.get(3);
									int days_length = days.length();

									if (days_length > 2) {
										CourseDay courseDay = new CourseDay();
										courseDays = new ArrayList<>();

										courseDays.add(courseDay);
										courseDay.set("day", td.get(3));
										courseDay.setTime(td.get(4));

										course.addCourseDay(courseDay);
									} else {
										courseDays = new ArrayList<>();

										for (int i = 0; i < days_length; i++) {
											CourseDay courseDay = new CourseDay();

											courseDays.add(courseDay);
											courseDay.set(
													"day",
													String.valueOf(days.charAt(i))
											);
											courseDay.setTime(td.get(4));

											course.addCourseDay(courseDay);
										}
									}


									course.set("room", td.get(5));
									course.set(
											"enroll_cap",
											Integer.valueOf(td.get(6))
									);
									course.set(
											"enrolled",
											Integer.valueOf(td.get(7))
									);
									course.set("remarks", td.get(8));
								}
							} else
								// Professor is usually placed in a separate line.
								for (CourseDay courseDay : courseDays)
									courseDay.set("professor", td.get(0));

							td = new ArrayList<>();
							tr = false;
						} else
							td.add(getRelevantData(line));
					}
				}
			}


			/* Check for database entries if empty. */

			if (courses.size() == 0)
				courses = Course.getAll(
						MainActivity.database,
						course_name,
						term
				);

			listener.call(term, courses);
		} catch (Exception e) {
			e.printStackTrace();
			listener.call(term, null);
		}

		return null;
	}
}
