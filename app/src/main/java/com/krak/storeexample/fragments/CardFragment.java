package com.krak.storeexample.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.krak.storeexample.R;
import com.krak.storeexample.databinding.CardFragmentBinding;
import com.krak.storeexample.utils.api.entities.Profile;
import com.krak.storeexample.view_models.ProfileViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class CardFragment extends Fragment {

    private CardFragmentBinding binding;

    private void initSpinner() {
        Spinner spinner = binding.cardSex;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.sex)
        ){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                if (position == 0){
                    view.setTextColor(getResources().getColor(R.color.hint));
                } else {
                    view.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };

        spinner.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CardFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();
        addListeners();
    }

    private void addListeners() {
        ProfileViewModel profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getData().observe(getViewLifecycleOwner(), (profile) -> {
            String[] sex = getResources().getStringArray(R.array.sex);

            int index = 0;
            for (int i = 0; i < sex.length; i++) {
                if (sex[i].equals(profile.getPol())){
                    index = i;
                    break;
                }
            }

            binding.cardSex.setSelection(index);
            binding.cardBirthdate.setText(profile.getBith());
            binding.cardMiddleame.setText(profile.getMiddlename());
            binding.cardName.setText(profile.getFirstname());
            binding.cardSurname.setText(profile.getLastname());
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for (TextChangedListener listener : listeners){
                    listener.textChanged();
                }
                updateProfile();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        };

        binding.cardSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateProfile();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.cardBirthdate.addTextChangedListener(textWatcher);
        binding.cardMiddleame.addTextChangedListener(textWatcher);
        binding.cardName.addTextChangedListener(textWatcher);
        binding.cardSurname.addTextChangedListener(textWatcher);
    }

    public boolean isFilled(){
        return !binding.cardBirthdate.getText().toString().isEmpty() &&
               !binding.cardMiddleame.getText().toString().isEmpty() &&
               !binding.cardName.getText().toString().isEmpty() &&
               !binding.cardSurname.getText().toString().isEmpty() &&
               !binding.cardSex.getSelectedItem().toString().isEmpty();
    }

    public static interface TextChangedListener {
        void textChanged();
    }

    private ArrayList<TextChangedListener> listeners = new ArrayList<>();

    public void addTextChangedListener(TextChangedListener listener){
        listeners.add(listener);
    }

    private void updateProfile(){
        ProfileViewModel profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        Profile profile = profileViewModel.getData().getValue();

        if (profile == null){
            profile = new Profile();
        }

        profile.setPol(binding.cardSex.getSelectedItem().toString());
        profile.setBith(binding.cardBirthdate.getText().toString());
        profile.setMiddlename(binding.cardMiddleame.getText().toString());
        profile.setFirstname(binding.cardName.getText().toString());
        profile.setLastname(binding.cardSurname.getText().toString());

        profileViewModel.setProfile(profile);
    }
}
