package com.neopix.test.orders.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.OrderedProductItemBinding;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderedProducts;
import com.neopix.test.orders.view.callback.OrderClickCallback;

import java.util.ArrayList;

public class OrderedProductsAdapter extends RecyclerView.Adapter<OrderedProductsAdapter.ViewHolder> {

  private ArrayList<OrderedProducts> orderedProducts;
  private Context context;
  private OrderedProductItemBinding orderedProductsBinding;

  public OrderedProductsAdapter(Context ctx) {
    context = ctx;
  }

  public void setOrderedProductsList(Context context, final ArrayList<OrderedProducts> orderedProductsList) {
    this.context = context;
    if (this.orderedProducts == null) {
      this.orderedProducts = orderedProductsList;
      notifyItemRangeInserted(0, orderedProducts.size());
    }
  }

  @Override
  public OrderedProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
    orderedProductsBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.getContext()),
      R.layout.ordered_product_item, parent, false);

    return new ViewHolder(orderedProductsBinding);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    OrderedProducts dataModel = orderedProducts.get(position);
    holder.binding.setOrderedProducts(dataModel);
    holder.binding.executePendingBindings();
  }

  @Override
  public int getItemCount() {
    return this.orderedProducts.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public OrderedProductItemBinding binding;

    public ViewHolder(OrderedProductItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Object obj) {
      binding.setVariable(0, obj);
      binding.executePendingBindings();
    }
  }
}


