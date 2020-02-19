package com.cz3002.sharetolearn.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.viewModel.PypViewModel;

public class PypFragment extends Fragment {

    private PypViewModel pypViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pypViewModel =
                ViewModelProviders.of(this).get(PypViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pyp, container, false);
        final TextView textView = root.findViewById(R.id.text_pyp);
        pypViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}