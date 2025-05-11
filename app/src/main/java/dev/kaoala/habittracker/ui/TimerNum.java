package dev.kaoala.habittracker.ui;

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
import dev.kaoala.habittracker.databinding.TimerLayoutBinding;

public class TimerNum extends Fragment implements View.OnClickListener {
    private TimerLayoutBinding binding;
    Boolean pause = true;
    Integer count = 0;
    Timer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = TimerLayoutBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        binding.timerButton.setOnClickListener(this);
        binding.timerClear.setOnClickListener(this);
        start_timer();
        return root;
    }

    public void start_timer() {
        System.out.println("Timer starting");
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (!pause) {
                                count += 1;
                                System.out.println(count);
                                updateTimerText();
                            }
                        });
                    }

                }
            }, 0, 1);
        }
    }

    private void updateTimerText() {
        int seconds = count/1000;
        int minutes = seconds/60;
        int hours = minutes/60;
        int milliseconds = count % 1000;

        binding.timerText.setText(String.format(
                Locale.getDefault(),
                "%1$0,2d:%2$02d:%3$02d",
                hours,
                minutes % 60,
                seconds % 60
        ));
        binding.timerMilliseconds.setText(String.format(Locale.getDefault(), "%1$03d", milliseconds));
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
        System.out.println("Button pressed");
        if (v == binding.timerButton) {
            pause = !pause;
            swapPlayButton();
            System.out.println("Play button pressed");
        } else if (v == binding.timerClear) {
            System.out.println("Stop button pressed");
            pause = true;
            swapPlayButton();
            count = 0;
            updateTimerText();
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
