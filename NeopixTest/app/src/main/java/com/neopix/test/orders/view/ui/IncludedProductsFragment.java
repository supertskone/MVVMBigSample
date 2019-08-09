package com.neopix.test.orders.view.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentIncludedProductsBinding;
import com.neopix.test.orders.service.model.OrderedProducts;
import com.neopix.test.orders.view.adapter.OrderedProductsAdapter;

import java.util.ArrayList;

public class IncludedProductsFragment extends Fragment implements OrderFragment.DisplayOrderedProducts {
  public MutableLiveData<ArrayList<OrderedProducts>> orderedProducts = new MutableLiveData<>();
  public FragmentIncludedProductsBinding binding;
  public OrderedProductsAdapter orderedProductsAdapter;

  public IncludedProductsFragment(ArrayList<OrderedProducts> orderedProducts) {
    this.orderedProducts.setValue(orderedProducts);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_included_products, container, false);

    if (orderedProducts.getValue() != null) {

      orderedProductsAdapter = new OrderedProductsAdapter(getContext());
      orderedProductsAdapter.setOrderedProductsList(getContext(), this.orderedProducts.getValue());
      binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      binding.recyclerView.setHasFixedSize(true);
      binding.recyclerView.setAdapter(orderedProductsAdapter);

    }
    return binding.getRoot();

  }

  protected void displayReceivedData(ArrayList<OrderedProducts> orderedProducts) {
    this.orderedProducts.setValue(orderedProducts);
  }

  @Override
  public void sendOrderedProducts(ArrayList<OrderedProducts> orderedProducts) {
    this.orderedProducts.setValue(orderedProducts);
  }

}
