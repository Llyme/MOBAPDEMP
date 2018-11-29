package com.example.mobapdemp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	private static Database instance;
	private static OpenHelper helper;
	private static SQLiteDatabase database;

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

	public long createEntity(String table, ContentValues values) {
		return database.insert(
				table,
				null,
				values
		);
	}

	public Cursor queryEntity(String sql) {
		return database.rawQuery(sql, null);
	}

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
	 * @param table The table's name.
	 * @param whereClause This will filter which one will be deleted. Set to `null` to delete
	 *                    everything.
	 * @return More than 0 (up to the total documents that matched the `whereClause`) if
	 * successful, otherwise nothing was deleted.
	 */
	public long deleteEntity(String table, String whereClause) {
		return database.delete(table, whereClause, null);
	}
}