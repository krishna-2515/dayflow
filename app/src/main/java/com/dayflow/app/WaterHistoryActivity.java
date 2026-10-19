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

public class WaterHistoryActivity extends AppCompatActivity {

    private DayFlowData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new DayFlowData(this);

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                30,
                50,
                30,
                30
        );

        TextView title =
                new TextView(this);

        title.setText("💧 Weekly Water History");
        title.setTextSize(28);
        title.setTypeface(
                null,
                Typeface.BOLD
        );
        title.setGravity(
                Gravity.CENTER
        );

        layout.addView(title);

        TextView history =
                new TextView(this);

        history.setTextSize(18);
        history.setPadding(
                0,
                40,
                0,
                20
        );

        Calendar calendar =
        Calendar.getInstance();

int dayOfWeek =
        calendar.get(Calendar.DAY_OF_WEEK);

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

        int totalWater = 0;

        StringBuilder text =
                new StringBuilder();

        for (int i = 0; i < 7; i++) {

            String date =
                    new SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                    ).format(
                            calendar.getTime()
                    );

            String dayName =
                    new SimpleDateFormat(
                            "EEEE",
                            Locale.getDefault()
                    ).format(
                            calendar.getTime()
                    );

            int amount =
                    data.getWaterHistory(date);
                    int goal =
        data.getWaterGoal();

String status;

if (amount >= goal) {
    status = "✅ Goal Complete";
} else {
    status =
            "⏳ " +
            (goal - amount) +
            " ml remaining";
}

          text.append("📅 ")
        .append(dayName)
        .append("\n")
        .append("💧 ")
        .append(amount)
        .append(" / ")
        .append(goal)
        .append(" ml\n")
        .append(status)
        .append("\n\n");
            totalWater += amount;

            calendar.add(
                    Calendar.DAY_OF_MONTH,
                    1
            );
        }

        history.setText(
                text.toString()
        );

        layout.addView(history);

        TextView average =
                new TextView(this);

        int weeklyAverage =
                totalWater / 7;

        average.setText(
                "📊 Weekly Average: "
                        + weeklyAverage
                        + " ml"
        );

        average.setTextSize(20);

        average.setTypeface(
                null,
                Typeface.BOLD
        );

        layout.addView(average);

        Button back =
                new Button(this);

        back.setText("← Back");

        back.setOnClickListener(
                v -> finish()
        );

        layout.addView(back);

        setContentView(layout);
    }
}