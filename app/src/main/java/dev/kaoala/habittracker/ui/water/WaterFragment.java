package dev.kaoala.habittracker.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import dev.kaoala.habittracker.R;
import dev.kaoala.habittracker.databinding.FragmentWaterBinding;

public class WaterFragment extends Fragment implements View.OnClickListener {
    private FragmentWaterBinding binding;
    int waterTotal = 0;
    String waterInput = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWaterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button0.setOnClickListener(this);
        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.buttonPlus.setOnClickListener(this);
        binding.buttonMinus.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.buttonPlus) {
            waterTotal += Integer.parseInt(waterInput);
            updateTotalText();
            resetInput();
        } else if(v == binding.buttonMinus) {
            waterTotal -= Integer.parseInt(waterInput);
            updateTotalText();
            resetInput();
        } else {
            handleNumberInput(v);
        }
    }

    private void updateTotalText() {
        binding.waterTotal.setText(String.format(Locale.getDefault(), "%,dml", waterTotal));
    }

    private void resetInput() {
        waterInput = "";
        binding.waterInput.setText(R.string.water_default);
    }

    private void handleNumberInput(View v) {
        if (v == binding.button0) {
            waterInput += "0";
        } else if (v == binding.button1) {
            waterInput += "1";
        } else if (v == binding.button2) {
            waterInput += "2";
        } else if (v == binding.button3) {
            waterInput += "3";
        } else if (v == binding.button4) {
            waterInput += "4";
        } else if (v == binding.button5) {
            waterInput += "5";
        } else if (v == binding.button6) {
            waterInput += "6";
        } else if (v == binding.button7) {
            waterInput += "7";
        } else if (v == binding.button8) {
            waterInput += "8";
        } else if (v == binding.button9) {
            waterInput += "9";
        }
        binding.waterInput.setText(String.format(Locale.getDefault(), "%sml", waterInput));
    }
}