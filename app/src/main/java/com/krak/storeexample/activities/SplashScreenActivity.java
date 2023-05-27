package com.krak.storeexample.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.krak.storeexample.databinding.SplashScreenBinding;
import com.krak.storeexample.utils.PreferenceManager;

public class SplashScreenActivity extends AppCompatActivity {

    private SplashScreenBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(this);
        binding = SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startWaiting();
    }

    // Время вышло, нужно идти дальше
    private void goNext(){
        Intent intent;
        if (!preferenceManager.onboardShowed()) {
            intent = new Intent(SplashScreenActivity.this, OnboardActivity.class);
        } else if (!preferenceManager.containsEmail()){
            intent = new Intent(SplashScreenActivity.this, EmailInputActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, EmailInputActivity.class);
        }
        startActivity(intent);
    }

    // Ждём 1.2 секунды заставки
    private void startWaiting() {
        addListener();
        // Чтобы экран не завис, ожидание выполняем в отдельном потоке
        new Thread(() -> {
            try{
                Thread.sleep(1200);
            } catch (InterruptedException ignored){
            } finally {
                listener.postValue(true);
            }
        }).start();
    }

    private MutableLiveData<Boolean> listener;

    // Добавляем слушатель к LiveData, чтобы по истечении времени вызвать goNext()
    private void addListener() {
        listener = new MutableLiveData<>();
        listener.observe(this, data -> goNext());
    }
}
