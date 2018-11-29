package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;

public class CourseDay extends Entity {
	private static EntityReference ENTITY_REFERENCE;

	/**
	 * Create a `CourseDay` document. As the name suggests, this represents a course's
	 * assigned day. Courses may have multiple `CourseDay` documents.
	 *
	 * {int} id - The unique ID number for this document.
	 * {int} course_id - The associated `Course` document's id.
	 * {int} start - The starting time in minutes (0730 = 7*60 + 30 = 450).
	 * {int} length - The total duration for the assigned day in minutes (end - start).
	 * {String} professor - The professor's name.
	 * {String} day - The assigned day. This may also have specific dates.
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
							"day TEXT NOT NULL",
					new String[]{
							"id",
							"course_id",
							"start",
							"length"
					},
					new String[]{
							"professor",
							"day"
					}
			);

		super.initialize(ENTITY_REFERENCE);

		values.remove("id");
	}

	/**
	 * Set the time for this day with the given text like "0730 - 0900". You can still set it
	 * manually via `CourseDay.set("start", 450)` and `CourseDay.set("length", 90)`.
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
	 * @param db The database.
	 * @return This document's id.
	 */
	public long save(Database db) {
		return super.save(db, "id=" + values.getAsInteger("id"));
	}

	/**
	 * Attempt to delete this document if it exists in the database.
	 * @param db The database.
	 * @return This document's id.
	 */
	public long delete(Database db) {
		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
