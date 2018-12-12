package com.example.mobapdemp.Database.Entities;

import android.database.Cursor;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.Table;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends Entity {
	private static EntityReference ENTITY_REFERENCE;
	final private static String[] INTS = new String[]{
			"id"
	};
	final private static String[] STRINGS = new String[]{
			"name",
			"term"
	};

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
					INTS,
					STRINGS
			);

		super.initialize(ENTITY_REFERENCE);

		values.remove("id");
		values.put("name", name);
		values.put("term", term);

		courses = new ArrayList<>();
	}

	public Schedule(Cursor cursor) {
		this("", "");

		for (String name : INTS)
			values.put(name, cursor.getInt(cursor.getColumnIndex(name)));

		for (String name : STRINGS)
			values.put(name, cursor.getString(cursor.getColumnIndex(name)));
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
		for (Course course : courses)
			if (course.getString("name").equals(name))
				return true;

		return false;
	}

	/**
	 * Check if this schedule has a course with the given ID.
	 *
	 * @param int The ID.
	 * @return True if the same ID, otherwise False.
	 */
	public Boolean hasCourse(int id) {
		for (Course course : courses)
			if (course.getInt("id") == id)
				return true;

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
	 * Add a course to the schedule. Conflicts will be checked. It will automatically remove
	 * courses with the same name.
	 *
	 * @param course The course to be added.
	 * @return True if it was successfully added, otherwise False.
	 */
	public Boolean addCourse(Course course) {
		for (int i = 0; i < courses.size(); i++)
			if (courses.get(i).getString("name")
					.equals(course.getString("name"))) {
				courses.remove(i);

				break;
			}

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
		for (int i = 0; i < courses.size(); i++)
			if (courses.get(i).getString("name").equals(name)) {
				courses.remove(i);

				return true;
			}

		return false;
	}

	/**
	 * Remove a course from the schedule with the same ID. Nothing happens if it doesn't exist.
	 *
	 * @param id The ID of the course.
	 * @return Returns True if successfully removed, otherwise False.
	 */
	public Boolean removeCourse(int id) {
		for (int i = 0; i < courses.size(); i++)
			if (courses.get(i).getInt("id") == id) {
				courses.remove(i);

				return true;
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
		long id = super.save(
				db,
				"id=" + (values.containsKey("id") ?
						values.getAsInteger("id") : null)
		);

		for (Course course : courses) {
			course.save(db);

			new ScheduleCourse((int) id, course.getInt("id")).save(db);
		}

		return id;
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

	public static void getAll(Database db) {
		Cursor cursor = db.queryEntity("SELECT * FROM schedules");

		while (cursor.moveToNext()) {
			Schedule schedule = new Schedule(cursor);
			int schedule_id = schedule.getInt("id");

			Cursor cursor_schedule_courses = db.queryEntity(
					"SELECT id, course_id FROM schedule_courses " +
							"WHERE schedule_id=" + schedule_id
			);

			while (cursor_schedule_courses.moveToNext()) {
				int schedule_course_id = cursor_schedule_courses.getInt(
						cursor_schedule_courses.getColumnIndex("id")
				);
				int course_id = cursor_schedule_courses.getInt(
						cursor_schedule_courses.getColumnIndex("course_id")
				);

				Cursor cursor_courses = db.queryEntity(
						"SELECT * FROM courses " +
								"WHERE id=" + course_id
				);

				if (cursor_courses.moveToNext()) {
					Course course = new Course(cursor_courses);
					schedule.addCourse(course);

					Cursor cursor_course_days = db.queryEntity(
							"SELECT * FROM course_days " +
									"WHERE course_id=" + course_id
					);

					while (cursor_course_days.moveToNext())
						course.addCourseDay(new CourseDay(cursor_course_days));
				} else
					db.deleteEntity(
							"schedule_courses",
							"id=" + schedule_course_id
					);
			}
		}
	}
}
