package com.example.mobapdemp.ViewSummary;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.R;
import com.example.mobapdemp.Utility;

public class ViewSummaryHolder extends RecyclerView.ViewHolder {

    private TextView tvCourseCode;
    private TextView tvCourseDays;
    private TextView tvCourseTime;
    private TextView tvCourseRoom;
    private TextView tvCourseProfessor;
    private Button btnRemove;
    private View bottomLine;
    private LinearLayout llExpandedLayout;
    private ConstraintLayout constraintLayout;

    public ViewSummaryHolder(View view) {
        super(view);

        tvCourseCode = view.findViewById(R.id.tvCourseCode);
        tvCourseDays = view.findViewById(R.id.tvCourseDays);
        tvCourseTime = view.findViewById(R.id.tvCourseTime);
        tvCourseRoom = view.findViewById(R.id.tvCourseRoom);
        tvCourseProfessor = view.findViewById(R.id.tvCourseProf);
        btnRemove = view.findViewById(R.id.view_summary_remove_button);
        bottomLine = view.findViewById(R.id.view_summary_bottom_line);
        llExpandedLayout = view.findViewById(R.id.expanded_view_summary_item_layout);
        constraintLayout = view.findViewById(R.id.view_summary_layout);
    }

    public void setCourseCode(String courseCode) {
        tvCourseCode.setText(courseCode);
    }

    public void setCourseDays(String courseDays) {
        tvCourseDays.setText(courseDays);
    }

    public void setCourseTime(String courseTime) {
        tvCourseTime.setText(courseTime);
    }

    public void setCourseRoom(String room) {
        tvCourseRoom.setText(room);
    }

    public void setCourseProfessor(String professor) {
        tvCourseProfessor.setText(professor);
    }

    public void setBackgroundColor(int color) {
        constraintLayout.setBackgroundColor(color);
    }

    public void setButtonVisibility(int visibility) {
        llExpandedLayout.setVisibility(visibility);
    }

    public void setUnderlineVisibility(int visibility) {
        bottomLine.setVisibility(visibility);
    }

    protected void setBtnRemoveListener(View.OnClickListener listener) {
        btnRemove.setOnClickListener(listener);
    }
}
