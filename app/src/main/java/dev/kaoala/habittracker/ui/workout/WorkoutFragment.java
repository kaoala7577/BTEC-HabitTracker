package dev.kaoala.habittracker.ui.workout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import dev.kaoala.habittracker.R;
import dev.kaoala.habittracker.databinding.FragmentWorkoutBinding;

public class WorkoutFragment extends Fragment implements View.OnClickListener {
    private FragmentWorkoutBinding binding;
    Boolean isPaused = true;
    Boolean currentWorkout = false;
    int timerCount = 0;
    int lastTime;
    int totalTime;
    int workoutCount = 0;
    Timer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.timerButton.setOnClickListener(this);
        binding.timerClear.setOnClickListener(this);
        binding.saveButton.setOnClickListener(this);
        initiateTimer();
        return root;
    }

    public void initiateTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (!isPaused) {
                                timerCount += 1;
                                updateTimerText();
                            }
                        });
                    }

                }
            }, 0, 10);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.timerButton) {
            if (isPaused) startTimer();
            else pauseTimer();
        } else if (v == binding.timerClear) {
            clearTimer();
        } else if (v == binding.saveButton) {
            saveWorkout();
        }
    }

    public void startTimer() {
        isPaused = false;
        Drawable pauseDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.pause_button);
        binding.timerButton.setBackground(pauseDrawable);
        binding.timerButton.setContentDescription(requireContext().getString(R.string.pause_button));
    }

    public void pauseTimer() {
        isPaused = true;
        Drawable playDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.play_button);
        binding.timerButton.setBackground(playDrawable);
        binding.timerButton.setContentDescription(requireContext().getString(R.string.play_button));
    }

    public void clearTimer() {
        pauseTimer();
        timerCount = 0;
        currentWorkout = false;
        updateTimerText();
    }

    public void saveWorkout() {
        if (timerCount == 0) return;
        if(currentWorkout) {
            totalTime += timerCount - lastTime;
            lastTime = timerCount;
        } else {
            workoutCount += 1;
            if(workoutCount == 1) binding.textOutput.setVisibility(View.VISIBLE);
            currentWorkout = true;
            lastTime = timerCount;
            totalTime += lastTime;
        }
        binding.textOutput.setText(String.format(
                Locale.getDefault(),
                "Last time: %1$s\nTotal time: %2$s\nTotal workouts: %3$d",
                formatTimer(lastTime),
                formatTimer(totalTime),
                workoutCount
        ));
    }

    private void updateTimerText() {
        int centiseconds = timerCount % 100;
        binding.timerText.setText(formatTimer(timerCount));
        binding.timerCentiseconds.setText(String.format(Locale.getDefault(), "%1$02d", centiseconds));
    }

    private String formatTimer(int time) {
        int seconds = time/100;
        int minutes = seconds/60;
        int hours = minutes/60;

        return String.format(
                Locale.getDefault(),
                "%1$0,2d:%2$02d:%3$02d",
                hours,
                minutes % 60,
                seconds % 60
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        binding = null;
    }
}