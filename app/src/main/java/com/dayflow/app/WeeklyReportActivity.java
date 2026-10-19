package com.dayflow.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeeklyReportActivity extends AppCompatActivity {

    private DayFlowData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new DayFlowData(this);

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                30,
                50,
                30,
                30
        );

        // TITLE
        TextView title = new TextView(this);

        title.setText(
                "📊 Weekly Report"
        );

        title.setTextSize(30);

        title.setTypeface(
                null,
                Typeface.BOLD
        );

        title.setGravity(
                Gravity.CENTER
        );

        // WEEK
        TextView week = new TextView(this);

        week.setText(
                "This Week"
        );

        week.setTextSize(22);

        week.setTypeface(
                null,
                Typeface.BOLD
        );

        week.setPadding(
                0,
                40,
                0,
                20
        );

        // REPORT
        TextView report = new TextView(this);

        report.setTextSize(18);

        StringBuilder reportText =
                new StringBuilder();

        Calendar calendar =
                Calendar.getInstance();

       Calendar taskCalendar =
        Calendar.getInstance();

int dayOfWeek =
        taskCalendar.get(Calendar.DAY_OF_WEEK);

int daysFromMonday;

if (dayOfWeek == Calendar.SUNDAY) {

    daysFromMonday = 6;

} else {

    daysFromMonday =
            dayOfWeek - Calendar.MONDAY;
}

calendar.add(
        Calendar.DAY_OF_MONTH,
        -daysFromMonday
);

        int totalPercentage = 0;

        for (int i = 0; i < 7; i++) {

            String date =
                    new SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                    ).format(
                            calendar.getTime()
                    );

            int percentage =
                    data.getCompletionForDate(date);

            String dayName =
                    new SimpleDateFormat(
                            "EEEE",
                            Locale.getDefault()
                    ).format(
                            calendar.getTime()
                    );

           int completed =
        data.getCompletedTasksForDate(date);

int total =
        data.getTotalTasksForDate(date);

int missed =
        total - completed;

reportText
        .append("📅 ")
        .append(dayName)
        .append("     ")
        .append(percentage)
        .append("%\n")
        .append("   ✅ Completed: ")
        .append(completed)
        .append("    ❌ Missed: ")
        .append(missed)
        .append("\n\n");
            totalPercentage += percentage;

            calendar.add(
                    Calendar.DAY_OF_MONTH,
                    1
            );
        }

        report.setText(
                reportText.toString()
        );

// COMPLETED / MISSED TASKS
int weeklyCompleted = 0;
int weeklyMissed = 0;



int taskDayOfWeek =
        taskCalendar.get(Calendar.DAY_OF_WEEK);

int taskDaysFromMonday;

if (taskDayOfWeek == Calendar.SUNDAY) {

    taskDaysFromMonday = 6;

} else {

    taskDaysFromMonday =
            taskDayOfWeek - Calendar.MONDAY;
}

taskCalendar.add(
        Calendar.DAY_OF_MONTH,
        -taskDaysFromMonday
);

for (int i = 0; i < 7; i++) {

    String date =
            new SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
            ).format(
                    taskCalendar.getTime()
            );

    int completed =
            data.getCompletedTasksForDate(date);

    int total =
            data.getTotalTasksForDate(date);

    weeklyCompleted += completed;

    if (total >= completed) {
        weeklyMissed += total - completed;
    }

    taskCalendar.add(
            Calendar.DAY_OF_MONTH,
            1
    );
}

TextView taskStats =
        new TextView(this);

taskStats.setText(
        "✅ Completed Tasks: " +
        weeklyCompleted +
        "\n❌ Missed Tasks: " +
        weeklyMissed
);

taskStats.setTextSize(20);

taskStats.setPadding(
        0,
        20,
        0,
        20
);
        // AVERAGE
        TextView average = new TextView(this);

        int weeklyAverage =
                totalPercentage / 7;

        average.setText(
                "📈 Weekly Average: " +
                weeklyAverage +
                "%"
        );

        average.setTextSize(20);

        average.setTypeface(
                null,
                Typeface.BOLD
        );

        average.setPadding(
                0,
                30,
                0,
                20
        );

        // STREAK
        TextView streak = new TextView(this);

        streak.setText(
                "🔥 Current Streak: " +
                calculateStreak() +
                " days"
        );

        streak.setTextSize(20);

        // BACK
        Button back = new Button(this);

        back.setText(
                "← Back"
        );

        back.setOnClickListener(
                v -> finish()
        );

        layout.addView(title);
        layout.addView(week);
        layout.addView(report);
        layout.addView(taskStats);
        layout.addView(average);
        layout.addView(streak);
        layout.addView(back);

        setContentView(layout);
    }

    private int calculateStreak() {

        int streak = 0;

        Calendar calendar =
                Calendar.getInstance();

        for (int i = 0; i < 7; i++) {

            String date =
                    new SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                    ).format(
                            calendar.getTime()
                    );

            int percentage =
                    data.getCompletionForDate(date);

            if (percentage > 0) {

                streak++;

            } else {

                break;
            }

            calendar.add(
                    Calendar.DAY_OF_MONTH,
                    -1
            );
        }

        return streak;
    }
}