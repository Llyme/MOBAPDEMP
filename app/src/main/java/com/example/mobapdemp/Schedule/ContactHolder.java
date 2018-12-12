package com.example.mobapdemp.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobapdemp.Database.Entities.Schedule;
import com.example.mobapdemp.MainActivity;
import com.example.mobapdemp.R;

public class ContactHolder extends RecyclerView.ViewHolder {
	private TextView name;
	private Schedule schedule;

	public ContactHolder(View view, final ScheduleActivity self, final ContactAdapter adapter) {
		super(view);

		name = view.findViewById(R.id.schedule_model_name);

		view.findViewById(R.id.schedule_model_load).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MainActivity.selectSchedule(schedule);

						self.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(
										self,
										"Schedule successfully loaded.",
										Toast.LENGTH_SHORT
								).show();
							}
						});
					}
				}
		);

		view.findViewById(R.id.schedule_model_delete).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MainActivity.showDialog(
								self,
								"Are you sure you want to delete this schedule?",
								null,
								"Yes",
								"No",
								new Runnable() {
									@Override
									public void run() {
										adapter.remove(schedule);
										schedule.delete(MainActivity.database);

										Toast.makeText(
												self,
												"Schedule successfully deleted.",
												Toast.LENGTH_SHORT
										).show();
									}
								}
						);
						MainActivity.selectSchedule(schedule);
					}
				}
		);
	}

	public void set(Schedule schedule) {
		this.schedule = schedule;

		name.setText(schedule.getString("name"));
	}
}
