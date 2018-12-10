package com.example.mobapdemp.Database.Entities;

import android.database.Cursor;

import com.example.mobapdemp.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class Course extends Entity {
	private static EntityReference ENTITY_REFERENCE;

	private List<CourseDay> courseDays;

	/**
	 * Create a new `Course` document entity.
	 * <p>
	 * {int} id - The unique ID number for this document.
	 * {int} enrolled - How many people ARE ENROLLED in this slot.
	 * {int} enroll_cap - How many people CAN ENROLL in this slot.
	 * {String} name - The name of this course.
	 * {String} room - The room assigned for this slot.
	 * {String} remarks - I don't know anything about this since it's always empty.
	 * {String} term - The term and year associated with this course.
	 *
	 * @param name The name of this course.
	 */
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

	/**
	 * Check if this course conficts with another course.
	 *
	 * @param course The other course.
	 * @return `true` if does conflict, otherwise `false`.
	 */
	public Boolean conflictsWith(Course course) {
		for (CourseDay courseDay0 : course.getCourseDays())
			for (CourseDay courseDay1 : courseDays)
				if (courseDay0.conflictsWith(courseDay1))
					return true;

		return false;
	}

	/**
	 * Add a `CourseDay` document.
	 *
	 * @param courseDay The document.
	 */
	public void addCourseDay(CourseDay courseDay) {
		courseDays.add(courseDay);
	}

	/**
	 * Get all the `CourseDay` documents.
	 *
	 * @return The documents.
	 */
	public CourseDay[] getCourseDays() {
		return courseDays.toArray(new CourseDay[0]);
	}

	/**
	 * Remove all `CourseDay` documents inside. This will also delete them from the database.
	 *
	 * @param db The database.
	 */
	public void clearCourseDays(Database db) {
		int id = values.getAsInteger("id");

		for (CourseDay courseDay : courseDays) {
			courseDay.set("course_id", id);
			courseDay.delete(db);
		}

		courseDays = new ArrayList<>();
	}

	/**
	 * Get the saved course slots for the given name and term from the database.
	 *
	 * @param db          The database.
	 * @param course_name The name of the course.
	 * @param term        The term.
	 * @return All course slots.
	 */
	public static Cursor getCourses(Database db,
	                                String course_name,
	                                String term) {
		return db.queryEntity(
				"SELECT * FROM courses WHERE " +
						"course_name=\"" + course_name + "\" AND" +
						"term=\"" + term + "\""
		);
	}

	/**
	 * Attempt to save or update this document. This will also save the `CourseDay` documents
	 * inside.
	 *
	 * @param db The database.
	 * @return This document's id.
	 */
	public long save(Database db) {
		int id = values.getAsInteger("id");

		for (CourseDay courseDay : courseDays) {
			courseDay.set("course_id", id);
			courseDay.save(db);
		}

		return super.save(db, "id=" + id);
	}

	/**
	 * Attempt to delete this document if it exists in the database. This will also delete
	 * the `CourseDay` documents inside.
	 *
	 * @param db The database.
	 * @return This document's id.
	 */
	public long delete(Database db) {
		int id = values.getAsInteger("id");

		for (CourseDay courseDay : courseDays) {
			courseDay.set("course_id", id);
			courseDay.delete(db);
		}

		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
