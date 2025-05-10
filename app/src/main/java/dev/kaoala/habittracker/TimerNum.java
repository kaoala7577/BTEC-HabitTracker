package dev.kaoala.habittracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerNum extends AppCompatActivity implements View.OnClickListener {
    TextView timer_text;
    TextView timer_milliseconds;
    Button timer_button;
    Button timer_clear;
    Boolean pause = true;
    Integer count = 0;
    Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout);

        timer_text = findViewById(R.id.timer_text);
        timer_milliseconds = findViewById(R.id.timer_milliseconds);
        timer_button = findViewById(R.id.timer_button);
        timer_clear = findViewById(R.id.timer_clear);

        timer_button.setOnClickListener(this);
        timer_clear.setOnClickListener(this);

        start_timer();
    }

    public void start_timer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        if (!pause) {
                            count += 1;
                            timer_text.setText(String.format(Locale.getDefault(),"%1$2d", count));
                            timer_milliseconds.setText(String.format(Locale.getDefault(), "%1$2d", count / 1000));
                        }
                    });
                }
            }, 0, 10);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == timer_button) pause = !pause;
        else if (v == timer_clear) {
            pause = true;
            count = 0;
            timer_text.setText(R.string.timer_default);
            timer_milliseconds.setText(R.string.timer_milliseconds_default);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
