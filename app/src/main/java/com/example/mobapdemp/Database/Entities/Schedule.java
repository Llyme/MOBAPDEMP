package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Table;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends Entity {
	private static EntityReference ENTITY_REFERENCE;

	/**
	 * All the courses that this schedule has. The user cannot add a course if it has a conflict
	 * with something that's already in it.
	 */
	private List<Course> courses;

	/**
	 * Create an empty schedule. This will automatically handle conflicts.
	 * <p>
	 * {int} id - The unique ID number for this document.
	 * {String} name - The name for this schedule. Schedules can have the same name with
	 * another schedule.
	 * {String} term - The term and year associated with this schedule.
	 *
	 * @param name The name for this schedule.
	 * @param term This schedule's term and year.
	 */
	public Schedule(String name, String term) {
		if (ENTITY_REFERENCE == null)
			ENTITY_REFERENCE = new EntityReference(
					"schedules",
					"id INTEGER PRIMARY KEY NOT NULL," +
							"enrolled INTEGER NOT NULL," +
							"enroll_cap INTEGER NOT NULL," +
							"name TEXT NOT NULL," +
							"section TEXT NOT NULL," +
							"room TEXT NOT NULL," +
							"remarks TEXT NOT NULL," +
							"term TEXT NOT NULL",
					new String[]{
							"id"
					},
					new String[]{
							"name",
							"term"
					}
			);

		super.initialize(ENTITY_REFERENCE);

		values.remove("id");
		values.put("name", name);
		values.put("term", term);

		courses = new ArrayList<>();
	}

	public void draw(Table table) {
		table.clear();

		for (Course course : courses)
			table.draw(course);
	}

	/**
	 * Check if this schedule has a course with the given name.
	 *
	 * @param name The name of the course. Case-sensitive.
	 * @return True if there's a course with the same name, otherwise False.
	 */
	public Boolean hasCourse(String name) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getString("name").equals(name))
				return true;
		}

		return false;
	}

	/**
	 * See if there's a course in this schedule that will conflict with the given course. A
	 * conflict will occur if there is a course in this schedule with the same name, or the
	 * allotted time and day overlaps with the given course.
	 *
	 * @param course The course to be compared.
	 * @return True if there's at least 1 course that has conflicted with, otherwise False.
	 */
	public Boolean hasConflict(Course course) {
		if (hasCourse(course.getString("name")))
			return true;

		CourseDay[] courseDays0 = course.getCourseDays();

		for (Course course1 : courses)
			if (course.conflictsWith(course1))
				return true;

		return false;
	}

	/**
	 * Add a course to the schedule. Conflicts will be checked.
	 *
	 * @param course The course to be added.
	 * @return True if it was successfully added, otherwise False.
	 */
	public Boolean addCourse(Course course) {
		if (hasConflict(course))
			return false;

		courses.add(course);

		return true;
	}

	/**
	 * Remove a course from the schedule with the same name. Nothing happens if it doesn't exist.
	 *
	 * @param name The name of the course you want to remove.
	 * @return True if successfully removed, otherwise False.
	 */
	public Boolean removeCourse(String name) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getString("name").equals(name)) {
				courses.remove(i);

				return true;
			}
		}

		return false;
	}

	/**
	 * Attempt to save or update this document. This will also save the courses inside.
	 *
	 * @param db The database.
	 * @return This document's ID.
	 */
	public long save(Database db) {
		int id = values.getAsInteger("id");

		for (Course course : courses)
			course.save(db);

		return super.save(db, "id=" + id);
	}

	/**
	 * Attempt to delete this document if it exists in the database. This will not delete the
	 * courses inside.
	 *
	 * @param db The database.
	 * @return This document's id.
	 */
	public long delete(Database db) {
		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
