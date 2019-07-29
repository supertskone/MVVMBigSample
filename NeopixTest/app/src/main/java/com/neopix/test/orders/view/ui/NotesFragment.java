package com.neopix.test.orders.view.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentStatusBinding;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.view.adapter.OrderedProductsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment implements MainActivity.OnOrderDataReceivedListener {
    public Order order;
    public MainActivity mActivity;
    private FragmentStatusBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        mActivity.setOrderDataListener(this);
        order = mActivity.order.data;

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_status, container, false);
        binding.orderedProductsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderedProductsAdapter orderedProductsAdapter = new OrderedProductsAdapter();

        if (order != null) {
            if (order.orderedProducts != null) {
                orderedProductsAdapter.setOrderedProductsList(order.orderedProducts);
            }
        }

        binding.orderedProductsList.setAdapter(orderedProductsAdapter);
        binding.orderedProductsList.setItemAnimator(new DefaultItemAnimator());

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chats, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }

    @Override
    public void onDataReceived(OrderDetails order) {
    }
}
