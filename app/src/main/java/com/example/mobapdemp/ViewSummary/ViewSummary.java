package com.example.mobapdemp.ViewSummary;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.mobapdemp.Database.Database;
import com.example.mobapdemp.R;
import com.example.mobapdemp.Utility;

public class ViewSummary extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewSummaryAdapter viewSummaryAdapter;
    private SpecialCoursesAdapter specialCoursesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvTermLabel;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_summary);

        tvTermLabel = findViewById(R.id.tvTermLabel);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        viewSummaryAdapter = new ViewSummaryAdapter(this);
        specialCoursesAdapter = new SpecialCoursesAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewSummaryAdapter);

        /* Add test data for schedule */
        Utility.addDummySchedule(Database.getInstance(this));
        getAllCourses(1, 2018);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) { /* If regular courses tab */
                    recyclerView.setAdapter(viewSummaryAdapter);
                } else {
                    recyclerView.setAdapter(specialCoursesAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Get all courses within a specified term and year, and add them to the RecyclerView
     * @param term - Term (1; 2; 3)
     * @param yearStart - Year start (e.g. if AY 2018-2019, then yearStart = 2018)
     */
    private void getAllCourses(int term, int yearStart) {

        String chosenTerm = "";
        switch(term) {
            case 1:
                chosenTerm = "First ";
                break;
            case 2:
                chosenTerm = "Second ";
                break;
            case 3:
                chosenTerm = "Third ";
                break;
        }

        tvTermLabel.setText(chosenTerm + "Trimester, AY " + yearStart + "-" + (yearStart + 1));

        String[] courses = Utility.getCoursesInSchedule(Database.getInstance(this), chosenTerm +
                "Trimester, AY " + yearStart + "-" + (yearStart + 1));

        /* Get strings from courses */
        for(int i = 0; i < courses.length; i++) {
            String[] courseInfo = Utility.getCoursesInfo(Database.getInstance(this),
                    courses[i]);

            /* Add data to view summary model */
            if(Integer.parseInt(courseInfo[0]) == -1) {
                viewSummaryAdapter.addCourse(courseInfo);
            } else {
                specialCoursesAdapter.addCourse(courseInfo);
            }
        }
    }

    protected void scrollToItem(int position) {
        //int offset = position - layoutManager
    }
}
