package com.example.mobapdemp.Schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mobapdemp.R;

public class ScheduleActivity extends AppCompatActivity {
	private RecyclerView recycler;
	private ContactAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		recycler = findViewById(R.id.schedule_recycler);
		adapter = new ContactAdapter();

		recycler.setLayoutManager(new LinearLayoutManager(this));
		recycler.setAdapter(adapter);
	}
}
