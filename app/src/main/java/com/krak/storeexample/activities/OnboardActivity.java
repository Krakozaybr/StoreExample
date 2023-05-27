package com.krak.storeexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.krak.storeexample.R;
import com.krak.storeexample.databinding.OnboardActivityBinding;
import com.krak.storeexample.utils.PreferenceManager;
import com.krak.storeexample.utils.StepIndicatorAdapter;

public class OnboardActivity extends AppCompatActivity {
    private OnboardActivityBinding binding;
    private int currentIndex = 0;
    private final Kit[] kits = {
            new Kit(
                    R.drawable.onboard_img_1,
                    R.string.onboard_title_1,
                    R.string.onboard_text_1
            ),
            new Kit(
                    R.drawable.onboard_img_2,
                    R.string.onboard_title_2,
                    R.string.onboard_text_2
            ),
            new Kit(
                    R.drawable.onboard_img_3,
                    R.string.onboard_title_3,
                    R.string.onboard_text_3
            ),
    };
    private StepIndicatorAdapter adapter;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OnboardActivityBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(this);
        setContentView(binding.getRoot());
        binding.onBoardNextBtn.setOnClickListener(view -> nextStep());
        adapter = new StepIndicatorAdapter(kits.length, this);
        binding.onBoardIndicator.setAdapter(adapter);

        updateContent();
        preferenceManager.setOnboardShowed(true);
    }

    private void nextStep(){
        if (currentIndex == kits.length - 1){
            Intent intent = new Intent(OnboardActivity.this, EmailInputActivity.class);
            startActivity(intent);
        } else {
            currentIndex++;
            updateContent();
        }
    }

    private void animate(View view, Runnable runnable){
        final int duration = 150;
        view.animate()
                .alpha(0)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    runnable.run();
                    view.animate()
                            .alpha(1)
                            .setDuration(duration)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }).start();
    }

    private void updateContent(){
        Kit kit = kits[currentIndex];
        if (currentIndex == kits.length - 1){
            animate(binding.onBoardNextBtn, () -> binding.onBoardNextBtn.setText(R.string.confirm));
        }

        animate(binding.onBoardImg, () -> binding.onBoardImg.setImageResource(kit.img_id));

        animate(binding.onBoardText, () -> {
            binding.onBoardText.setText(kit.text_id);
            binding.onBoardText.setGravity(Gravity.CENTER_HORIZONTAL);
        });

        adapter.setFilled(currentIndex);

        animate(binding.onBoardTitle, () -> binding.onBoardTitle.setText(kit.title_id));
    }

    private static class Kit {
        final int img_id;
        final int title_id;
        final int text_id;

        public Kit(int img_id, int title_id, int text_id) {
            this.img_id = img_id;
            this.title_id = title_id;
            this.text_id = text_id;
        }
    }
}
