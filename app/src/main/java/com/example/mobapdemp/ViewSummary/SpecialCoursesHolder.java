package com.example.mobapdemp.ViewSummary;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobapdemp.R;

public class SpecialCoursesHolder extends RecyclerView.ViewHolder {

    private TextView tvCourseCode;
    private TextView tvCourseProf;
    private TextView tvDetails;
    private LinearLayout llButton;
    private LinearLayout llParent;
    private LinearLayout llChild;
    private Button btnRemove;
    private ConstraintLayout constraintLayout;
    private ViewSummary viewSummary;

    public SpecialCoursesHolder(View view, ViewSummary viewSummary) {
        super(view);

        tvCourseCode = view.findViewById(R.id.tvCourseCode);
        tvCourseProf = view.findViewById(R.id.tvCourseProf);
        tvDetails = view.findViewById(R.id.tvDetails);
        llButton = view.findViewById(R.id.expanded_view_summary_item_layout);
        constraintLayout = view.findViewById(R.id.view_summary_layout);
        btnRemove = view.findViewById(R.id.view_summary_remove_button);
        llParent = view.findViewById(R.id.linearLayoutParent);
        llChild = view.findViewById(R.id.linear_layout_child);

        this.viewSummary = viewSummary;
    }

    public void setCourseCode(String courseCode) {
        tvCourseCode.setText(courseCode);
    }

    public void setCourseProf(String courseProf) {
        tvCourseProf.setText(courseProf);
    }

    public void setDetails(String details) {
        tvDetails.setText(details);
    }

    public void setAll(String[] prof, String[] days, String[] time, String[] room, int size) {
        if (llParent.getChildCount() == size * 2) {
            for(int j = 0; j < time.length; j++) {
                TextView tvProf = new TextView(viewSummary);
                tvProf.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView tvDetails = new TextView(viewSummary);
                tvDetails.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                for(int i=0; i<size;i++){
                    Log.d("TIME", tvDetails.getText().toString());

                    if(i == 0) {
                        tvProf.setText(prof[i]);
                        tvDetails.setText(days[i] + " " + time[i] + " " + room[i]);
                    }
                    else{
                        tvProf.setText(tvProf.getText() + "\n" +prof[i]);
                        tvDetails.setText(tvDetails.getText() + "\n" + days[i] + " " + time[i] + " " + room[i]);
                    }
                }
                llParent.addView(tvProf);
                llParent.addView(tvDetails);
            }
        }
    }

    public void setButtonVisibility(int visibility) {
        llButton.setVisibility(visibility);
    }

    public void setBackgroundColor(int color) {
        constraintLayout.setBackgroundColor(color);
    }

    protected void setBtnRemoveListener(View.OnClickListener listener) {
        btnRemove.setOnClickListener(listener);
    }
}
