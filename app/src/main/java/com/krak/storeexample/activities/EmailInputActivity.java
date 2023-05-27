package com.krak.storeexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import com.krak.storeexample.databinding.EmailInputActivityBinding;
import com.krak.storeexample.utils.PreferenceManager;

public class EmailInputActivity extends AppCompatActivity {
    private EmailInputActivityBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmailInputActivityBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(this);
        setContentView(binding.getRoot());
        addEventListeners();
    }

    private void addEventListeners(){
        binding.emailInputEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateEnabled();
            }
        });

        binding.emailInputNextBtn.setOnClickListener(view -> {
            preferenceManager.saveEmail(binding.emailInputEmailInput.getText().toString());

            Intent intent = new Intent(EmailInputActivity.this, EmailCodeActivity.class);
            startActivity(intent);
        });
    }

    private void updateEnabled(){
        binding.emailInputNextBtn.setEnabled(isValidEmail(binding.emailInputEmailInput.getText()));
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
