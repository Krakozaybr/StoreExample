package com.krak.storeexample.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.krak.storeexample.R;
import com.krak.storeexample.databinding.CreateCardActivityBinding;
import com.krak.storeexample.fragments.CardFragment;

public class CreateCardActivity extends AppCompatActivity {

    private CreateCardActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateCardActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        addEventListeners();
    }

    private void addEventListeners() {
        CardFragment cardFragment = (CardFragment) getSupportFragmentManager().findFragmentById(R.id.createCardFragment);
        cardFragment.addTextChangedListener(() -> {
            binding.createCardCreateBtn.setEnabled(cardFragment.isFilled());
        });
        binding.createCardSkipBtn.setOnClickListener((view) -> {

        });

    }
}
