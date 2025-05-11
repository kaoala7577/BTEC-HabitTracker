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
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import dev.kaoala.habittracker.R;
import dev.kaoala.habittracker.databinding.FragmentWorkoutBinding;

public class WorkoutFragment extends Fragment implements View.OnClickListener {
    private FragmentWorkoutBinding binding;
    Boolean pause = true;
    int timerCount = 0;
    int lastTime;
    int totalTime;
    int workoutCount = 0;
    Timer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutViewModel workoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);

        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.timerButton.setOnClickListener(this);
        binding.timerClear.setOnClickListener(this);
        binding.saveButton.setOnClickListener(this);
        start_timer();
        return root;
    }

    public void start_timer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (!pause) {
                                timerCount += 1;
                                updateTimerText();
                            }
                        });
                    }

                }
            }, 0, 1);
        }
    }

    private void updateTimerText() {
        int milliseconds = timerCount % 1000;
        binding.timerText.setText(formatTimer(timerCount));
        binding.timerMilliseconds.setText(String.format(Locale.getDefault(), "%1$03d", milliseconds));
    }

    private String formatTimer(int time) {
        int seconds = time/1000;
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

    private void swapPlayButton() {
        if (!pause) {
            Drawable pauseDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.pause_button);
            binding.timerButton.setBackground(pauseDrawable);
            binding.timerButton.setContentDescription("@string/pause_button");
        } else {
            Drawable playDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.play_button);
            binding.timerButton.setBackground(playDrawable);
            binding.timerButton.setContentDescription("@string/play_button");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.timerButton) {
            pause = !pause;
            swapPlayButton();
            if(pause) lastTime = timerCount;
        } else if (v == binding.timerClear) {
            pause = true;
            swapPlayButton();
            timerCount = 0;
            updateTimerText();
        } else if (v == binding.saveButton) {
            workoutCount += 1;
            if(workoutCount == 1) binding.textOutput.setVisibility(View.VISIBLE);
            totalTime += lastTime;
            binding.textOutput.setText(String.format(
                    Locale.getDefault(),
                    "Last time: %1$s\nTotal time: %2$s\nTotal workouts: %3$d",
                    formatTimer(lastTime),
                    formatTimer(totalTime),
                    workoutCount
            ));
        }
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