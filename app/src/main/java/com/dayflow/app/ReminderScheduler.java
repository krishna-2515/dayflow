package com.dayflow.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ReminderScheduler {

    // =====================================================
    // NORMAL TIME-BASED REMINDER
    // =====================================================

    public static void scheduleReminder(
            Context context,
            int hour,
            int minute,
            String type
    ) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(
                        Context.ALARM_SERVICE
                );

        Intent intent =
                new Intent(context, ReminderReceiver.class);

        intent.putExtra(
                "reminder_type",
                type
        );

        int requestCode;

        if (type.equals("water")) {
            requestCode = 1001;
        } else {
            requestCode = 1002;
        }

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        Calendar calendar =
                Calendar.getInstance();

        calendar.set(
                Calendar.HOUR_OF_DAY,
                hour
        );

        calendar.set(
                Calendar.MINUTE,
                minute
        );

        calendar.set(
                Calendar.SECOND,
                0
        );

        calendar.set(
                Calendar.MILLISECOND,
                0
        );

        if (calendar.getTimeInMillis()
                <= System.currentTimeMillis()) {

            calendar.add(
                    Calendar.DAY_OF_MONTH,
                    1
            );
        }

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }


    // =====================================================
    // WATER INTERVAL REMINDER
    // =====================================================

    public static void scheduleWaterInterval(
            Context context,
            int hours,
            int minutes
    ) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(
                        Context.ALARM_SERVICE
                );

        Intent intent =
                new Intent(
                        context,
                        ReminderReceiver.class
                );

        intent.putExtra(
                "reminder_type",
                "water"
        );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        1001,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        long intervalMillis =
                ((hours * 60L) + minutes)
                        * 60L
                        * 1000L;

        if (intervalMillis <= 0) {
            return;
        }

        long triggerTime =
                System.currentTimeMillis()
                        + intervalMillis;

            try {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S
            && alarmManager.canScheduleExactAlarms()) {

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
        );

    } else {

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
        );
    }

} catch (SecurityException e) {

    alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
    );
}
}

public static void cancelWaterReminder(
        Context context
) {

    AlarmManager alarmManager =
            (AlarmManager) context.getSystemService(
                    Context.ALARM_SERVICE
            );

    Intent intent =
            new Intent(
                    context,
                    ReminderReceiver.class
            );

    PendingIntent pendingIntent =
            PendingIntent.getBroadcast(
                    context,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                            | PendingIntent.FLAG_IMMUTABLE
            );

    alarmManager.cancel(pendingIntent);
}


public static void scheduleTaskReminder(
        Context context,
        int taskIndex,
        int hour,
        int minute,
        String taskName
) {

    AlarmManager alarmManager =
            (AlarmManager) context.getSystemService(
                    Context.ALARM_SERVICE
            );

    Intent intent =
            new Intent(
                    context,
                    ReminderReceiver.class
            );

    intent.putExtra(
            "reminder_type",
            "study"
    );
intent.putExtra(
        "task_name",
        taskName
);
    intent.putExtra(
            "task_index",
            taskIndex
    );

    int requestCode =
            2000 + taskIndex;

    PendingIntent pendingIntent =
            PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                            | PendingIntent.FLAG_IMMUTABLE
            );

    Calendar calendar =
            Calendar.getInstance();

    calendar.set(
            Calendar.HOUR_OF_DAY,
            hour
    );

    calendar.set(
            Calendar.MINUTE,
            minute
    );

    calendar.set(
            Calendar.SECOND,
            0
    );

    calendar.set(
            Calendar.MILLISECOND,
            0
    );

    if (calendar.getTimeInMillis()
            <= System.currentTimeMillis()) {

        calendar.add(
                Calendar.DAY_OF_MONTH,
                1
        );
    }

   try {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S
            && alarmManager.canScheduleExactAlarms()) {

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

    } else {

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

} catch (SecurityException e) {

    alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            pendingIntent
    );
}
}
public static void cancelTaskReminder(
        Context context,
        int taskIndex
) {

    AlarmManager alarmManager =
            (AlarmManager) context.getSystemService(
                    Context.ALARM_SERVICE
            );

    Intent intent =
            new Intent(
                    context,
                    ReminderReceiver.class
            );

    PendingIntent pendingIntent =
            PendingIntent.getBroadcast(
                    context,
                    2000 + taskIndex,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                            | PendingIntent.FLAG_IMMUTABLE
            );

    alarmManager.cancel(pendingIntent);
}
}