package com.neopix.test.orders.view.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentOrderDetailsBinding;
import com.neopix.test.orders.di.Injectable;
import com.neopix.test.orders.service.model.Notes;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.OrderedProducts;
import com.neopix.test.orders.service.model.Venue;
import com.neopix.test.orders.view.adapter.PageAdapter;
import com.neopix.test.orders.viewmodel.OrderDetailViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.neopix.test.orders.service.model.StatusType.ACCEPTED;
import static com.neopix.test.orders.service.model.StatusType.PARTIALLYACCEPTED;
import static com.neopix.test.orders.service.model.StatusType.PENDING;


public class OrderFragment extends Fragment implements Injectable {
  private static final String KEY_ORDER_ID = "order_id";
  private FragmentOrderDetailsBinding binding;
  private DisplayVenue displayVenue;
  private TabLayout tabLayout;
  private ViewPager viewPager;
  private PageAdapter pageAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);

    tabLayout = binding.tablayout;
    viewPager = binding.viewPager;


    return binding.getRoot();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    OrderDetailViewModel orderDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
      .get(OrderDetailViewModel.class);

    orderDetailViewModel.setOrderID(getArguments().getInt(KEY_ORDER_ID));

    binding.setOrderDetailViewModel(orderDetailViewModel);
    binding.setIsLoading(true);
    pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(pageAdapter);

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
      }
    });

    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    changeStatusBarColor();

    observeViewModel(orderDetailViewModel);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      displayVenue = (DisplayVenue) getActivity();
    } catch (ClassCastException e) {
      throw new ClassCastException("Error in retrieving data. Please try again");
    }
  }

  private void changeStatusBarColor() {
    Window window = getActivity().getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.item_details_background));
  }

  private void observeViewModel(final OrderDetailViewModel viewModel) {
    // Observe orderDetails data
    viewModel.getObservableOrder().observe(this, order -> {
      if (order != null) {
        binding.setIsLoading(false);
        viewModel.setOrder(order.data);
        pageAdapter.setOrderedProducts(order.data.orderedProducts);
        pageAdapter.setNotes(order.data.notes);
        displayVenue.sendVenue(order.data.venue);

        setColorByStatus(order);
        setVenueName(order);
      }
    });
  }

  private void setVenueName(OrderDetails order) {
    String venueName = order.data.venue.name;
    if (venueName.length() > 14) {
      order.data.venue.name = venueName.substring(0, 14);
    }
  }

  private void setColorByStatus(OrderDetails order) {
    switch (order.data.status.toUpperCase().replace('_', ' ')) {
      case PARTIALLYACCEPTED:
        binding.status.setBackgroundResource(R.drawable.partially_accepted);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPartiallyAccepted));
        break;
      case ACCEPTED:
        binding.status.setBackgroundResource(R.drawable.accepted);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorAccepted));
        break;
      case PENDING:
        binding.status.setBackgroundResource(R.drawable.pending);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPending));
        break;
      default:
        binding.status.setBackgroundResource(R.drawable.declined);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorDeclined));
        break;
    }
  }

  public static OrderFragment forOrder(int orderID) {
    OrderFragment fragment = new OrderFragment();
    try {
      Bundle args = new Bundle();

      args.putInt(KEY_ORDER_ID, orderID);
      fragment.setArguments(args);
    } catch (Exception e) {
      e.getMessage();
      e.getMessage();
    }

    return fragment;
  }

  interface DisplayVenue {
    void sendVenue(Venue venue);
  }
}
