package com.example.mobapdemp.Database.Entities;

import com.example.mobapdemp.Database.Database;

public class ScheduleCourse extends Entity {
	private static EntityReference ENTITY_REFERENCE;

	/**
	 * Create a Schedule-Course relation.
	 * <p>
	 * {int} schedule_id - The unique ID number for the schedule.
	 * {int} course_id - The unique ID number for the course.
	 *
	 * @param schedule_id The schedule.
	 * @param course_id   The course.
	 */
	public ScheduleCourse(int schedule_id, int course_id) {
		if (ENTITY_REFERENCE == null)
			ENTITY_REFERENCE = new EntityReference(
					"schedule_courses",
					"schedule_id INTEGER NOT NULL," +
							"course_id INTEGER NOT NULL",
					new String[]{
							"id",
							"schedule_id",
							"course_id"
					},
					null
			);

		super.initialize(ENTITY_REFERENCE);

		values.put("schedule_id", schedule_id);
		values.put("course_id", course_id);
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
				"schdule_id=" + values.getAsInteger("schedule_id") + " AND " +
						"course_id=" + values.getAsInteger("course_id")
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
				"schdule_id=" + values.getAsInteger("schedule_id") + " AND " +
						"course_id=" + values.getAsInteger("course_id")
		);
	}
}
