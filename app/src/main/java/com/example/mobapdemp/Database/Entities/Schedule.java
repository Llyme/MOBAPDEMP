package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;

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
	 *
	 * @param name The name for this schedule.
	 * @param term This schedule's term and year. You cannot change this.
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
			for (CourseDay courseDay0 : course.getCourseDays())
				for (CourseDay courseDay1 : course1.getCourseDays())
					if (courseDay0.getString("day")
							.equals(courseDay1.getString("day"))) {
						int start0 = courseDay0.getInt("start"),
								length0 = courseDay0.getInt("length"),
								end0 = start0 + length0,
								start1 = courseDay1.getInt("start"),
								length1 = courseDay1.getInt("length"),
								end1 = start1 + length1;

						if ((start0 < end1 && start1 < end0) ||
								(start0 > start1 && end0 < end1) ||
								(start1 > start0 && end1 < end0))
							return true;
					}

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

	public long save(Database db) {
		int id = values.getAsInteger("id");

		for (Course course : courses)
			course.save(db);

		return super.save(db, "id=" + id);
	}

	public long delete(Database db) {
		return super.delete(db, "id=" + values.getAsInteger("id"));
	}
}
