package com.neopix.test.orders.view.ui;

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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentMainOrderListBinding;
import com.neopix.test.orders.di.Injectable;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.Orders;
import com.neopix.test.orders.view.adapter.OrderAdapter;
import com.neopix.test.orders.view.callback.OrderClickCallback;
import com.neopix.test.orders.viewmodel.OrderListViewModel;

import javax.inject.Inject;

import static com.neopix.test.orders.R.layout.fragment_main_order_list;

public class OrderListFragment extends Fragment implements Injectable {
  public static final String TAG = "OrderListFragment";
  private OrderAdapter orderAdapter;
  private FragmentMainOrderListBinding binding;
  private LinearLayoutManager layoutManager;
  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, fragment_main_order_list, container, false);

    orderAdapter = new OrderAdapter(orderClickCallback);
    layoutManager = new LinearLayoutManager(getContext());
    binding.orderList.setLayoutManager(layoutManager);
    binding.orderList.setAdapter(orderAdapter);
    binding.setIsLoading(true);
    changeStatusBarColor();

    return binding.getRoot();
  }

  private void changeStatusBarColor() {
    Window window = getActivity().getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.item_light_background));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final OrderListViewModel viewModel = ViewModelProviders.of(this,
      viewModelFactory).get(OrderListViewModel.class);

    observeViewModel(viewModel);
  }

  private void observeViewModel(OrderListViewModel viewModel) {
    // Update the list when the data changes
    viewModel.getOrderListObservable().observe(this, orders -> {
      if (orders != null) {
        binding.setIsLoading(false);
        orderAdapter.setOrderList(getContext(), orders);
      }
    });
  }

  private final OrderClickCallback orderClickCallback = order -> {
    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
      ((MainActivity) getActivity()).show(order);
    }
  };
}
