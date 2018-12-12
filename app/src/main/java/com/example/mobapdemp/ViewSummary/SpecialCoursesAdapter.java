package com.example.mobapdemp.ViewSummary;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.R;
import com.example.mobapdemp.Utility;

import java.util.ArrayList;

public class SpecialCoursesAdapter extends RecyclerView.Adapter<SpecialCoursesHolder> {

    private ArrayList<SpecialCourseModel> specialCourseModel;
    private ViewSummary viewSummary;
    private int expandedPosition = -1;
    private int previousExpandedPosition = -1;

    public SpecialCoursesAdapter(ViewSummary viewSummary) {
        specialCourseModel = new ArrayList<>();
        this.viewSummary = viewSummary;
    }

    @Override
    public SpecialCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.special_courses_item_layout, parent, false);

        SpecialCoursesHolder holder = new SpecialCoursesHolder(view, viewSummary);

        return holder;
    }

    @Override
    public void onBindViewHolder(SpecialCoursesHolder specialCoursesHolder, final int position) {

        specialCoursesHolder.setCourseCode(specialCourseModel.get(position).getCourseCode());
        specialCoursesHolder.setAll(specialCourseModel.get(position).getCourseProf(),
                specialCourseModel.get(position).getCourseDays(), specialCourseModel.get(position).getCourseTime(),
                specialCourseModel.get(position).getCourseRoom(), specialCourseModel.get(position).getSize());
        specialCoursesHolder.setBackgroundColor(specialCourseModel.get(position).getBackgroundColor());

        /* Handle the hide/show of button */
        final boolean isExpanded = position == expandedPosition;
        specialCoursesHolder.setButtonVisibility(isExpanded?View.VISIBLE:View.GONE);
        specialCoursesHolder.itemView.setActivated(isExpanded);

        /* Remove subject from database and RecyclerView model */
        specialCoursesHolder.setBtnRemoveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.deleteCourse(Database.getInstance(viewSummary.getApplicationContext()),
                        specialCourseModel.get(position).getCourseCode());

                specialCourseModel.remove(position);
                notifyDataSetChanged();
            }
        });

        if(isExpanded) {
            previousExpandedPosition = position;
        }

        specialCoursesHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialCourseModel.size();
    }

    public void addCourse(String[] courseInfo) {
        int color;
        /* If enrolled >= enroll_cap */
        if(Integer.parseInt(courseInfo[1]) >= Integer.parseInt(courseInfo[2])) {
            color = Color.parseColor("#c02034"); //red
        } else {
            color = Color.parseColor("#169c78"); //green
        }

        String[] date = new String[Integer.parseInt(courseInfo[0])];
        String[] time = new String[Integer.parseInt(courseInfo[0])];
        String[] room = new String[Integer.parseInt(courseInfo[0])];
        String[] prof = new String[Integer.parseInt(courseInfo[0])];

        int dateIndex = 8;
        int timeIndex = 6;
        int roomIndex = 5;
        int profIndex = 7;

        for(int i = 0; i < Integer.parseInt(courseInfo[0]); i++) {

            date[i] = courseInfo[dateIndex];
            time[i] = courseInfo[timeIndex];
            room[i] = courseInfo[roomIndex];
            prof[i] = courseInfo[profIndex];

            if(i == 0) {
                timeIndex = 9;
                dateIndex = 10;
                roomIndex = 11;
                profIndex = 12;
            } else {
                timeIndex += 4;
                dateIndex += 4;
                roomIndex += 4;
                profIndex += 4;
            }
        }

        specialCourseModel.add(new SpecialCourseModel(courseInfo[3], date, time, room, prof, color,
                Integer.parseInt(courseInfo[0])));

        notifyDataSetChanged();
    }
}
