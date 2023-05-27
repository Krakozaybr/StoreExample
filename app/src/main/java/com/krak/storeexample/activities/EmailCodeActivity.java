package com.krak.storeexample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.krak.storeexample.R;
import com.krak.storeexample.databinding.EmailCodeActivityBinding;
import com.krak.storeexample.databinding.EmailInputActivityBinding;
import com.krak.storeexample.utils.ErrorDialog;
import com.krak.storeexample.utils.PreferenceManager;
import com.krak.storeexample.utils.api.API;
import com.krak.storeexample.utils.api.APIHelper;
import com.krak.storeexample.utils.api.entities.TokenResponse;

import in.aabhasjindal.otptextview.OTPListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailCodeActivity extends AppCompatActivity {
    private EmailCodeActivityBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmailCodeActivityBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(this);
        setContentView(binding.getRoot());
        sendCode();
        addEventListeners();

        binding.emailCode.requestFocusOTP();
    }

    private void addEventListeners() {
        binding.emailCode.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                checkCode();
            }
        });
        binding.emailCodeSendcodeBtn.setOnClickListener((view) -> {
            sendCode();
        });
    }

    private void startCountdown(){
        MutableLiveData<Integer> liveData = new MutableLiveData<>();

        liveData.observe(this, integer -> {
            binding.emailCodeCountdown.setText(String.format(getString(R.string.repeat_can_be), "" + integer));
            if (integer == 0){
                binding.emailCodeSendcodeBtn.setVisibility(View.VISIBLE);
                binding.emailCodeCountdown.setVisibility(View.GONE);
            }
        });

        new Thread(() -> {
            int time = 60;
            while (time > 0){
                time--;
                liveData.postValue(time);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                }
            }
        }).start();
    }

    private void sendCode(){

        binding.emailCodeSendcodeBtn.setVisibility(View.GONE);
        binding.emailCodeCountdown.setVisibility(View.VISIBLE);

        API api = APIHelper.getInstance().getApi();

        Call<Void> call = api.sendCode(preferenceManager.getEmail());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new ErrorDialog(getString(R.string.error), getString(R.string.email_failure))
                        .show(getSupportFragmentManager(), "custom");
            }
        });

        startCountdown();
    }

    private void checkCode(){
        String code = binding.emailCode.getOTP();
        API api = APIHelper.getInstance().getApi();
        api.signIn(
                preferenceManager.getEmail(),
                code
        ).observe(this, tokenResponseResource -> {
            if (tokenResponseResource.isSuccess()){
                String token = tokenResponseResource.getResource().token;
                preferenceManager.saveToken(token);
                goNext();
            } else {
                new ErrorDialog(getString(R.string.error), getString(R.string.code_failure))
                        .show(getSupportFragmentManager(), "custom");
                binding.emailCode.setOTP("");
            }
        });
    }

    private void goNext(){
        Intent intent = new Intent(EmailCodeActivity.this, CreatePasswordActivity.class);
        startActivity(intent);
    }
}
