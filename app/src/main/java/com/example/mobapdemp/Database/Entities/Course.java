package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class Course extends Entity {
	private static EntityReference ENTITY_REFERENCE;

	private List<CourseDay> courseDays;

	public Course(String name) {
		if (ENTITY_REFERENCE == null)
			ENTITY_REFERENCE = new EntityReference(
					"courses",
					"id INTEGER PRIMARY KEY NOT NULL," +
							"enrolled INTEGER NOT NULL," +
							"enroll_cap INTEGER NOT NULL," +
							"name TEXT NOT NULL," +
							"section TEXT NOT NULL," +
							"room TEXT NOT NULL," +
							"remarks TEXT NOT NULL," +
							"term TEXT NOT NULL",
					new String[]{
							"id",
							"enrolled",
							"enroll_cap"
					},
					new String[]{
							"name",
							"section",
							"room",
							"remarks",
							"term"
					}
			);

		super.initialize(ENTITY_REFERENCE);

		values.remove("id");
		values.put("name", name);

		courseDays = new ArrayList<>();
	}

	public void addCourseDay(CourseDay courseDay) {
		courseDays.add(courseDay);
	}

	public CourseDay[] getCourseDays() {
		return courseDays.toArray(new CourseDay[0]);
	}

	public void clearCourseDays() {
		courseDays = new ArrayList<>();
	}

	public long save(Database db) {
		int id = values.getAsInteger("id");

		for (CourseDay courseDay : courseDays) {
			courseDay.set("course_id", id);
			courseDay.save(db);
		}

		return super.save(db, "id=" + id);
	}

	public long delete(Database db) {
		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
