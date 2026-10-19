package com.dayflow.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DayFlowData {

    private static final String PREF_NAME = "DayFlowData";

    private static final String KEY_WATER = "water";
    private static final String KEY_CUSTOM_SOUND_URI =
        "custom_sound_uri";
    private static final String KEY_WATER_GOAL = "water_goal";

    private static final String KEY_WATER_REMINDER_HOURS =
        "water_reminder_hours";

private static final String KEY_WATER_REMINDER_MINUTES =
        "water_reminder_minutes";

private static final String KEY_WATER_REMINDER_ENABLED =
        "water_reminder_enabled";

    private static final String KEY_TASK_NAMES = "task_names";
    private static final String KEY_TASK_TIMES = "task_times";
    private static final String KEY_TASK_COMPLETED = "task_completed";

    private static final String KEY_TASK_IMPORTANT =
        "task_important_";

    private static final String KEY_COMPLETED_TASKS = "completed_tasks";
    private static final String KEY_TOTAL_TASKS = "total_tasks";
    private static final String KEY_DAILY_PERCENTAGE = "daily_percentage";
    private static final String KEY_DAILY_HISTORY = "daily_history";

    private static final String KEY_PUZZLE_ID = "puzzle_id";
    private static final String KEY_PUZZLE_DATE = "puzzle_date";

    private final SharedPreferences prefs;
    public void saveCustomSoundUri(String uri) {

    prefs.edit()
            .putString(
                    KEY_CUSTOM_SOUND_URI,
                    uri
            )
            .apply();
}

public String getCustomSoundUri() {

    return prefs.getString(
            KEY_CUSTOM_SOUND_URI,
            null
    );
}

    public DayFlowData(Context context) {

        prefs = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }

    // =====================================================
    // WATER
    // =====================================================

    public void saveWater(int amount) {

    String today =
            getTodayDate();

    prefs.edit()
            .putInt(
                    KEY_WATER,
                    amount
            )
            .putString(
                    "water_date",
                    today
            )
            .apply();
}
public void saveWaterHistory() {


    String today =
            getTodayDate();

    int amount =
            getWater();

    prefs.edit()
            .putInt(
                    "water_history_" + today,
                    amount
            )
            .apply();
}
public int getWaterHistory(String date) {

    return prefs.getInt(
            "water_history_" + date,
            0
    );
}
    public int getWater() {

    String savedDate =
            prefs.getString(
                    "water_date",
                    ""
            );

    String today =
            getTodayDate();

    if (!today.equals(savedDate)) {

        prefs.edit()
                .putInt(
                        KEY_WATER,
                        0
                )
                .putString(
                        "water_date",
                        today
                )
                .apply();

        return 0;
    }

    return prefs.getInt(
            KEY_WATER,
            0
    );
}

    public void saveWaterGoal(int goal) {

        prefs.edit()
                .putInt(
                        KEY_WATER_GOAL,
                        goal
                )
                .apply();
    }
    // =====================================================
// WATER REMINDER
// =====================================================

public void saveWaterReminder(
        int hours,
        int minutes,
        boolean enabled
) {

    prefs.edit()
            .putInt(
                    KEY_WATER_REMINDER_HOURS,
                    hours
            )
            .putInt(
                    KEY_WATER_REMINDER_MINUTES,
                    minutes
            )
            .putBoolean(
                    KEY_WATER_REMINDER_ENABLED,
                    enabled
            )
            .apply();
}

public int getWaterReminderHours() {

    return prefs.getInt(
            KEY_WATER_REMINDER_HOURS,
            1
    );
}

public int getWaterReminderMinutes() {

    return prefs.getInt(
            KEY_WATER_REMINDER_MINUTES,
            0
    );
}

public boolean isWaterReminderEnabled() {

    return prefs.getBoolean(
            KEY_WATER_REMINDER_ENABLED,
            true
    );
}

public void disableWaterReminder() {

    prefs.edit()
            .putBoolean(
                    KEY_WATER_REMINDER_ENABLED,
                    false
            )
            .apply();
}

    public int getWaterGoal() {

        return prefs.getInt(
                KEY_WATER_GOAL,
                3000
        );
    }

    // =====================================================
    // TASK NAMES
    // =====================================================

    public void saveTaskNames(
            ArrayList<String> tasks
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0;
             i < tasks.size();
             i++) {

            if (i > 0) {
                builder.append("|||");
            }

            builder.append(
                    tasks.get(i)
            );
        }

        prefs.edit()
                .putString(
                        KEY_TASK_NAMES,
                        builder.toString()
                )
                .apply();
    }

    public ArrayList<String> getTaskNames() {

        ArrayList<String> tasks =
                new ArrayList<>();

        String saved =
                prefs.getString(
                        KEY_TASK_NAMES,
                        ""
                );

        if (!saved.isEmpty()) {

            String[] values =
                    saved.split("\\|\\|\\|");

            for (String value :
                    values) {

                tasks.add(value);
            }
        }

        return tasks;
    }

    // =====================================================
    // INDIVIDUAL TASK NAME
    // =====================================================

    public void saveTaskName(
            int index,
            String name
    ) {

        ArrayList<String> tasks =
                getTaskNames();

        while (tasks.size() <= index) {

            tasks.add("");
        }

        tasks.set(
                index,
                name
        );

        saveTaskNames(tasks);
    }

    public String getTaskName(
            int index
    ) {

        ArrayList<String> tasks =
                getTaskNames();

        if (index >= 0 &&
                index < tasks.size()) {

            return tasks.get(index);
        }

        return "";
    }

    // =====================================================
    // TASK TIMES
    // =====================================================

    public void saveTaskTimes(
            ArrayList<String> times
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0;
             i < times.size();
             i++) {

            if (i > 0) {
                builder.append("|||");
            }

            builder.append(
                    times.get(i)
            );
        }

        prefs.edit()
                .putString(
                        KEY_TASK_TIMES,
                        builder.toString()
                )
                .apply();
    }

    public ArrayList<String> getTaskTimes() {

        ArrayList<String> times =
                new ArrayList<>();

        String saved =
                prefs.getString(
                        KEY_TASK_TIMES,
                        ""
                );

        if (!saved.isEmpty()) {

            String[] values =
                    saved.split("\\|\\|\\|");

            for (String value :
                    values) {

                times.add(value);
            }
        }

        return times;
    }

    // =====================================================
    // INDIVIDUAL TASK TIME
    // =====================================================

    public void saveTaskTime(
            int index,
            String time
    ) {

        ArrayList<String> times =
                getTaskTimes();

        while (times.size() <= index) {

            times.add("");
        }

        times.set(
                index,
                time
        );

        saveTaskTimes(times);
    }

    public String getTaskTime(
            int index
    ) {

        ArrayList<String> times =
                getTaskTimes();

        if (index >= 0 &&
                index < times.size()) {

            return times.get(index);
        }

        return "";
    }

    // =====================================================
    // TASK COMPLETION
    // =====================================================

    public void saveTaskCompleted(
            ArrayList<Boolean> completed
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0;
             i < completed.size();
             i++) {

            if (i > 0) {
                builder.append(",");
            }

            builder.append(
                    completed.get(i)
                            ? "1"
                            : "0"
            );
        }

        prefs.edit()
                .putString(
                        KEY_TASK_COMPLETED,
                        builder.toString()
                )
                .apply();
    }

    public ArrayList<Boolean> getTaskCompleted() {

        ArrayList<Boolean> completed =
                new ArrayList<>();

        String saved =
                prefs.getString(
                        KEY_TASK_COMPLETED,
                        ""
                );

        if (!saved.isEmpty()) {

            String[] values =
                    saved.split(",");

            for (String value :
                    values) {

                completed.add(
                        value.equals("1")
                );
            }
        }

        return completed;
    }

    // =====================================================
    // INDIVIDUAL TASK COMPLETION
    // =====================================================

    public void saveTaskCompleted(
            int index,
            boolean completed
    ) {

        ArrayList<Boolean> values =
                getTaskCompleted();

        while (values.size() <= index) {

            values.add(false);
        }

        values.set(
                index,
                completed
        );

        saveTaskCompleted(values);
    }

    public boolean getTaskCompleted(
            int index
    ) {

        ArrayList<Boolean> values =
                getTaskCompleted();

        if (index >= 0 &&
                index < values.size()) {

            return values.get(index);
        }

        return false;
    }

    // =====================================================
// TASK IMPORTANT
// =====================================================

public void saveTaskImportant(
        int index,
        boolean important
) {

    prefs.edit()
            .putBoolean(
                    KEY_TASK_IMPORTANT + index,
                    important
            )
            .apply();
}

public boolean getTaskImportant(
        int index
) {

    return prefs.getBoolean(
            KEY_TASK_IMPORTANT + index,
            false
    );
}

public void deleteTaskImportant(
        int index
) {

    prefs.edit()
            .remove(
                    KEY_TASK_IMPORTANT + index
            )
            .apply();
}

    // =====================================================
    // TASK COUNT
    // =====================================================

    public int getTotalTasks() {

        return getTaskNames().size();
    }

    public int getCompletedTasks() {

        ArrayList<Boolean> completed =
                getTaskCompleted();

        int count = 0;

        for (Boolean value :
                completed) {

            if (value) {
                count++;
            }
        }

        return count;
    }

    // =====================================================
    // SAVED TASK STATISTICS
    // =====================================================

    public void saveCompletedTasks(
            int count
    ) {

        prefs.edit()
                .putInt(
                        KEY_COMPLETED_TASKS,
                        count
                )
                .apply();
    }

    public int getSavedCompletedTasks() {

        return prefs.getInt(
                KEY_COMPLETED_TASKS,
                0
        );
    }

    public void saveTotalTasks(
            int count
    ) {

        prefs.edit()
                .putInt(
                        KEY_TOTAL_TASKS,
                        count
                )
                .apply();
    }

    public int getSavedTotalTasks() {

        return prefs.getInt(
                KEY_TOTAL_TASKS,
                0
        );
    }

    public void saveDailyPercentage(
            int percentage
    ) {

        prefs.edit()
                .putInt(
                        KEY_DAILY_PERCENTAGE,
                        percentage
                )
                .apply();
    }

    public int getDailyPercentage() {

        return prefs.getInt(
                KEY_DAILY_PERCENTAGE,
                getDailyCompletionPercentage()
        );
    }

    // =====================================================
    // DAILY COMPLETION
    // =====================================================

    public int getDailyCompletionPercentage() {

        int total =
                getTotalTasks();

        if (total == 0) {
            return 0;
        }

        int completed =
                getCompletedTasks();

        return (completed * 100) / total;
    }

    // =====================================================
    // CLEAR TASK DATA
    // =====================================================

    public void clearTaskData() {

        prefs.edit()
                .remove(KEY_TASK_NAMES)
                .remove(KEY_TASK_TIMES)
                .remove(KEY_TASK_COMPLETED)
                .remove(KEY_COMPLETED_TASKS)
                .remove(KEY_TOTAL_TASKS)
                .remove(KEY_DAILY_PERCENTAGE)
                .apply();
    }

    // =====================================================
    // DELETE ONE TASK
    // =====================================================

    public void deleteTask(
            int index
    ) {

        ArrayList<String> tasks =
                getTaskNames();

        ArrayList<String> times =
                getTaskTimes();

        ArrayList<Boolean> completed =
                getTaskCompleted();

        if (index < 0 ||
                index >= tasks.size()) {

            return;
        }

        // Remove task
        tasks.remove(index);

        // Remove time
        if (index < times.size()) {

            times.remove(index);
        }

        // Remove completion status
        if (index < completed.size()) {

            completed.remove(index);
        }

        // Save updated lists
        saveTaskNames(tasks);

        saveTaskTimes(times);

        saveTaskCompleted(completed);

        // Update statistics
        saveTotalTasks(
                tasks.size()
        );

        saveCompletedTasks(
                getCompletedTasks()
        );

        saveDailyPercentage(
                getDailyCompletionPercentage()
        );

        // Save today's updated completion
        saveTodayCompletion();
    }

    // =====================================================
    // PUZZLE
    // =====================================================

    public void savePuzzleId(
            int id
    ) {

        prefs.edit()
                .putInt(
                        KEY_PUZZLE_ID,
                        id
                )
                .apply();
    }

    public int getPuzzleId() {

        return prefs.getInt(
                KEY_PUZZLE_ID,
                -1
        );
    }

    public void savePuzzleDate(
            String date
    ) {

        prefs.edit()
                .putString(
                        KEY_PUZZLE_DATE,
                        date
                )
                .apply();
    }

    public String getPuzzleDate() {

        return prefs.getString(
                KEY_PUZZLE_DATE,
                ""
        );
    }

    // =====================================================
    // USED PUZZLES
    // =====================================================

    public void saveUsedPuzzle(
            int id
    ) {

        prefs.edit()
                .putBoolean(
                        "used_puzzle_" + id,
                        true
                )
                .apply();
    }

    public boolean isPuzzleUsed(
            int id
    ) {

        return prefs.getBoolean(
                "used_puzzle_" + id,
                false
        );
    }

    // =====================================================
    // CLEAR PUZZLE HISTORY
    // =====================================================

    public void clearPuzzleHistory() {

        SharedPreferences.Editor editor =
                prefs.edit();

        for (int i = 1;
             i <= 10;
             i++) {

            editor.remove(
                    "used_puzzle_" + i
            );
        }

        editor.remove(
                KEY_PUZZLE_ID
        );

        editor.remove(
                KEY_PUZZLE_DATE
        );

        editor.apply();
    }

    // =====================================================
    // DAILY HISTORY
    // =====================================================

    public void saveTodayCompletion() {

    String today = getTodayDate();

    int percentage = getDailyCompletionPercentage();

    int completedTasks = getCompletedTasks();

    int totalTasks = getTotalTasks();

    String history =
            prefs.getString(
                    KEY_DAILY_HISTORY,
                    ""
            );

    String newEntry =
            today + "=" + percentage;

    if (history.isEmpty()) {

        history = newEntry;

    } else {

        String[] entries =
                history.split("\\|\\|\\|");

        StringBuilder builder =
                new StringBuilder();

        boolean found = false;

        for (String entry : entries) {

            if (entry.startsWith(today + "=")) {

                if (builder.length() > 0) {
                    builder.append("|||");
                }

                builder.append(newEntry);

                found = true;

            } else {

                if (builder.length() > 0) {
                    builder.append("|||");
                }

                builder.append(entry);
            }
        }

        if (!found) {

            if (builder.length() > 0) {
                builder.append("|||");
            }

            builder.append(newEntry);
        }

        history = builder.toString();
    }

    prefs.edit()

            .putString(
                    KEY_DAILY_HISTORY,
                    history
            )

            .putInt(
                    "completed_tasks_" + today,
                    completedTasks
            )

            .putInt(
                    "total_tasks_" + today,
                    totalTasks
            )

            .putInt(
                    "daily_percentage_" + today,
                    percentage
            )

            .putInt(
                    KEY_DAILY_PERCENTAGE,
                    percentage
            )

            .apply();
}
    public int getCompletionForDate(
            String date
    ) {

        String history =
                prefs.getString(
                        KEY_DAILY_HISTORY,
                        ""
                );

        if (history.isEmpty()) {
            return 0;
        }

        String[] entries =
                history.split("\\|\\|\\|");

        for (String entry :
                entries) {

            if (entry.startsWith(
                    date + "="
            )) {

                String value =
                        entry.substring(
                                (date + "=")
                                        .length()
                        );

                try {

                    return Integer.parseInt(
                            value
                    );

                } catch (
                        NumberFormatException e
                ) {

                    return 0;
                }
            }
        }

        return 0;
    }

    // =====================================================
    // TODAY'S DATE
    // =====================================================
// =====================================================
// RESET TASKS FOR NEW DAY
// =====================================================

public void resetTasksForNewDay() {

    String savedDate =
            prefs.getString(
                    "last_task_date",
                    ""
            );

    String today =
            getTodayDate();

    if (!today.equals(savedDate)) {

        ArrayList<Boolean> completed =
                getTaskCompleted();

        for (int i = 0;
             i < completed.size();
             i++) {

            completed.set(i, false);
        }

        saveTaskCompleted(completed);

        prefs.edit()
                .putString(
                        "last_task_date",
                        today
                )
                .apply();
    }
}
// =====================================================
// WEEKLY TASK STATISTICS
// =====================================================

public int getCompletedTasksForDate(String date) {

    return prefs.getInt(
            "completed_tasks_" + date,
            0
    );
}

public int getTotalTasksForDate(String date) {

    return prefs.getInt(
            "total_tasks_" + date,
            0
    );
}
// =====================================================
// CURRENT STREAK
// =====================================================

public int getCurrentStreak() {

    int streak = 0;

    java.util.Calendar calendar =
            java.util.Calendar.getInstance();

    for (int i = 0; i < 7; i++) {

        String date =
                new java.text.SimpleDateFormat(
                        "yyyy-MM-dd",
                        java.util.Locale.getDefault()
                ).format(
                        calendar.getTime()
                );

        int percentage =
                prefs.getInt(
                        "daily_percentage_" + date,
                        0
                );

        if (percentage >= 80) {

            streak++;

        } else {

            break;
        }

        calendar.add(
                java.util.Calendar.DAY_OF_YEAR,
                -1
        );
    }

    return streak;
}
// =====================================================
// DARK MODE
// =====================================================

public boolean isDarkModeEnabled() {

    return prefs.getBoolean(
            "dark_mode_enabled",
            false
    );
}

public void setDarkModeEnabled(
        boolean enabled
) {

    prefs.edit()
            .putBoolean(
                    "dark_mode_enabled",
                    enabled
            )
            .apply();
}
    public String getTodayDate() {

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                );

        return format.format(
                new Date()
        );
    }
}