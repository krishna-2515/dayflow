package com.dayflow.app;

import android.app.Service;
import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.Locale;

public class ReminderService extends Service {

    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer;

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ) {

        startForeground(
                1000,
                new Notification.Builder(
                        this,
                        "DAYFLOW_REMINDER"
                )
                        .setContentTitle("DayFlow Reminder")
                        .setContentText("Reminder is active")
                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info
                        )
                        .build(),
                android.content.pm.ServiceInfo
                        .FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        );

        String type = "study";
        String taskName = null;

        if (intent != null) {

            String receivedType =
                    intent.getStringExtra(
                            "reminder_type"
                    );

            if (receivedType != null) {
                type = receivedType;
            }

            taskName =
                    intent.getStringExtra(
                            "task_name"
                    );
        }

        String message;

        if (type.equals("water")) {

            message =
                    "It's water time. Please drink water.";

        } else if (
                taskName != null
                        && !taskName.isEmpty()
        ) {

            message =
                    "It's "
                    + taskName.toLowerCase(
                            Locale.getDefault()
                    )
                    + " time. Let's start.";

        } else {

            message =
                    "It's study time. Start your study session.";
        }

        final String finalMessage = message;

        // =========================
        // CUSTOM SOUND
        // =========================

        DayFlowData data =
                new DayFlowData(this);

        String savedSoundUri =
                data.getCustomSoundUri();

        if (savedSoundUri != null
                && !savedSoundUri.isEmpty()) {

            try {

                mediaPlayer =
                        MediaPlayer.create(
                                this,
                                Uri.parse(savedSoundUri)
                        );

                if (mediaPlayer != null) {

                    mediaPlayer.setOnCompletionListener(
                            mp -> {

                                mp.release();
                                mediaPlayer = null;

                                startTextToSpeech(
                                        finalMessage
                                );
                            }
                    );

                    mediaPlayer.start();

                } else {

                    startTextToSpeech(
                            finalMessage
                    );
                }

            } catch (Exception e) {

                startTextToSpeech(
                        finalMessage
                );
            }

        } else {

            // No custom sound selected
            startTextToSpeech(
                    finalMessage
            );
        }

        return START_NOT_STICKY;
    }


    // =========================
    // TEXT TO SPEECH
    // =========================

    private void startTextToSpeech(
            String message
    ) {

        textToSpeech =
                new TextToSpeech(
                        this,
                        status -> {

                            if (
                                    status
                                    == TextToSpeech.SUCCESS
                            ) {

                                textToSpeech.setLanguage(
                                        Locale.getDefault()
                                );

                                textToSpeech.speak(
                                        message,
                                        TextToSpeech.QUEUE_FLUSH,
                                        null,
                                        "DAYFLOW_REMINDER"
                                );
                            }
                        }
                );
    }


    @Override
    public void onDestroy() {

        if (textToSpeech != null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}