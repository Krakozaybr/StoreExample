package com.krak.storeexample.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krak.storeexample.utils.api.entities.Profile;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<Profile> liveData = new MutableLiveData<>();

    public LiveData<Profile> getData(){
        return liveData;
    }

    public void setProfile(Profile profile){
        liveData.postValue(profile);
    }
}
