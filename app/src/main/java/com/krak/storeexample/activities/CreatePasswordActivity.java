package com.krak.storeexample.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.krak.storeexample.databinding.CreatePasswordActivityBinding;
import com.krak.storeexample.utils.PreferenceManager;
import com.krak.storeexample.utils.StepIndicatorAdapter;

public class CreatePasswordActivity extends AppCompatActivity {

    private CreatePasswordActivityBinding binding;
    private StepIndicatorAdapter adapter;
    private ButtonHolder[] buttonHolders;
    private StringBuilder currentText;
    private static final int passwordLength = 4;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreatePasswordActivityBinding.inflate(getLayoutInflater());
        adapter = new StepIndicatorAdapter(4, this);
        binding.createPasswordInput.setAdapter(adapter);
        currentText = new StringBuilder();
        preferenceManager = new PreferenceManager(this);

        initButtonHolders();
        addListeners();
    }

    private void addListeners() {
        for (ButtonHolder holder : buttonHolders){
            holder.btn.setOnClickListener((view) -> {
                currentText.append(holder.btn.getText());
                updateIndicator();
            });
        }
        binding.createPasswordBackspaceBtn.setOnClickListener((view -> {
            if (currentText.length() > 0) {
                currentText.deleteCharAt(currentText.length() - 1);
                updateIndicator();
            }
        }));
    }

    private void updateIndicator(){
        adapter.setFilledCells(currentText.length());
        if (currentText.length() == passwordLength){
            savePassword();
            goNext();
        }
    }

    private void goNext() {
        Intent intent = new Intent(CreatePasswordActivity.this, null);
        startActivity(intent);
    }

    private void savePassword() {
        preferenceManager.savePassword(currentText.toString());
    }

    private void initButtonHolders() {
        buttonHolders = new ButtonHolder[]{
                new ButtonHolder(binding.createPasswordBtn0, 0),
                new ButtonHolder(binding.createPasswordBtn1, 1),
                new ButtonHolder(binding.createPasswordBtn2, 2),
                new ButtonHolder(binding.createPasswordBtn3, 3),
                new ButtonHolder(binding.createPasswordBtn4, 4),
                new ButtonHolder(binding.createPasswordBtn5, 5),
                new ButtonHolder(binding.createPasswordBtn6, 6),
                new ButtonHolder(binding.createPasswordBtn7, 7),
                new ButtonHolder(binding.createPasswordBtn8, 8),
                new ButtonHolder(binding.createPasswordBtn9, 9),
        };
    }

    static class ButtonHolder {
        AppCompatButton btn;
        int num;

        public ButtonHolder(AppCompatButton btn, int num) {
            this.btn = btn;
            this.num = num;
        }
    }
}
