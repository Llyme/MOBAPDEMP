package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;

public class CourseDay extends Entity {
	private static EntityReference ENTITY_REFERENCE;

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

	public void setTime(String text) {
		int start = Integer.valueOf(text.substring(0, 2)) * 60 +
				Integer.valueOf(text.substring(2, 4));
		int length = Integer.valueOf(text.substring(7, 9)) * 60 +
				Integer.valueOf(text.substring(9, 11)) - start;

		values.put("start", start);
		values.put("length", length);
	}

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

	public long save(Database db) {
		return super.save(db, "id=" + values.getAsInteger("id"));
	}

	public long delete(Database db) {
		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
