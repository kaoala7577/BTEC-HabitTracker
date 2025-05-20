package dev.kaoala.habittracker.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
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

        for (View button : Arrays.asList(
                binding.buttonReset, binding.buttonClear, binding.buttonBack,
                binding.button7, binding.button8, binding.button9,
                binding.button4, binding.button5, binding.button6,
                binding.button1, binding.button2, binding.button3,
                binding.buttonPlus, binding.button0, binding.buttonMinus)) {
            button.setOnClickListener(this);
        }
        return root;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.buttonPlus) {
            if(!waterInput.isEmpty()) {
                waterTotal += Integer.parseInt(waterInput);
                updateTotalText();
            }
        } else if(v == binding.buttonMinus) {
            if (!waterInput.isEmpty()) {
                waterTotal -= Integer.parseInt(waterInput);
                if (waterTotal < 0) waterTotal = 0;
                updateTotalText();
            }
        } else if (v == binding.buttonReset) {
            waterTotal = 0;
            updateTotalText();
        } else if (v == binding.buttonClear) {
            resetInput();
        } else if (v == binding.buttonBack) {
            if(waterInput.isEmpty()) {
                resetInput();
            } else {
                waterInput = waterInput.substring(0, waterInput.length() - 1);
                if (waterInput.isEmpty()) resetInput();
                else updateInputText();
            }
        } else {
            handleNumberInput(v);
        }
    }

    private void updateTotalText() {
        binding.waterTotal.setText(String.format(
                Locale.getDefault(),
                getString(R.string.water_format),
                waterTotal
        ));
        resetInput();
    }

    private void updateInputText() {
        binding.waterInput.setText(String.format(
                Locale.getDefault(),
                getString(R.string.water_format),
                Integer.parseInt(waterInput)
        ));
    }

    private void resetInput() {
        waterInput = "";
        binding.waterInput.setText(R.string.water_default);
    }

    private void handleNumberInput(View v) {
        if(waterInput.length() < 5) {
            String digit = ((android.widget.Button) v).getText().toString();
            waterInput += digit;
            updateInputText();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}