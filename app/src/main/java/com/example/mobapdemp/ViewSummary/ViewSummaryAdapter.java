package com.example.mobapdemp.ViewSummary;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.R;
import com.example.mobapdemp.Utility;

import java.util.ArrayList;

public class ViewSummaryAdapter extends RecyclerView.Adapter<ViewSummaryHolder> {

    private ArrayList<ViewSummaryModel> currentSubjectsList;
    private int expandedPosition = -1;
    private int previousExpandedPosition = -1;

    private ViewSummary viewSummary;

    public ViewSummaryAdapter(ViewSummary viewSummary) {
        currentSubjectsList = new ArrayList<>();
        this.viewSummary = viewSummary;
    }

    @Override
    public ViewSummaryHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_summary_item_layout, parent, false);

        ViewSummaryHolder holder = new ViewSummaryHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewSummaryHolder viewSummaryHolder, final int position) {
        viewSummaryHolder.setCourseCode(currentSubjectsList.get(position).getCourseCode());
        viewSummaryHolder.setCourseDays(currentSubjectsList.get(position).getCourseDays());
        viewSummaryHolder.setCourseTime(currentSubjectsList.get(position).getCourseTime());
        viewSummaryHolder.setCourseRoom(currentSubjectsList.get(position).getCourseRoom());
        viewSummaryHolder.setCourseProfessor(currentSubjectsList.get(position).getCourseProf());
        viewSummaryHolder.setBackgroundColor(currentSubjectsList.get(position).getBackgroundColor());

        /* Remove subject from database and RecyclerView model */
        viewSummaryHolder.setBtnRemoveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.deleteCourse(Database.getInstance(viewSummary.getApplicationContext()),
                        currentSubjectsList.get(position).getCourseCode());

                currentSubjectsList.remove(position);
                notifyDataSetChanged();
            }
        });

        /* Handle the hide/show of button */
        final boolean isExpanded = position == expandedPosition;
        viewSummaryHolder.setButtonVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewSummaryHolder.itemView.setActivated(isExpanded);

        if(isExpanded) {
            previousExpandedPosition = position;
        }

        viewSummaryHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return currentSubjectsList.size();
    }

    protected void addCourse(String[] courseInfo) {

        int color;
        /* If enrolled >= enroll_cap */
        if(Integer.parseInt(courseInfo[1]) >= Integer.parseInt(courseInfo[2])) {
            color = Color.parseColor("#c02034"); //red
        } else {
            color = Color.parseColor("#169c78"); //green
        }
        currentSubjectsList.add(new ViewSummaryModel(courseInfo[3], courseInfo[8], courseInfo[6],
                courseInfo[5], courseInfo[7], color));

        notifyDataSetChanged();
    }
}
