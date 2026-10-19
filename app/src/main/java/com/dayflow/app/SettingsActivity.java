package com.dayflow.app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
public class SettingsActivity extends AppCompatActivity {

    private DayFlowData data;
    private TextView selectedGoalText;
    private TextView selectedReminderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new DayFlowData(this).isDarkModeEnabled()) {

    AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
    );

} else {

    AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
    );
}
        super.onCreate(savedInstanceState);

        data = new DayFlowData(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);

        TextView title = new TextView(this);
        title.setText("⚙ Settings");
        title.setTextSize(30);
        title.setTypeface(null, Typeface.BOLD);

        // =========================
        // WATER GOAL
        // =========================

        TextView waterGoal = new TextView(this);
        waterGoal.setText("💧 Water Goal");
        waterGoal.setTextSize(20);
        waterGoal.setTypeface(null, Typeface.BOLD);
        waterGoal.setPadding(0, 40, 0, 10);

        selectedGoalText = new TextView(this);
        selectedGoalText.setText(
                "Selected Goal: " + data.getWaterGoal() + " ml"
        );
        selectedGoalText.setTextSize(18);
        selectedGoalText.setPadding(0, 10, 0, 20);

        Button goal3000 = createGoalButton("3000 ml", 3000);
        Button goal3500 = createGoalButton("3500 ml", 3500);
        Button goal4000 = createGoalButton("4000 ml", 4000);

        // =========================
        // WATER REMINDER
        // =========================

        TextView waterReminder = new TextView(this);
        waterReminder.setText("⏰ Water Reminder");
        waterReminder.setTextSize(20);
        waterReminder.setTypeface(null, Typeface.BOLD);
        waterReminder.setPadding(0, 40, 0, 10);

        selectedReminderText = new TextView(this);
        selectedReminderText.setTextSize(18);
        selectedReminderText.setPadding(0, 10, 0, 20);

        // Load saved reminder
        updateReminderText();

        Button reminder1Hour = new Button(this);
        reminder1Hour.setText("Every 1 Hour");

        Button reminder2Hour = new Button(this);
        reminder2Hour.setText("Every 2 Hours");

        Button reminder3Hour = new Button(this);
        reminder3Hour.setText("Every 3 Hours");

        // =========================
        // CUSTOM REMINDER
        // =========================

        Button customReminder = new Button(this);
        customReminder.setText("Custom Interval");

        customReminder.setOnClickListener(view -> {

            LinearLayout customLayout =
                    new LinearLayout(this);

            customLayout.setOrientation(
                    LinearLayout.VERTICAL
            );

            customLayout.setPadding(
                    20,
                    10,
                    20,
                    10
            );

            EditText hoursInput =
                    new EditText(this);

            hoursInput.setHint("Hours");

            hoursInput.setInputType(
                    InputType.TYPE_CLASS_NUMBER
            );

            EditText minutesInput =
                    new EditText(this);

            minutesInput.setHint("Minutes");

            minutesInput.setInputType(
                    InputType.TYPE_CLASS_NUMBER
            );

            customLayout.addView(hoursInput);
            customLayout.addView(minutesInput);

            new AlertDialog.Builder(this)
                    .setTitle("Custom Water Reminder")
                    .setMessage(
                            "Set your reminder interval"
                    )
                    .setView(customLayout)
                    .setPositiveButton(
                            "Save",
                            (dialog, which) -> {

                                String hoursText =
                                        hoursInput
                                                .getText()
                                                .toString();

                                String minutesText =
                                        minutesInput
                                                .getText()
                                                .toString();

                                int hours = 0;
                                int minutes = 0;

                                if (!hoursText.isEmpty()) {
                                    hours =
                                            Integer.parseInt(
                                                    hoursText
                                            );
                                }

                                if (!minutesText.isEmpty()) {
                                    minutes =
                                            Integer.parseInt(
                                                    minutesText
                                            );
                                }

                                // Invalid interval
                                if (hours == 0
                                        && minutes == 0) {

                                    data.disableWaterReminder();

                                    ReminderScheduler
                                            .cancelWaterReminder(
                                                    this
                                            );

                                    selectedReminderText
                                            .setText(
                                                    "Selected Reminder: Off"
                                            );

                                    return;
                                }

                                // Maximum 59 minutes
                                if (minutes >= 60) {
                                    minutes = 59;
                                }

                                data.saveWaterReminder(
                                        hours,
                                        minutes,
                                        true
                                );

                                ReminderScheduler
                                        .scheduleWaterInterval(
                                                this,
                                                hours,
                                                minutes
                                        );

                                updateReminderText();
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });

        Button reminderOff = new Button(this);
        reminderOff.setText(
                "Turn Off Reminder"
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        reminder1Hour.setOnClickListener(view -> {

            data.saveWaterReminder(
                    1,
                    0,
                    true
            );

            ReminderScheduler
                    .scheduleWaterInterval(
                            this,
                            1,
                            0
                    );

            updateReminderText();
        });

        reminder2Hour.setOnClickListener(view -> {

            data.saveWaterReminder(
                    2,
                    0,
                    true
            );

            ReminderScheduler
                    .scheduleWaterInterval(
                            this,
                            2,
                            0
                    );

            updateReminderText();
        });

        reminder3Hour.setOnClickListener(view -> {

            data.saveWaterReminder(
                    3,
                    0,
                    true
            );

            ReminderScheduler
                    .scheduleWaterInterval(
                            this,
                            3,
                            0
                    );

            updateReminderText();
        });

        reminderOff.setOnClickListener(view -> {

            data.disableWaterReminder();

            ReminderScheduler
                    .cancelWaterReminder(
                            this
                    );

            updateReminderText();
        });

        // =========================
        // BACK BUTTON
        // =========================

        Button back = new Button(this);
        back.setText("← Back");

        back.setOnClickListener(
                view -> finish()
        );

        // =========================
        // ADD VIEWS
        // =========================

        layout.addView(title);

        layout.addView(waterGoal);
        layout.addView(selectedGoalText);
        layout.addView(goal3000);
        layout.addView(goal3500);
        layout.addView(goal4000);

        layout.addView(waterReminder);
        layout.addView(selectedReminderText);
        layout.addView(reminder1Hour);
        layout.addView(reminder2Hour);
        layout.addView(reminder3Hour);
        layout.addView(customReminder);
        layout.addView(reminderOff);
// =========================
// DARK MODE
// =========================

TextView darkModeTitle =
        new TextView(this);

darkModeTitle.setText(
        "🌙 Dark Mode"
);

darkModeTitle.setTextSize(20);

darkModeTitle.setTypeface(
        null,
        android.graphics.Typeface.BOLD
);

darkModeTitle.setPadding(
        0,
        25,
        0,
        10
);

android.widget.Switch darkModeSwitch =
        new android.widget.Switch(this);

darkModeSwitch.setText(
        "Enable Dark Mode"
);

darkModeSwitch.setTextSize(17);

darkModeSwitch.setChecked(
        data.isDarkModeEnabled()
);

darkModeSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> {

            data.setDarkModeEnabled(
                    isChecked
            );

            if (isChecked) {

    AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
    );

} else {

    AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
    );
}
        }
);

layout.addView(darkModeTitle);
layout.addView(darkModeSwitch);
// =========================
// CUSTOM REMINDER SOUND
// =========================

TextView soundTitle =
        new TextView(this);

soundTitle.setText(
        "🔊 Reminder Sound"
);

soundTitle.setTextSize(20);

soundTitle.setTypeface(
        null,
        android.graphics.Typeface.BOLD
);

soundTitle.setPadding(
        0,
        25,
        0,
        10
);

Button chooseSound =
        new Button(this);

chooseSound.setText(
        "🎵 Choose Custom Sound"
);

chooseSound.setOnClickListener(
        view -> {

            android.content.Intent intent =
                    new android.content.Intent(
                            android.content.Intent.ACTION_OPEN_DOCUMENT
                    );

            intent.addCategory(
                    android.content.Intent.CATEGORY_OPENABLE
            );

            intent.setType("audio/*");

            startActivityForResult(
                    intent,
                    5000
            );
        }
);

layout.addView(soundTitle);
layout.addView(chooseSound);
        layout.addView(back);

        setContentView(layout);
    }

    // =========================
    // UPDATE REMINDER TEXT
    // =========================

    private void updateReminderText() {

        if (!data.isWaterReminderEnabled()) {

            selectedReminderText.setText(
                    "Selected Reminder: Off"
            );

            return;
        }

        int hours =
                data.getWaterReminderHours();

        int minutes =
                data.getWaterReminderMinutes();

        if (hours == 0 && minutes == 0) {

            selectedReminderText.setText(
                    "Selected Reminder: Off"
            );

            return;
        }

        String reminderText =
                "Selected Reminder: Every ";

        if (hours > 0) {

            reminderText +=
                    hours + " Hour";

            if (hours > 1) {
                reminderText += "s";
            }
        }

        if (hours > 0 && minutes > 0) {
            reminderText += " ";
        }

        if (minutes > 0) {

            reminderText +=
                    minutes + " Minute";

            if (minutes > 1) {
                reminderText += "s";
            }
        }

        selectedReminderText.setText(
                reminderText
        );
    }

    // =========================
    // WATER GOAL BUTTON
    // =========================

    private Button createGoalButton(
            String text,
            int goal
    ) {

        Button button =
                new Button(this);

        button.setText(text);
        button.setTextSize(16);
        button.setTextColor(Color.BLACK);

        button.setOnClickListener(view -> {

            data.saveWaterGoal(goal);

            selectedGoalText.setText(
                    "Selected Goal: "
                            + goal
                            + " ml"
            );
        });

        return button;
    }
    @Override
protected void onActivityResult(
        int requestCode,
        int resultCode,
        android.content.Intent dataIntent
) {
    super.onActivityResult(
            requestCode,
            resultCode,
            dataIntent
    );

    if (requestCode == 5000
            && resultCode == RESULT_OK
            && dataIntent != null
            && dataIntent.getData() != null) {

        android.net.Uri soundUri =
                dataIntent.getData();

        data.saveCustomSoundUri(
                soundUri.toString()
        );

        try {

            getContentResolver()
                    .takePersistableUriPermission(
                            soundUri,
                            android.content.Intent
                                    .FLAG_GRANT_READ_URI_PERMISSION
                    );

        } catch (Exception e) {
            // Permission may not be persistable
        }
    }
}
}