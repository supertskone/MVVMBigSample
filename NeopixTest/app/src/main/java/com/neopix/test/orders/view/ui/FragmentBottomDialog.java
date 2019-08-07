package com.neopix.test.orders.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.neopix.test.orders.databinding.BottomFragmentBinding;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.Venue;

@SuppressLint("ValidFragment")
public class FragmentBottomDialog extends BottomSheetDialogFragment implements OrderFragment.DisplayVenue {
  public Order order;
  private BottomFragmentBinding binding;
  public MutableLiveData<Venue> venue = new MutableLiveData<>();

  public static FragmentBottomDialog getInstance() {
    return new FragmentBottomDialog();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.bottom_fragment, container, false);

    if (venue != null) {
      binding.venueFirstName.setText(venue.getValue().primaryContactName);
      binding.venueFirstNumber.setText(venue.getValue().primaryContactPhone);
      binding.venueSecondName.setText(venue.getValue().secondaryContactName);
      binding.venueSecondNumber.setText(venue.getValue().secondaryContactPhone);
      binding.venueAddress.setText(venue.getValue().address);
      binding.executePendingBindings();
    }
    return binding.getRoot();
  }

  @Override
  public void sendVenue(Venue venue) {
    this.venue.setValue(venue);
  }

  protected void displayReceivedData(Venue venue) {
    this.venue.setValue(venue);
  }
}