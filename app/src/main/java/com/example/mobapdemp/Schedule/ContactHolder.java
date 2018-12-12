package com.example.mobapdemp.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mobapdemp.R;

public class ContactHolder extends RecyclerView.ViewHolder {
	private TextView name;

	public ContactHolder(View view) {
		super(view);

		name = view.findViewById(R.id.schedule_model_name);

		view.findViewById(R.id.schedule_model_load).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	public void set(String name) {
		this.name.setText(name);
	}
}
