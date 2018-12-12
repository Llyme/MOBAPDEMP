package com.example.mobapdemp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.Database.Entities.CourseDay;
import com.example.mobapdemp.Database.Entities.Entity;
import com.example.mobapdemp.Database.Entities.Schedule;
import com.example.mobapdemp.Database.Entities.ScheduleCourse;

public class OpenHelper extends SQLiteOpenHelper {
	private static OpenHelper instance;

	private static final String DATABASE_NAME = "kunoichi-database";
	private static final int DATABASE_VERSION = 1;

	// Create sample entities.
	private static Entity[] ENTITIES = new Entity[]{
			new Course(""),
			new CourseDay(),
			new Schedule(null, null),
			new ScheduleCourse(0, 0)
	};

	public OpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static OpenHelper getInstance(Context context) {
		if (instance == null)
			synchronized (OpenHelper.class) {
				if (instance == null) {
					instance = new OpenHelper(context);
				}
			}

		return instance;
	}

	public static OpenHelper getInstance() {
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Entity entity : ENTITIES)
			db.execSQL(entity.getEntityReference().string("DB_CREATE"));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (Entity entity : ENTITIES)
			db.execSQL(entity.getEntityReference().string("DB_DROP"));

		onCreate(db);
	}
}
