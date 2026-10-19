package com.dayflow.app;

import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BrainPuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DayFlowData data = new DayFlowData(this);

        int percentage = data.getDailyPercentage();

        // =========================
        // TODAY'S DATE
        // =========================

        String today = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());

        // =========================
        // GET TODAY'S PUZZLE
        // =========================

       int puzzleId = data.getPuzzleId();
String savedDate = data.getPuzzleDate();

int requiredStartId;
int requiredEndId;

if (percentage >= 100) {
    requiredStartId = 7;
    requiredEndId = 10;
}
else if (percentage >= 90) {
    requiredStartId = 4;
    requiredEndId = 6;
}
else {
    requiredStartId = 1;
    requiredEndId = 3;
}

boolean puzzleMatchesDifficulty =
        puzzleId >= requiredStartId &&
        puzzleId <= requiredEndId;

if (!today.equals(savedDate) || !puzzleMatchesDifficulty) {

    puzzleId = getNewPuzzleId(
            data,
            percentage
    );

    data.savePuzzleId(puzzleId);
    data.savePuzzleDate(today);
}

        // =========================
        // SCREEN
        // =========================

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        layout.setPadding(
                30,
                50,
                30,
                30
        );

        // =========================
        // TITLE
        // =========================

        TextView title = new TextView(this);

        title.setText(
                "🧠 Brain Exercise"
        );

        title.setTextSize(30);

        title.setTypeface(
                null,
                Typeface.BOLD
        );

        title.setGravity(
                Gravity.CENTER
        );

        // =========================
        // MESSAGE
        // =========================

        TextView message = new TextView(this);

        message.setText(
                "Great job! 🎉\n\n" +
                "Today's completion: " +
                percentage + "%"
        );

        message.setTextSize(18);

        message.setGravity(
                Gravity.CENTER
        );

        message.setPadding(
                0,
                30,
                0,
                20
        );

        // =========================
        // DIFFICULTY
        // =========================

        TextView difficulty = new TextView(this);

        difficulty.setTextSize(20);

        difficulty.setTypeface(
                null,
                Typeface.BOLD
        );

        difficulty.setGravity(
                Gravity.CENTER
        );

        difficulty.setPadding(
                0,
                10,
                0,
                20
        );

        // =========================
        // PUZZLE
        // =========================

        TextView puzzle = new TextView(this);

        puzzle.setTextSize(24);

        puzzle.setGravity(
                Gravity.CENTER
        );

        puzzle.setPadding(
                0,
                20,
                0,
                30
        );

        String correctAnswer;
        final int currentPuzzleId = puzzleId;

        // =========================
        // NORMAL
        // 80-89%
        // =========================

        if (puzzleId == 1) {

            difficulty.setText(
                    "🟢 Normal Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "2, 4, 8, 16, ?"
            );

            correctAnswer = "32";

        }

        else if (puzzleId == 2) {

            difficulty.setText(
                    "🟢 Normal Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "5, 10, 15, 20, ?"
            );

            correctAnswer = "25";

        }

        else if (puzzleId == 3) {

            difficulty.setText(
                    "🟢 Normal Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "1, 3, 6, 10, ?"
            );

            correctAnswer = "15";

        }

        // =========================
        // HARD
        // 90-99%
        // =========================

        else if (puzzleId == 4) {

            difficulty.setText(
                    "🟠 Hard Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "3, 6, 12, 24, 48, ?"
            );

            correctAnswer = "96";

        }

        else if (puzzleId == 5) {

            difficulty.setText(
                    "🟠 Hard Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "4, 9, 19, 39, ?"
            );

            correctAnswer = "79";

        }

        else if (puzzleId == 6) {

            difficulty.setText(
                    "🟠 Hard Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "7, 14, 28, 56, ?"
            );

            correctAnswer = "112";

        }

        // =========================
        // EXPERT
        // 100%
        // =========================

        else if (puzzleId == 7) {

            difficulty.setText(
                    "🔴 Expert Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "2, 6, 12, 20, 30, ?"
            );

            correctAnswer = "42";

        }

        else if (puzzleId == 8) {

            difficulty.setText(
                    "🔴 Expert Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "1, 4, 9, 16, 25, ?"
            );

            correctAnswer = "36";

        }

        else if (puzzleId == 9) {

            difficulty.setText(
                    "🔴 Expert Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "2, 3, 5, 8, 12, 17, ?"
            );

            correctAnswer = "23";

        }

        else if (puzzleId == 10) {

            difficulty.setText(
                    "🔴 Expert Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "1, 2, 6, 24, 120, ?"
            );

            correctAnswer = "720";

        }

        else {

            difficulty.setText(
                    "🟢 Normal Difficulty"
            );

            puzzle.setText(
                    "What comes next?\n\n" +
                    "10, 20, 30, 40, ?"
            );

            correctAnswer = "50";
        }

        // =========================
        // ANSWER INPUT
        // =========================

        EditText answer = new EditText(this);

        answer.setHint(
                "Enter your answer"
        );

        answer.setTextSize(18);

        answer.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER
        );

        // =========================
        // CHECK BUTTON
        // =========================

        Button checkButton = new Button(this);

        checkButton.setText(
                "✅ Check Answer"
        );

        // =========================
        // RESULT
        // =========================

        TextView result = new TextView(this);

        result.setTextSize(20);

        result.setGravity(
                Gravity.CENTER
        );

        result.setPadding(
                0,
                30,
                0,
                30
        );

        // =========================
        // CHECK ANSWER
        // =========================

        checkButton.setOnClickListener(v -> {

            String userAnswer =
                    answer.getText()
                            .toString()
                            .trim();

            if (userAnswer.isEmpty()) {

                result.setText(
                        "⚠ Please enter an answer."
                );

                return;
            }

            if (userAnswer.equals(correctAnswer)) {

                result.setText(
                        "🎉 Correct!\n\n" +
                        "Excellent work!"
                );

               data.saveUsedPuzzle(
        currentPuzzleId
);

            } else {

                result.setText(
                        "❌ Incorrect.\n\n" +
                        "Try again!"
                );
            }
        });

        // =========================
        // BACK BUTTON
        // =========================

        Button back = new Button(this);

        back.setText(
                "← Back"
        );

        back.setOnClickListener(
                v -> finish()
        );

        // =========================
        // ADD VIEWS
        // =========================

        layout.addView(title);

        layout.addView(message);

        layout.addView(difficulty);

        layout.addView(puzzle);

        layout.addView(answer);

        layout.addView(checkButton);

        layout.addView(result);

        layout.addView(back);

        setContentView(layout);
    }

    // =========================
    // FIND UNUSED PUZZLE
    // =========================

    private int getNewPuzzleId(
            DayFlowData data,
            int percentage
    ) {

        int startId;
        int endId;

        // 100%
        if (percentage >= 100) {

            startId = 7;
            endId = 10;

        }

        // 90-99%
        else if (percentage >= 90) {

            startId = 4;
            endId = 6;

        }

        // 80-89%
        else {

            startId = 1;
            endId = 3;
        }

        // =========================
        // FIND UNUSED PUZZLE
        // =========================

        for (int id = startId; id <= endId; id++) {

            if (!data.isPuzzleUsed(id)) {

                return id;
            }
        }

        // =========================
        // ALL PUZZLES USED
        // =========================
        //
        // If every puzzle in the
        // current difficulty has
        // already been used, return
        // the first puzzle for now.
        //
        // We will expand the puzzle
        // bank later.
        // =========================

        Random random = new Random();

        return startId +
                random.nextInt(
                        endId - startId + 1
                );
    }
}