package com.example.mobapdemp.Course;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobapdemp.Database.Entities.Course;
import com.example.mobapdemp.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
	private static List<Course> contactList = new ArrayList<>();
	private ContactHolder selected;

	public void clear() {
		selected = null;
		final int size = contactList.size();
		contactList.clear();

		notifyItemRangeRemoved(0, size);
	}

	public void toggle(ContactHolder holder, Boolean flag) {
		if (!flag) {
			if (selected == holder)
				selected = null;
		} else {
			if (selected != null) {
				selected.toggle(false);
			}

			selected = holder;
		}
	}

	public void add(Course course) {
		contactList.add(course);
	}

	@Override
	public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.course_model, parent, false);

		return new ContactHolder(view, this);
	}

	@Override
	public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
		holder.set(contactList.get(position));
	}

	@Override
	public int getItemCount() {
		return contactList.size();
	}
}
