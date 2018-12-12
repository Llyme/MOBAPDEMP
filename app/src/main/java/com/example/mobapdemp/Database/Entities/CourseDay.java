package com.example.mobapdemp.Database.Entities;

import android.database.Cursor;

import com.example.mobapdemp.Database.Database;

public class CourseDay extends Entity {
	private static EntityReference ENTITY_REFERENCE;
	final private static String[] INTS = new String[]{
			"id",
			"course_id",
			"start",
			"length"
	};
	final private static String[] STRINGS = new String[]{
			"professor",
			"day",
			"term"
	};

	/**
	 * Create a `CourseDay` document. As the name suggests, this represents a course's
	 * assigned day. Courses may have multiple `CourseDay` documents.
	 * <p>
	 * {int} course_id - The associated `Course` document's id.
	 * {int} start - The starting time in minutes (0730 = 7*60 + 30 = 450).
	 * {int} length - The total duration for the assigned day in minutes (end - start).
	 * {String} professor - The professor's name.
	 * {String} day - The assigned day. This may also have specific dates.
	 * {String} term - The term associated with this `CourseDay`.
	 * (M = Monday; T = Tuesday; W = Wednesday; H = Thursday; F = Friday; S = Saturday)
	 */
	public CourseDay() {
		if (ENTITY_REFERENCE == null)
			ENTITY_REFERENCE = new EntityReference(
					"course_days",
					"id INTEGER PRIMARY KEY NOT NULL," +
							"course_id INTEGER NOT NULL," +
							"start INTEGER NOT NULL," +
							"length INTEGER NOT NULL," +
							"professor TEXT NOT NULL," +
							"day TEXT NOT NULL," +
							"term TEXT NOT NULL",
					INTS,
					STRINGS
			);

		super.initialize(ENTITY_REFERENCE);

		values.remove("id");
	}

	public CourseDay(Cursor cursor) {
		this();

		for (String name : INTS)
			values.put(name, cursor.getInt(cursor.getColumnIndex(name)));

		for (String name : STRINGS)
			values.put(name, cursor.getString(cursor.getColumnIndex(name)));
	}

	/**
	 * Check if this `CourseDay` conficts with another `CourseDay`.
	 *
	 * @param courseDay The other course.
	 * @return `true` if does conflict, otherwise `false`.
	 */
	public Boolean conflictsWith(CourseDay courseDay) {
		if (getString("day").equals(courseDay.getString("day"))) {
			int start0 = getInt("start"),
					end0 = start0 + getInt("length"),
					start1 = courseDay.getInt("start"),
					end1 = start1 + courseDay.getInt("length");

			// Range value of 0.
			if (start0 == end0)
				return start0 >= start1 && start0 <= end1;
			else if (start1 == end1)
				return start1 >= start0 && start1 <= end0;

			int n = start0 < start1 ?
					Math.min(end0 - start1, end1 - start1) :
					Math.min(end1 - start0, end0 - start0);

			return n > 0;
		}

		return false;
	}

	/**
	 * Set the time for this day with the given text like "0730 - 0900". You can still set it
	 * manually via `CourseDay.set("start", 450)` and `CourseDay.set("length", 90)`.
	 *
	 * @param text The text.
	 */
	public void setTime(String text) {
		int start = Integer.valueOf(text.substring(0, 2)) * 60 +
				Integer.valueOf(text.substring(2, 4));
		int length = Integer.valueOf(text.substring(7, 9)) * 60 +
				Integer.valueOf(text.substring(9, 11)) - start;

		values.put("start", start);
		values.put("length", length);
	}

	/**
	 * Generate a text for the time similar to how DLSU does it (like "0730 - 0900").
	 *
	 * @return The time converted to text.
	 */
	public String readableTime() {
		int start = values.getAsInteger("start"),
				length = values.getAsInteger("length");

		int hr0 = start / 60;
		int mn0 = start - hr0 * 60;
		int hr1 = (start + length) / 60;
		int mn1 = (start + length) - hr1 * 60;

		return String.format("%02d", hr0) + String.format("%02d", mn0) + " - " +
				String.format("%02d", hr1) + String.format("%02d", mn1);
	}

	/**
	 * Attempt to save or update this document.
	 *
	 * @param db The database.
	 * @return This document's id.
	 */
	public long save(Database db) {
		return super.save(
				db,
				"id=" + values.getAsInteger("id")
		);
	}

	/**
	 * Attempt to delete this document if it exists in the database.
	 *
	 * @param db The database.
	 * @return This document's id.
	 */
	public long delete(Database db) {
		return super.delete(
				db,
				"id=" + values.getAsInteger("id")
		);
	}
}
