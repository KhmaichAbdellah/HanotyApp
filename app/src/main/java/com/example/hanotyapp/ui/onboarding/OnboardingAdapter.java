package com.example.hanotyapp.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hanotyapp.R;

public class OnboardingAdapter extends FragmentStateAdapter {

    public OnboardingAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OnboardingStep1Fragment();
            case 1:
                return new OnboardingStep2Fragment();
            case 2:
                return new OnboardingStep3Fragment();
            default:
                return new OnboardingStep1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class OnboardingStep1Fragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_onboarding_step_1, container, false);
        }
    }

    public static class OnboardingStep2Fragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_onboarding_step_2, container, false);
        }
    }

    public static class OnboardingStep3Fragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_onboarding_step_3, container, false);
            // Setup "Commencer" button listener here if needed to navigate to Auth
            view.findViewById(R.id.btn_start).setOnClickListener(v -> {
                // Navigate to Auth
                // Navigation.findNavController(view).navigate(R.id.action_onboarding_to_auth);
            });
            return view;
        }
    }
}
