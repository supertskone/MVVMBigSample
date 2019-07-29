package com.neopix.test.orders.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.neopix.test.orders.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressLint("ValidFragment")
public class FragmentBottomDialog extends BottomSheetDialogFragment {

    public static FragmentBottomDialog getInstance() {
        return new FragmentBottomDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment, container, false);
        (view.findViewById(R.id.button_close)).setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }

}