package com.example.mobapdemp.Database.Entities;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mobapdemp.Database.Database;

public class Entity {
	private EntityReference ENTITY_REFERENCE;
	protected ContentValues values;

	public class EntityReference {
		protected String DB_NAME, DB_CREATE, DB_DROP;
		protected String[] INTEGER, TEXT;

		public EntityReference(String DB_NAME,
		                       String DB_CREATE,
		                       String[] INTEGER,
		                       String[] TEXT) {
			this.DB_NAME = DB_NAME;
			this.DB_CREATE = "CREATE TABLE " + DB_NAME + " (" + DB_CREATE + ")";
			this.DB_DROP = "DROP TABLE IF EXISTS " + DB_NAME;
			this.INTEGER = INTEGER != null ? INTEGER : new String[]{};
			this.TEXT = TEXT != null ? TEXT : new String[]{};
		}

		public String string(String name) {
			switch (name) {
				case "DB_NAME":
					return DB_NAME;

				case "DB_CREATE":
					return DB_CREATE;

				case "DB_DROP":
					return DB_DROP;
			}

			return null;
		}
	}

	protected ContentValues initialize(EntityReference ENTITY_REFERENCE) {
		this.ENTITY_REFERENCE = ENTITY_REFERENCE;

		values = new ContentValues();

		for (String str : ENTITY_REFERENCE.INTEGER)
			values.put(str, 0);

		for (String str : ENTITY_REFERENCE.TEXT)
			values.put(str, "");

		return values;
	}

	public EntityReference getEntityReference() {
		return ENTITY_REFERENCE;
	}

	public void set(String var_name, int value) {
		for (String str : ENTITY_REFERENCE.INTEGER)
			if (str.equals(var_name)) {
				values.put(var_name, value);

				break;
			}
	}

	public void set(String var_name, String value) {
		for (String str : ENTITY_REFERENCE.TEXT)
			if (str.equals(var_name)) {
				values.put(var_name, value);

				break;
			}
	}

	public int getInt(String var_name) {
		for (String str : ENTITY_REFERENCE.INTEGER)
			if (str.equals(var_name))
				return values.getAsInteger(var_name);

		return 0;
	}

	public String getString(String var_name) {
		for (String str : ENTITY_REFERENCE.TEXT)
			if (str.equals(var_name))
				return values.getAsString(var_name);

		return "";
	}

	/**
	 * Save this entity into into the database. If `whereClause` is supplied and something
	 * was found, it will replace those instead.
	 *
	 * @param db          The database.
	 * @param whereClause Will look for documents that fits the criteria given.
	 */
	public long save(Database db, String whereClause) {
		if (whereClause != null) {
			Cursor cursor = db.queryEntity(
					"SELECT * " +
							"FROM " + ENTITY_REFERENCE.DB_NAME + " " +
							"WHERE " + whereClause
			);

			if (cursor.moveToNext())
				return db.updateEntity(ENTITY_REFERENCE.DB_NAME, values, whereClause);
		}

		return db.createEntity(ENTITY_REFERENCE.DB_NAME, values);
	}

	/**
	 * Get what term and year are saved in the database.
	 *
	 * @param db The database.
	 * @return The term and year collection.
	 */
	public static Cursor getTerms(Database db) {
		return db.queryEntity("SELECT DISTINCT term FROM courses");
	}

	public Cursor query(Database db, String whereClause) {
		return db.queryEntity("SELECT * FROM " + ENTITY_REFERENCE.DB_NAME);
	}

	/**
	 * Delete this. Nothing happens if it doesn't exist in the database.
	 *
	 * @param db          The database.
	 * @param whereClause Distinguishes which one is which.
	 * @return More than 0 (up to the total documents that matched the `whereClause`) if
	 * successful, otherwise nothing was deleted.
	 */
	public long delete(Database db, String whereClause) {
		return db.deleteEntity(
				ENTITY_REFERENCE.DB_NAME,
				whereClause
		);
	}
}
