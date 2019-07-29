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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentOrderDetailsBinding;
import com.neopix.test.orders.di.Injectable;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.viewmodel.OrderViewModel;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;


public class OrderFragment extends Fragment implements Injectable {
    private static final String KEY_ORDER_ID = "order_id";
    private FragmentOrderDetailsBinding binding;

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

        OrderViewModel orderViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(OrderViewModel.class);

        orderViewModel.setOrderID(getArguments().getInt(KEY_ORDER_ID));

        binding.setOrderViewModel(orderViewModel);
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

        observeViewModel(orderViewModel);
    }

    private void changeStatusBarColor() {
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.item_details_background));
    }

    private void observeViewModel(final OrderViewModel viewModel) {
        // Observe order data
        viewModel.getObservableOrder().observe(this, order -> {
            if (order != null) {
                binding.setIsLoading(false);
                viewModel.setOrder(order.data);
            }
        });
    }

    public static OrderFragment forOrder(int orderID) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_ORDER_ID, orderID);
        fragment.setArguments(args);

        return fragment;
    }
}
