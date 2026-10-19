package com.dayflow.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = intent.getStringExtra("reminder_type");
        String taskName =
        intent.getStringExtra("task_name");

        if (type == null) {
            type = "study";
        }

        String title;
        String message;

if (type.equals("water")) {
DayFlowData data =
        new DayFlowData(context);

if (data.getWater() >= data.getWaterGoal()) {

    ReminderScheduler.cancelWaterReminder(context);
    data.disableWaterReminder();
    return;
}
    title = "💧 Water Reminder";
    message = "It's water time. Please drink water.";

} else {

    if (taskName != null && !taskName.isEmpty()) {

        title = "⏰ " + taskName;

        message =
                "It's "
                + taskName.toLowerCase(java.util.Locale.getDefault())
                + " time. Let's start.";

    } else {

        title = "📚 Study Reminder";
        message = "It's study time. Start your study session.";

    }
}

        Intent openIntent =
                new Intent(context, MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        2001,
                        openIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        Notification notification =
                new Notification.Builder(
                        context,
                        "DAYFLOW_REMINDER"
                )
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .build();

        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );

        manager.notify(
                type.equals("water") ? 3001 : 3002,
                notification
        );
        Intent serviceIntent =
        new Intent(context, ReminderService.class);

serviceIntent.putExtra(
        "reminder_type",
        type
);
serviceIntent.putExtra(
        "task_name",
        taskName
);
androidx.core.content.ContextCompat.startForegroundService(
        context,
        serviceIntent
);
if (type.equals("water")) {

    DayFlowData data =
            new DayFlowData(context);

    if (data.isWaterReminderEnabled()
            && data.getWater() < data.getWaterGoal()) {

        ReminderScheduler.scheduleWaterInterval(
                context,
                data.getWaterReminderHours(),
                data.getWaterReminderMinutes()
        );
    }
}
    }
}