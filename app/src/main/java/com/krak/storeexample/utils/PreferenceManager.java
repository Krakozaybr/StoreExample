package com.krak.storeexample.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.krak.storeexample.databinding.PasswordCreateAcitivityBinding;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PreferenceManager {

    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String TOKEN = "TOKEN";
    private static final String ONBOARD_SHOWED = "ONBOARD_SHOWED";
    private static final String KEY = "77345D763FD65D8E54A278A87BC47";

    private SharedPreferences preferences;

    public PreferenceManager(AppCompatActivity context) {
        try {
            this.preferences = EncryptedSharedPreferences.create(
                    PREFERENCES,
                    KEY,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putString(String key, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getString(String key, String defaultValue){
        return preferences.getString(key, defaultValue);
    }

    private boolean containsKey(String key){
        return preferences.contains(key);
    }

    public String getEmail(){
        return getString(EMAIL, "");
    }

    public void saveEmail(String name){
        putString(EMAIL, name);
    }

    public void savePassword(String password){
        putString(PASSWORD, DigestUtils.md5Hex(password));
    }

    public boolean checkPassword(String password){
        return DigestUtils.md5Hex(password).equals(getString(PASSWORD, ";"));
    }

    public void saveToken(String token){
        putString(TOKEN, token);
    }

    public String getToken(){
        return getString(TOKEN, "");
    }

    public boolean onboardShowed(){
        return getBoolean(ONBOARD_SHOWED, false);
    }

    public void setOnboardShowed(boolean val){
        putBoolean(ONBOARD_SHOWED, val);
    }

    private void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }

    public boolean containsEmail() {
        return containsKey(EMAIL);
    }
}