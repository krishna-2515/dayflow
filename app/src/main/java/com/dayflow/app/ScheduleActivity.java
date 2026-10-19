package com.dayflow.app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    EditText taskInput;
    TextView selectedTime;
    TextView progressText;
    LinearLayout taskList;

    String taskTime = "";
    boolean taskImportant = false;

    boolean isEditing = false;
    int editingIndex = -1;

    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<Boolean> taskCompleted = new ArrayList<>();

    int completedTasks = 0;
    Button puzzleButton;

    DayFlowData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new DayFlowData(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);

        TextView title = new TextView(this);
        title.setText("📅 Daily Schedule");
        title.setTextSize(30);
        title.setTypeface(null, Typeface.BOLD);

        progressText = new TextView(this);
        progressText.setText("📊 Daily Completion: 0%");
        progressText.setTextSize(20);
        progressText.setTypeface(null, Typeface.BOLD);
        progressText.setPadding(0, 20, 0, 20);

        taskInput = new EditText(this);
        taskInput.setHint("Enter task name");
        taskInput.setTextSize(18);

        selectedTime = new TextView(this);
        selectedTime.setText("⏰ No time selected");
        selectedTime.setTextSize(18);
        selectedTime.setPadding(0, 20, 0, 20);

        Button timeButton = new Button(this);
        timeButton.setText("🕐 Select Time");
        Button importantButton = new Button(this);
importantButton.setText("⭐ Important: OFF");

importantButton.setOnClickListener(v -> {

    taskImportant = !taskImportant;

    if (taskImportant) {
        importantButton.setText("⭐ Important: ON");
    } else {
        importantButton.setText("⭐ Important: OFF");
    }
});

        timeButton.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(
                    ScheduleActivity.this,
                    (view, selectedHour, selectedMinute) -> {

                        taskTime = String.format(
                                "%02d:%02d",
                                selectedHour,
                                selectedMinute
                        );

                        selectedTime.setText(
                                "⏰ Time: " + taskTime
                        );
                    },
                    hour,
                    minute,
                    true
            );

            dialog.show();
        });

        Button addTask = new Button(this);
        addTask.setText("➕ Add to Schedule");

        TextView listHeading = new TextView(this);
        listHeading.setText("Today's Tasks");
        listHeading.setTextSize(22);
        listHeading.setTypeface(null, Typeface.BOLD);
        listHeading.setPadding(0, 30, 0, 15);

        taskList = new LinearLayout(this);
        taskList.setOrientation(LinearLayout.VERTICAL);

        addTask.setOnClickListener(v -> {

            String taskName =
                    taskInput.getText().toString().trim();

            if (taskName.isEmpty()) {
                taskInput.setError("Enter task name");
                return;
            }

            if (taskTime.isEmpty()) {
                selectedTime.setText(
                        "⚠ Please select a time"
                );
                return;
            }

            // EDIT EXISTING TASK
            if (isEditing) {

                if (editingIndex < 0 ||
                        editingIndex >= tasks.size()) {

                    isEditing = false;
                    editingIndex = -1;
                    return;
                }

                tasks.set(
                        editingIndex,
                        taskName
                );
                data.saveTaskImportant(
        editingIndex,
        taskImportant
);

                times.set(
                        editingIndex,
                        taskTime
                );

                data.saveTaskName(
                        editingIndex,
                        taskName
                );

                data.saveTaskTime(
                        editingIndex,
                        taskTime
                );
                if (data.getTaskImportant(editingIndex)) {

    String[] timeParts =
            taskTime.split(":");

    int reminderHour =
            Integer.parseInt(timeParts[0]);

    int reminderMinute =
            Integer.parseInt(timeParts[1]);

  ReminderScheduler.scheduleTaskReminder(
        this,
        editingIndex,
        reminderHour,
        reminderMinute,
        taskName
);
}
                taskList.removeAllViews();

                completedTasks = 0;

                for (int j = 0;
                     j < taskCompleted.size();
                     j++) {

                    if (taskCompleted.get(j)) {
                        completedTasks++;
                    }

                    addTaskToScreen(
        tasks.get(j),
        times.get(j),
        j + 1,
        taskCompleted.get(j),
        data.getTaskImportant(j)
);
                     }
                isEditing = false;
                editingIndex = -1;

                saveTaskData();
                updateProgress();

                taskInput.setText("");

                taskTime = "";

                selectedTime.setText(
                        "⏰ No time selected"
                );

                return;
            }

            // ADD NEW TASK

            tasks.add(taskName);
            times.add(taskTime);
            taskCompleted.add(false);
            data.saveTaskImportant(
        tasks.size() - 1,
        taskImportant
);
if (taskImportant) {

    String[] timeParts =
            taskTime.split(":");

    int reminderHour =
            Integer.parseInt(timeParts[0]);

    int reminderMinute =
            Integer.parseInt(timeParts[1]);

   ReminderScheduler.scheduleTaskReminder(
        this,
        tasks.size() - 1,
        reminderHour,
        reminderMinute,
        taskName
);

}
            int taskNumber = tasks.size();

            addTaskToScreen(
        taskName,
        taskTime,
        taskNumber,
        false,
        taskImportant
);

            int index = tasks.size() - 1;

            data.saveTaskName(
                    index,
                    taskName
            );

            data.saveTaskTime(
                    index,
                    taskTime
            );

            data.saveTaskCompleted(
                    index,
                    false
            );

            data.saveTodayCompletion();

            saveTaskData();
            updateProgress();

            taskInput.setText("");

            taskTime = "";

            selectedTime.setText(
                    "⏰ No time selected"
            );
        });

        Button back = new Button(this);
        back.setText("← Back");

        back.setOnClickListener(v -> finish());

        layout.addView(title);
        layout.addView(progressText);
        layout.addView(taskInput);
        layout.addView(selectedTime);
        layout.addView(timeButton);
        layout.addView(importantButton);
        layout.addView(addTask);
        layout.addView(listHeading);
        layout.addView(taskList);
        layout.addView(back);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.addView(layout);

        setContentView(scrollView);

        loadSavedTasks();
    }

    private void addTaskToScreen(
        String taskName,
        String time,
        int number,
        boolean alreadyCompleted,
        boolean important
) {

        LinearLayout taskCard =
                new LinearLayout(this);

        taskCard.setOrientation(
                LinearLayout.VERTICAL
        );

        taskCard.setPadding(
                15,
                15,
                15,
                15
        );

        TextView taskText =
                new TextView(this);

        Button completeButton =
                new Button(this);

        if (alreadyCompleted) {

            taskText.setText(
                    "✅ " +
                    taskName +
                    "\n🕐 " +
                    time +
                    "\n\nCompleted"
            );

            completeButton.setText(
                    "Completed ✓"
            );

            completeButton.setEnabled(true);

        } else {

            taskText.setText(
                    number +
                    ". " +
                    taskName +
                    "\n🕐 " +
                    time
            );

            completeButton.setText(
                    "✅ Complete"
            );
        }

        taskText.setTextSize(18);
        if (important) {
    taskText.setText(
            "⭐ IMPORTANT\n" +
            taskText.getText().toString()
    );
}

        completeButton.setOnClickListener(v -> {

        int index = tasks.indexOf(taskName);

            if (index < 0) {
                return;
            }

            if (taskCompleted.get(index)) {

                // UNCOMPLETE TASK

                taskCompleted.set(
                        index,
                        false
                );

                if (completedTasks > 0) {
                    completedTasks--;
                }

                data.saveTaskCompleted(
                        index,
                        false
                );

                data.saveTodayCompletion();

                taskText.setText(
                        number +
                        ". " +
                        taskName +
                        "\n🕐 " +
                        time
                );

                completeButton.setText(
                        "✅ Complete"
                );

            } else {

                // COMPLETE TASK

                taskCompleted.set(
                        index,
                        true
                );

                completedTasks++;

                data.saveTaskCompleted(
                        index,
                        true
                );

                data.saveTodayCompletion();

                taskText.setText(
                        "✅ " +
                        taskName +
                        "\n🕐 " +
                        time +
                        "\n\nCompleted"
                );

                completeButton.setText(
                        "Completed ✓"
                );
            }

            completeButton.setEnabled(true);

            saveTaskData();

            updateProgress();
        });

        taskCard.addView(taskText);
        taskCard.addView(completeButton);

        // EDIT BUTTON

        Button editButton =
                new Button(this);

        editButton.setText(
                "✏️ Edit"
        );

        editButton.setOnClickListener(v -> {

            int index =
                    tasks.indexOf(taskName);

            if (index < 0) {
                return;
            }

            taskInput.setText(
                    tasks.get(index)
            );

            taskTime =
                    times.get(index);

            selectedTime.setText(
                    "⏰ Time: " +
                    taskTime
            );
ReminderScheduler.cancelTaskReminder(
        this,
        index
);
            isEditing = true;
            editingIndex = index;
        });

        // DELETE BUTTON

        Button deleteButton =
                new Button(this);

        deleteButton.setText(
                "🗑️ Delete"
        );

        deleteButton.setOnClickListener(v -> {

            int index =
                    tasks.indexOf(taskName);

            if (index < 0) {
                return;
            }

            tasks.remove(index);
            times.remove(index);
            taskCompleted.remove(index);
            ReminderScheduler.cancelTaskReminder(
        this,
        index
);
            data.deleteTask(index);

            data.saveTotalTasks(
                    tasks.size()
            );

            taskList.removeAllViews();

            completedTasks = 0;

            for (int j = 0;
                 j < taskCompleted.size();
                 j++) {

                if (taskCompleted.get(j)) {
                    completedTasks++;
                }

                addTaskToScreen(
        tasks.get(j),
        times.get(j),
        j + 1,
        taskCompleted.get(j),
        data.getTaskImportant(j)
);
            }

            if (isEditing &&
                    editingIndex == index) {

                isEditing = false;
                editingIndex = -1;

                taskInput.setText("");

                taskTime = "";

                selectedTime.setText(
                        "⏰ No time selected"
                );
            }

            saveTaskData();

            updateProgress();
        });

        taskCard.addView(editButton);
        taskCard.addView(deleteButton);

        taskList.addView(taskCard);
    }

    private void updateProgress() {

        int totalTasks =
                tasks.size();

        if (totalTasks == 0) {

            completedTasks = 0;

            progressText.setText(
                    "📊 Daily Completion: 0%"
            );

            data.saveCompletedTasks(0);
            data.saveTotalTasks(0);
            data.saveDailyPercentage(0);

            return;
        }

        completedTasks = 0;

        for (Boolean completed :
                taskCompleted) {

            if (completed) {
                completedTasks++;
            }
        }

        int percentage =
                (completedTasks * 100)
                / totalTasks;

        if (percentage > 100) {
            percentage = 100;
        }

        progressText.setText(
                "📊 Daily Completion: " +
                percentage +
                "%"
        );

        data.saveCompletedTasks(
                completedTasks
        );

        data.saveTotalTasks(
                totalTasks
        );

        data.saveDailyPercentage(
                percentage
        );

        if (percentage >= 80) {

            progressText.setText(
                    "📊 Daily Completion: " +
                    percentage +
                    "%\n🧠 Brain Puzzle Unlocked!"
            );

            showBrainPuzzleButton();
        }
    }

    private void showBrainPuzzleButton() {

        if (puzzleButton != null) {
            return;
        }

        puzzleButton =
                new Button(this);

        puzzleButton.setText(
                "🧠 Open Brain Puzzle"
        );

        puzzleButton.setTextSize(18);

        puzzleButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ScheduleActivity.this,
                            BrainPuzzleActivity.class
                    );

            startActivity(intent);
        });

        taskList.addView(
                puzzleButton
        );
    }

    private void saveTaskData() {

        data.saveTotalTasks(
                tasks.size()
        );

        data.saveCompletedTasks(
                completedTasks
        );

        data.saveTodayCompletion();
    }

    private void loadSavedTasks() {

        int savedTotal =
                data.getTotalTasks();

        if (savedTotal <= 0) {

            completedTasks = 0;

            progressText.setText(
                    "📊 Daily Completion: 0%"
            );

            return;
        }

        for (int i = 0;
             i < savedTotal;
             i++) {

            String taskName =
                    data.getTaskName(i);

            String time =
                    data.getTaskTime(i);

            boolean completed =
                    data.getTaskCompleted(i);
                    boolean important =
        data.getTaskImportant(i);

            if (taskName.isEmpty()) {
                continue;
            }

            tasks.add(taskName);
            times.add(time);
            taskCompleted.add(completed);

            addTaskToScreen(
        taskName,
        time,
        i + 1,
        completed,
        important
);
        }

        completedTasks = 0;

        for (Boolean completed :
                taskCompleted) {

            if (completed) {
                completedTasks++;
            }
        }

        updateProgress();
    }
}