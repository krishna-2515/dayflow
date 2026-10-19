package com.dayflow.app;

import android.graphics.Typeface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int waterAmount = 0;
    int waterGoal = 3000;

    DayFlowData data;

    TextView waterInfo;
    ProgressBar waterProgress;
    TextView dailyProgress;
    TextView dailyQuote;
String[] dailyQuotes = {
        "Aaj ka chhota effort, kal ka bada result banega. 💪",
        "Consistency rakho, results apne aap aayenge. 🔥",
        "Har din ek naya chance hai khud ko better banane ka. 🌱",
        "Slow progress bhi progress hoti hai. Bas rukna mat. 🚀",
        "Apne goal par focus karo, distractions par nahi. 🎯",
        "Aaj mehnat kar lo, kal proud feel karoge. 💯",
        "Discipline motivation se zyada powerful hota hai. 💪",
        "Ek step roz, goal ke aur close. 👣",
        "Mushkil waqt temporary hai, tumhari mehnat permanent result degi. 🔥",
        "Khud par believe karo, tum soch se zyada kar sakte ho. ✨",
        "Perfect hone ka wait mat karo, start karo. 🚀",
        "Aaj ka kaam kal par mat chhodo. ⏰",
        "Chhoti chhoti wins ko celebrate karo. 🏆",
        "Focus + Consistency = Success. 🎯",
        "Tumhara future tumhari daily habits se banta hai. 🌟",
        "Aaj ka effort hi kal ki success banega. 🔥",
        "Thoda thoda karke bhi bada goal achieve hota hai. 🚀",
        "Apni speed nahi, apni consistency dekho. 💪",
        "Jo start karta hai, wahi finish karta hai. 🏆",
        "Excuses kam, action zyada. 🎯",
        "Aaj khud ko kal se better banao. 🌱",
        "Mehnat kabhi waste nahi jaati. 💯",
        "Goal bada hai to effort bhi bada hona chahiye. 🔥",
        "Har setback ek lesson hai, give up karne ka reason nahi. 💪",
        "Apna focus strong rakho, result zaroor milega. 🎯",
        "Daily improvement hi real progress hai. 📈",
        "Sapne tabhi complete hote hain jab unke liye kaam karte ho. ✨",
        "Aaj ka discipline, kal ki freedom hai. 🚀",
        "Khud se compete karo, har din better bano. 🏆",
        "Believe karo, start karo aur consistent raho. 💪"
};
        private String getDailyQuote() {
    java.util.Calendar calendar = java.util.Calendar.getInstance();

    int dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR);

    int index = (dayOfYear - 1) % dailyQuotes.length;

    return dailyQuotes[index];
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new DayFlowData(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
    if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {

        requestPermissions(
                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                1001
        );
    }
}
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

    android.app.NotificationChannel channel =
            new android.app.NotificationChannel(
                    "DAYFLOW_REMINDER",
                    "DayFlow Reminders",
                    android.app.NotificationManager.IMPORTANCE_HIGH
            );

    channel.setDescription("Water and study reminders");

    android.app.NotificationManager manager =
            getSystemService(android.app.NotificationManager.class);

    manager.createNotificationChannel(channel);
}
        data.resetTasksForNewDay();
        

        waterAmount = data.getWater();
        waterGoal = data.getWaterGoal();

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(30, 50, 30, 30);

       TextView title = new TextView(this);

title.setText("DayFlow");
title.setTextSize(34);
title.setTypeface(null, Typeface.BOLD);

title.setGravity(
        android.view.Gravity.CENTER
);

title.setPadding(
        0,
        10,
        0,
        10
);;

        TextView greeting = new TextView(this);
        java.util.Calendar currentTime =
        java.util.Calendar.getInstance();

int hour =
        currentTime.get(
                java.util.Calendar.HOUR_OF_DAY
        );

if (hour < 12) {

    greeting.setText("Good Morning 👋");

} else if (hour < 17) {

    greeting.setText("Good Afternoon ☀️");

} else if (hour < 21) {

    greeting.setText("Good Evening 🌆");

} else {

    greeting.setText("Good Night 🌙");
}
        greeting.setTextSize(22);
        greeting.setPadding(0, 25, 0, 20);

        TextView schedule = new TextView(this);
        schedule.setText("📅 Today's Schedule");
        schedule.setTextSize(20);
        schedule.setTypeface(null, Typeface.BOLD);

      Button scheduleButton = new Button(this);
scheduleButton.setText("📅 Daily Schedule");

if (data.isDarkModeEnabled()) {

    scheduleButton.setTextColor(Color.WHITE);
    scheduleButton.setBackgroundColor(
            Color.rgb(55, 55, 55)
    );

} else {

    scheduleButton.setTextColor(Color.DKGRAY);
    scheduleButton.setBackgroundColor(
            Color.rgb(235, 235, 235)
    );
}

        scheduleButton.setOnClickListener(v -> {

            android.content.Intent intent =
                    new android.content.Intent(
                            MainActivity.this,
                            ScheduleActivity.class
                    );

            startActivity(intent);
        });

        TextView scheduleInfo = new TextView(this);
        scheduleInfo.setText("No activities added yet");
        scheduleInfo.setTextSize(17);
        scheduleInfo.setPadding(0, 10, 0, 25);

        EditText taskInput = new EditText(this);
        taskInput.setHint("Enter task name");
        taskInput.setTextSize(17);

        Button addTask = new Button(this);
addTask.setText("➕ Add Task");

if (data.isDarkModeEnabled()) {

    addTask.setTextColor(Color.WHITE);
    addTask.setBackgroundColor(
            Color.rgb(55, 55, 55)
    );

} else {

    addTask.setTextColor(Color.DKGRAY);
    addTask.setBackgroundColor(
            Color.rgb(235, 235, 235)
    );
}

        TextView taskInfo = new TextView(this);
        taskInfo.setText("No tasks added yet");
        taskInfo.setTextSize(17);
        taskInfo.setPadding(0, 10, 0, 20);

        addTask.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String taskName =
                                taskInput.getText()
                                        .toString()
                                        .trim();

                        if (!taskName.isEmpty()) {

                            taskInfo.setText(
                                    "My Tasks\n\n✅ " +
                                    taskName
                            );

                            taskInput.setText("");

                        } else {

                            taskInfo.setText(
                                    "Please enter a task name"
                            );
                        }
                    }
                }
        );
// =========================
// DAILY PROGRESS
// =========================
LinearLayout progressCard =
        new LinearLayout(this);

progressCard.setOrientation(
        LinearLayout.VERTICAL
);

progressCard.setPadding(
        30,
        30,
        30,
        30
);

android.graphics.drawable.GradientDrawable progressCardBackground =
        new android.graphics.drawable.GradientDrawable();

if (data.isDarkModeEnabled()) {

    progressCardBackground.setColor(
            android.graphics.Color.rgb(
                    45,
                    45,
                    45
            )
    );

} else {

    progressCardBackground.setColor(
            android.graphics.Color.rgb(
                    225,
                    230,
                    235
            )
    );
}

progressCardBackground.setCornerRadius(50);

progressCardBackground.setStroke(
        2,
        android.graphics.Color.rgb(180, 188, 195)
);

progressCard.setBackground(
        progressCardBackground
);

progressCard.setElevation(8);

progressCard.setClipToOutline(true);    
dailyProgress =
        new TextView(this);
int completedTasks =
        data.getCompletedTasks();

int totalTasks =
        data.getTotalTasks();

int progressPercentage =
        data.getDailyCompletionPercentage();

dailyProgress.setText(
        "📊 Daily Progress\n\n" +
        "✅ Completed: " +
        completedTasks +
        "\n" +
        "📝 Total Tasks: " +
        totalTasks +
        "\n" +
        "📈 Progress: " +
        progressPercentage +
        "%"
);

dailyProgress.setTextSize(19);
if (data.isDarkModeEnabled()) {

    dailyProgress.setTextColor(
            android.graphics.Color.WHITE
    );

} else {

    dailyProgress.setTextColor(
            android.graphics.Color.DKGRAY
    );
}
dailyProgress.setPadding(
        0,
        20,
        0,
        25
);
        // =========================
        // WATER TRACKING
        // =========================

        TextView water = new TextView(this);
        water.setText("💧 Water Tracking");
        water.setTextSize(20);
        water.setTypeface(null, Typeface.BOLD);

        waterInfo = new TextView(this);
        waterInfo.setTextSize(17);
        waterInfo.setPadding(0, 10, 0, 20);

        waterProgress =
                new ProgressBar(
                        this,
                        null,
                        android.R.attr.progressBarStyleHorizontal
                );
waterProgress.setPadding(
        0,
        10,
        0,
        20
);
        updateWaterDisplay();

        Button addWater = new Button(this);
addWater.setText("+ Add Water");

if (data.isDarkModeEnabled()) {

    addWater.setTextColor(Color.WHITE);
    addWater.setBackgroundColor(
            Color.rgb(55, 55, 55)
    );

} else {

    addWater.setTextColor(Color.DKGRAY);
    addWater.setBackgroundColor(
            Color.rgb(235, 235, 235)
    );
}

        addWater.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        waterAmount += 500;

                        if (waterAmount > waterGoal) {
                            waterAmount = waterGoal;
                        }

                        data.saveWater(waterAmount);
                        data.saveWaterHistory();
                        updateWaterDisplay();
                    }
                }
        );

        Button add250 = new Button(this);
add250.setText("+250 ml");

if (data.isDarkModeEnabled()) {

    add250.setTextColor(Color.WHITE);
    add250.setBackgroundColor(
            Color.rgb(55, 55, 55)
    );

} else {

    add250.setTextColor(Color.DKGRAY);
    add250.setBackgroundColor(
            Color.rgb(235, 235, 235)
    );
}

        add250.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        waterAmount += 250;

                        if (waterAmount > waterGoal) {
                            waterAmount = waterGoal;
                        }

                        data.saveWater(waterAmount);
                        data.saveWaterHistory();

                        updateWaterDisplay();
                    }
                }
        );

Button add500 = new Button(this);
add500.setText("+500 ml");

if (data.isDarkModeEnabled()) {
    add500.setTextColor(Color.WHITE);
    add500.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    add500.setTextColor(Color.DKGRAY);
    add500.setBackgroundColor(Color.rgb(235, 235, 235));
}

add500.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                waterAmount += 500;

                if (waterAmount > waterGoal) {
                    waterAmount = waterGoal;
                }

                data.saveWater(waterAmount);
                data.saveWaterHistory();

                updateWaterDisplay();
            }
        }
);


Button add750 = new Button(this);
add750.setText("+750 ml");

if (data.isDarkModeEnabled()) {
    add750.setTextColor(Color.WHITE);
    add750.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    add750.setTextColor(Color.DKGRAY);
    add750.setBackgroundColor(Color.rgb(235, 235, 235));
}

add750.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                waterAmount += 750;

                if (waterAmount > waterGoal) {
                    waterAmount = waterGoal;
                }

                data.saveWater(waterAmount);
                data.saveWaterHistory();

                updateWaterDisplay();
            }
        }
);


Button tasks = new Button(this);
tasks.setText("✅ My Tasks");

if (data.isDarkModeEnabled()) {
    tasks.setTextColor(Color.WHITE);
    tasks.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    tasks.setTextColor(Color.DKGRAY);
    tasks.setBackgroundColor(Color.rgb(235, 235, 235));
}

tasks.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                taskInfo.setText(
                        "My Tasks\n\n" +
                        "No tasks added yet"
                );
            }
        }
);


Button weeklyReport = new Button(this);
weeklyReport.setText("📊 Weekly Report");

if (data.isDarkModeEnabled()) {
    weeklyReport.setTextColor(Color.WHITE);
    weeklyReport.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    weeklyReport.setTextColor(Color.DKGRAY);
    weeklyReport.setBackgroundColor(Color.rgb(235, 235, 235));
}

weeklyReport.setOnClickListener(v -> {

    android.content.Intent intent =
            new android.content.Intent(
                    MainActivity.this,
                    WeeklyReportActivity.class
            );

    startActivity(intent);
});


Button waterHistory = new Button(this);
waterHistory.setText("💧 Water History");

if (data.isDarkModeEnabled()) {
    waterHistory.setTextColor(Color.WHITE);
    waterHistory.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    waterHistory.setTextColor(Color.DKGRAY);
    waterHistory.setBackgroundColor(Color.rgb(235, 235, 235));
}

waterHistory.setOnClickListener(v -> {

    android.content.Intent intent =
            new android.content.Intent(
                    MainActivity.this,
                    WaterHistoryActivity.class
            );

    startActivity(intent);
});


Button settings = new Button(this);
settings.setText("⚙ Settings");

if (data.isDarkModeEnabled()) {
    settings.setTextColor(Color.WHITE);
    settings.setBackgroundColor(Color.rgb(55, 55, 55));
} else {
    settings.setTextColor(Color.DKGRAY);
    settings.setBackgroundColor(Color.rgb(235, 235, 235));
}

settings.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                android.content.Intent intent =
                        new android.content.Intent(
                                MainActivity.this,
                                SettingsActivity.class
                        );

                startActivity(intent);
            }
        }
);
// =========================
// STREAK CARD
// =========================

TextView streakInfo =
        new TextView(this);

int currentStreak =
        data.getCurrentStreak();

streakInfo.setText(
        "🔥 Current Streak\n\n" +
        "🔥 " +
        currentStreak +
        " days"
);

streakInfo.setTextSize(19);

streakInfo.setPadding(
        0,
        10,
        0,
        10
);

LinearLayout streakCard =
        new LinearLayout(this);

streakCard.setOrientation(
        LinearLayout.VERTICAL
);

streakCard.setPadding(
        30,
        30,
        30,
        30
);

android.graphics.drawable.GradientDrawable
        streakCardBackground =
        new android.graphics.drawable.GradientDrawable();

if (data.isDarkModeEnabled()) {

    streakCardBackground.setColor(
            android.graphics.Color.rgb(
                    45,
                    45,
                    45
            )
    );

    streakCardBackground.setStroke(
            2,
            android.graphics.Color.rgb(
                    120,
                    120,
                    120
            )
    );

    streakInfo.setTextColor(
            android.graphics.Color.WHITE
    );

} else {

    streakCardBackground.setColor(
            android.graphics.Color.rgb(
                    225,
                    230,
                    235
            )
    );

    streakCardBackground.setStroke(
            2,
            android.graphics.Color.rgb(
                    180,
                    188,
                    195
            )
    );

    streakInfo.setTextColor(
            android.graphics.Color.DKGRAY
    );
}

streakCardBackground.setCornerRadius(50);

streakCard.setBackground(
        streakCardBackground
);

streakCard.setElevation(8);

streakCard.setClipToOutline(true);

streakCard.addView(streakInfo);

LinearLayout.LayoutParams
        streakCardParams =
        new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

streakCardParams.setMargins(
        0,
        20,
        0,
        30
);

mainLayout.addView(
        streakCard,
        streakCardParams
);
// =========================
// CALENDAR VIEW
// =========================

Button calendarButton =
        new Button(this);

calendarButton.setText(
        "📅 Calendar View"
);

if (data.isDarkModeEnabled()) {

    calendarButton.setTextColor(Color.WHITE);
    calendarButton.setBackgroundColor(
            Color.rgb(55, 55, 55)
    );

} else {

    calendarButton.setTextColor(Color.DKGRAY);
    calendarButton.setBackgroundColor(
            Color.rgb(235, 235, 235)
    );
}

calendarButton.setOnClickListener(
        v -> {

            android.app.DatePickerDialog
                    datePickerDialog =
                    new android.app.DatePickerDialog(
                            MainActivity.this,
                            (view, year, month, dayOfMonth) -> {

                                String selectedDate =
                                        String.format(
                                                java.util.Locale.getDefault(),
                                                "%04d-%02d-%02d",
                                                year,
                                                month + 1,
                                                dayOfMonth
                                        );

                                int percentage =
                                        data.getCompletionForDate(
                                                selectedDate
                                        );

                                int completed =
                                        data.getCompletedTasksForDate(
                                                selectedDate
                                        );

                                int total =
                                        data.getTotalTasksForDate(
                                                selectedDate
                                        );

                                int missed =
                                        total - completed;

                                if (missed < 0) {
                                    missed = 0;
                                }

                                new android.app.AlertDialog.Builder(
                                        MainActivity.this
                                )
                                        .setTitle(
                                                "📅 " + selectedDate
                                        )
                                        .setMessage(
                                                "📊 Completion: "
                                                        + percentage
                                                        + "%\n\n"
                                                        + "✅ Completed: "
                                                        + completed
                                                        + "\n"
                                                        + "❌ Missed: "
                                                        + missed
                                                        + "\n"
                                                        + "📝 Total Tasks: "
                                                        + total
                                        )
                                        .setPositiveButton(
                                                "OK",
                                                null
                                        )
                                        .show();
                            },
                            java.util.Calendar.getInstance()
                                    .get(
                                            java.util.Calendar.YEAR
                                    ),
                            java.util.Calendar.getInstance()
                                    .get(
                                            java.util.Calendar.MONTH
                                    ),
                            java.util.Calendar.getInstance()
                                    .get(
                                            java.util.Calendar.DAY_OF_MONTH
                                    )
                    );

            datePickerDialog.show();
        }
);

mainLayout.addView(
        calendarButton
);
        // =========================
        // ADD VIEWS
        // =========================

        mainLayout.addView(title);
        mainLayout.addView(greeting);

        mainLayout.addView(schedule);
        mainLayout.addView(scheduleButton);
        mainLayout.addView(scheduleInfo);

        mainLayout.addView(taskInput);
        mainLayout.addView(addTask);
        mainLayout.addView(taskInfo);
        progressCard.addView(dailyProgress);

LinearLayout.LayoutParams progressCardParams =
        new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

progressCardParams.setMargins(
        0,
        20,
        0,
        30
);

mainLayout.addView(
        progressCard,
        progressCardParams
);
// =========================
// DAILY MOTIVATION
// =========================

LinearLayout quoteCard = new LinearLayout(this);

quoteCard.setOrientation(
        LinearLayout.VERTICAL
);

quoteCard.setPadding(
        30,
        25,
        30,
        25
);

TextView quoteTitle = new TextView(this);

quoteTitle.setText(
        "🌟 Daily Motivation"
);

quoteTitle.setTextSize(18);

quoteTitle.setTypeface(
        null,
        Typeface.BOLD
);

dailyQuote = new TextView(this);

dailyQuote.setText(
        getDailyQuote()
);

dailyQuote.setTextSize(16);

dailyQuote.setPadding(
        0,
        15,
        0,
        0
);

if (data.isDarkModeEnabled()) {

    dailyQuote.setTextColor(Color.WHITE);
    quoteTitle.setTextColor(Color.WHITE);

} else {

    dailyQuote.setTextColor(Color.DKGRAY);
    quoteTitle.setTextColor(Color.DKGRAY);
}

quoteCard.addView(quoteTitle);
quoteCard.addView(dailyQuote);

mainLayout.addView(quoteCard);
// =========================
// WATER CARD
// =========================

LinearLayout waterCard =
        new LinearLayout(this);

waterCard.setOrientation(
        LinearLayout.VERTICAL
);

waterCard.setPadding(
        30,
        30,
        30,
        30
);

android.graphics.drawable.GradientDrawable waterCardBackground =
        new android.graphics.drawable.GradientDrawable();

if (data.isDarkModeEnabled()) {

    waterCardBackground.setColor(
            android.graphics.Color.rgb(
                    45,
                    45,
                    45
            )
    );

    waterCardBackground.setStroke(
            2,
            android.graphics.Color.rgb(
                    120,
                    120,
                    120
            )
    );

    water.setTextColor(
            android.graphics.Color.WHITE
    );

    waterInfo.setTextColor(
            android.graphics.Color.WHITE
    );

} else {

    waterCardBackground.setColor(
            android.graphics.Color.rgb(
                    225,
                    230,
                    235
            )
    );

    waterCardBackground.setStroke(
            2,
            android.graphics.Color.rgb(
                    180,
                    188,
                    195
            )
    );

    water.setTextColor(
            android.graphics.Color.DKGRAY
    );

    waterInfo.setTextColor(
            android.graphics.Color.DKGRAY
    );
}

waterCardBackground.setCornerRadius(32);

waterCard.setBackground(
        waterCardBackground
);

waterCard.setElevation(8);

waterCard.setClipToOutline(true);

waterCard.addView(water);
waterCard.addView(waterInfo);
waterCard.addView(waterProgress);

waterCard.addView(addWater);
waterCard.addView(add250);
waterCard.addView(add500);
waterCard.addView(add750);

LinearLayout.LayoutParams waterCardParams =
        new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

waterCardParams.setMargins(
        0,
        20,
        0,
        30
);

mainLayout.addView(
        waterCard,
        waterCardParams
);
        mainLayout.addView(tasks);
        mainLayout.addView(weeklyReport);
        mainLayout.addView(waterHistory);
mainLayout.addView(settings);

android.widget.ScrollView scrollView =
        new android.widget.ScrollView(this);

scrollView.addView(mainLayout);

setContentView(scrollView);
    }

    // =========================
    // UPDATE WATER DISPLAY
    // =========================

  private void updateWaterDisplay() {

    if (waterInfo != null) {

        int percentage = 0;

        if (waterGoal > 0) {

            percentage =
                    (waterAmount * 100)
                    / waterGoal;
        }

        int remaining =
        waterGoal - waterAmount;

if (remaining < 0) {
    remaining = 0;
}

waterInfo.setText(
        "💧 " +
        waterAmount +
        " ml / " +
        waterGoal +
        " ml\n" +
        "📊 Progress: " +
        percentage +
        "%\n" +
        "🥤 Remaining: " +
        remaining +
        " ml"
);
    }

    if (waterProgress != null) {

        waterProgress.setMax(waterGoal);
        waterProgress.setProgress(waterAmount);
    }
}

    // =========================
    // REFRESH AFTER SETTINGS
    // =========================
@Override
protected void onResume() {

    super.onResume();

    if (data != null) {

        waterGoal = data.getWaterGoal();

        if (waterAmount > waterGoal) {

            waterAmount = waterGoal;

            data.saveWater(waterAmount);
        }

        updateWaterDisplay();
        // =========================
// REFRESH DAILY PROGRESS
// =========================

int completedTasks =
        data.getCompletedTasks();

int totalTasks =
        data.getTotalTasks();

int progressPercentage =
        data.getDailyCompletionPercentage();

dailyProgress.setText(
        "📊 Daily Progress\n\n" +
        "✅ Completed: " +
        completedTasks +
        "\n" +
        "📝 Total Tasks: " +
        totalTasks +
        "\n" +
        "📈 Progress: " +
        progressPercentage +
        "%"
);

dailyProgress.setTextSize(19);

if (data.isDarkModeEnabled()) {

    dailyProgress.setTextColor(
            android.graphics.Color.WHITE
    );

} else {

    dailyProgress.setTextColor(
            android.graphics.Color.DKGRAY
    );
}

dailyProgress.setPadding(
        0,
        20,
        0,
        25
);



        // =========================
        // APPLY DARK MODE
        // =========================

        if (data.isDarkModeEnabled()) {

            getWindow()
                    .getDecorView()
                    .setBackgroundColor(
                            android.graphics.Color.rgb(
                                    25,
                                    25,
                                    25
                            )
                    );

        } else {

            getWindow()
                    .getDecorView()
                    .setBackgroundColor(
                            android.graphics.Color.WHITE
                    );
        }
    }
}
}
