package dev.kaoala.habittracker.ui.workout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WorkoutViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}