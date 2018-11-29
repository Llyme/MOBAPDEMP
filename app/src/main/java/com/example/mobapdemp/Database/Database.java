package com.example.mobapdemp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	private static Database instance;
	private static OpenHelper helper;
	private static SQLiteDatabase database;

	/**
	 * Get an instance of the database. It will only create 1 instance at first call, and
	 * succeeding calls will always give that instance.
	 *
	 * @param context The context.
	 * @return The database.
	 */
	public static Database getInstance(Context context) {
		if (instance == null)
			synchronized (Database.class) {
				if (instance == null) {
					instance = new Database();
					helper = OpenHelper.getInstance(context);
					database = helper.getWritableDatabase();
				}
			}

		return instance;
	}

	/**
	 * Insert an entity into the table as a document.
	 *
	 * @param table  The table's name.
	 * @param values The values that the entity has. Will throw an error if the values do not
	 *               match the requirements to make a document for the given table.
	 * @return The given `row id` of the document. This is different from the usual `id` where
	 * this is automatically given by SQLite and is based on what row it was placed on.
	 */
	public long createEntity(String table, ContentValues values) {
		return database.insert(
				table,
				null,
				values
		);
	}

	/**
	 * Make a query to the database.
	 *
	 * @param sql SQL statement.
	 * @return An iterator which is usually used with a `while loop`.
	 */
	public Cursor queryEntity(String sql) {
		return database.rawQuery(sql, null);
	}

	/**
	 * Update an existing entity. Will throw an error if it doesn't exist.
	 *
	 * @param table       The table's name.
	 * @param values      The values that you want to update.
	 * @param whereClause Distinguish which ones you want to update.
	 * @return Total number of documents that were updated.
	 */
	public long updateEntity(String table, ContentValues values, String whereClause) {
		return database.update(
				table,
				values,
				whereClause,
				null
		);
	}

	/**
	 * Delete a document within the given table.
	 *
	 * @param table       The table's name.
	 * @param whereClause This will filter which one will be deleted. Set to `null` to delete
	 *                    everything.
	 * @return More than 0 (up to the total documents that matched the `whereClause`) if
	 * successful, otherwise nothing was deleted.
	 */
	public long deleteEntity(String table, String whereClause) {
		return database.delete(table, whereClause, null);
	}
}