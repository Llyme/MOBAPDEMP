package com.example.mobapdemp.Schedule;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobapdemp.Database.Entities.Schedule;
import com.example.mobapdemp.MainActivity;
import com.example.mobapdemp.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
	private static List<Schedule> contactList;

	public ContactAdapter() {
		contactList = Schedule.getAll(MainActivity.database);
	}

	@Override
	public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.schedule_model, parent, false);

		return new ContactHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
		holder.set(contactList.get(position).getString("name"));
	}

	@Override
	public int getItemCount() {
		return contactList.size();
	}
}
