package com.example.mobapdemp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;

import java.util.ArrayList;
import java.util.List;

public class Table {
	final private static int ORIGIN_TIME = 450; // Starts at 0730.
	final private static int COLORS[] = new int[]{
			Color.rgb(220, 220, 220), // Time color
			Color.rgb(211, 211, 211), // Even column color
			Color.rgb(202, 201, 201), // Odd column color
			Color.rgb(157, 254, 151), // Slot not full color
			Color.rgb(255, 0, 0), // Slot full color
			Color.rgb(0, 0, 0) // Text color
	};

	private TextView layout[][];
	private List<List<CourseDay>> courseDays;

	public Table(AppCompatActivity view, TableLayout table) {
		int hr = 7, mn = 30;

		layout = new TextView[56][4];

		courseDaysInit();

		for (int x = 0; x < 56; x++) {
			TableRow row = new TableRow(view);
			row.setWeightSum(5);


			TableRow.LayoutParams param = new TableRow.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT,
					1
			);


			TextView time = new TextView(view);
			time.setBackgroundColor(COLORS[0]);
			//time.setPadding(0, 5, 0 ,5);
			time.setGravity(Gravity.CENTER);
			time.setText(
					(hr < 10 ? "0" : "") + String.valueOf(hr) +
							(mn == 0 ? "00" : String.valueOf(mn))
			);
			time.setLayoutParams(param);
			row.addView(time);


			for (int y = 0; y < 4; y++) {
				layout[x][y] = new TextView(view);
				layout[x][y].setGravity(Gravity.CENTER);
				layout[x][y].setTextColor(COLORS[5]);
				layout[x][y].setBackgroundColor(COLORS[1 + y % 2]);
				row.addView(layout[x][y]);

				layout[x][y].setLayoutParams(param);
			}

			table.addView(row);

			mn += 15;

			if (mn == 60) {
				mn = 0;
				hr++;
			}
		}
	}

	/**
	 * Clear the entire table.
	 */
	public void clear() {
		for (int i = 0; i < 4; i++) {
			List list = courseDays.get(i);

			for (Object obj : list) {
				CourseDay courseDay = (CourseDay) obj;

				int start = courseDay.getInt("start") - ORIGIN_TIME;
				int end = start + courseDay.getInt("length");

				colorize(i, start / 15, end / 15, COLORS[1 + i % 2]);
			}
		}

		courseDaysInit();
	}

	/**
	 * Draw the course into the table. This will remove anything that conflicts with the course.
	 * You can only draw courses with regular schedules (`T, W, H, F` only).
	 *
	 * @param course The course.
	 */
	public void draw(Course course) {
		for (CourseDay courseDay1 : course.getCourseDays()) {
			int i = getDayIndex(courseDay1.getString("day"));

			if (i != -1) {
				List list = courseDays.get(i);

				for (Object obj : list) {
					CourseDay courseDay0 = (CourseDay) obj;

					if (courseDay0.conflictsWith(courseDay1)) {
						list.remove(obj);

						int start = courseDay0.getInt("start") - ORIGIN_TIME;
						int end = start + courseDay0.getInt("length");

						colorize(i, start / 15, end / 15, COLORS[1 + i % 2]);
					}
				}

				list.add(courseDay1);

				int start = courseDay1.getInt("start") - ORIGIN_TIME;
				int end = start + courseDay1.getInt("length");

				colorize(
						i,
						start / 15,
						end / 15,
						COLORS[course.getInt("enrolled") <
								course.getInt("enroll_cap") ? 3 : 4]
				);

				setText(course.getString("name"), i, start / 15);
			}
		}
	}

	/**
	 * Get the proper index for the given day.
	 *
	 * @param day The string.
	 * @return The index. If not applicable, returns -1.
	 */
	private int getDayIndex(String day) {
		if (day.length() > 1)
			return -1;

		switch (day) {
			case "T":
				return 0;

			case "W":
				return 1;

			case "H":
				return 2;

			case "F":
				return 3;
		}

		return -1;
	}

	/**
	 * Set the color for a part of the table. This will also remove any text related to it.
	 *
	 * @param column   The column.
	 * @param rowstart Which row to start with.
	 * @param rowend   Which row to end to.
	 * @param color    The color you want.
	 */
	private void colorize(int column, int rowstart, int rowend, int color) {
		for (int i = Math.max(0, rowstart); i < Math.min(56, rowend); i++) {
			layout[i][column].setText("");
			layout[i][column].setBackgroundColor(color);
		}
	}

	/**
	 * Set the given cell's text.
	 *
	 * @param text   The text.
	 * @param column Column.
	 * @param row    Row.
	 */
	private void setText(String text, int column, int row) {
		layout[row][column].setText(text);
	}

	/**
	 * Initialize the `courseDays` `ArrayList`s.
	 */
	private void courseDaysInit() {
		courseDays = new ArrayList<>(4);

		for (int i = 0; i < 4; i++) {
			List<CourseDay> l = new ArrayList<>();
			courseDays.add(i, l);
		}
	}
}
