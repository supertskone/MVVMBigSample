package com.neopix.test.orders.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentIncludedProductsBinding;
import com.neopix.test.orders.service.model.OrderedProducts;

import java.util.ArrayList;

public class OrderedProductsAdapter extends RecyclerView.Adapter<OrderedProductsAdapter.ViewHolder> {
  private ArrayList<OrderedProducts> orderedProductsList;

  public void setOrderedProductsList(ArrayList<OrderedProducts> orderedProductsList) {
    if (this.orderedProductsList == null) {
      this.orderedProductsList = orderedProductsList;
      notifyItemRangeInserted(0, orderedProductsList.size());
    } else {
      DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
          return OrderedProductsAdapter.this.orderedProductsList.size();
        }

        @Override
        public int getNewListSize() {
          return orderedProductsList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
          return OrderedProductsAdapter.this.orderedProductsList.get(oldItemPosition).id ==
            orderedProductsList.get(newItemPosition).id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
          OrderedProducts order = orderedProductsList.get(newItemPosition);
          OrderedProducts old = orderedProductsList.get(oldItemPosition);
          return order.id == old.id;
        }
      });
      this.orderedProductsList = orderedProductsList;
      result.dispatchUpdatesTo(this);
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    FragmentIncludedProductsBinding binding;

    public ViewHolder(FragmentIncludedProductsBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  @Override
  public OrderedProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    FragmentIncludedProductsBinding binding = DataBindingUtil
      .inflate(LayoutInflater.from(parent.getContext()), R.layout.order_main_list_item,
        parent, false);
    return new OrderedProductsAdapter.ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(OrderedProductsAdapter.ViewHolder viewHolder, int position) {

    OrderedProductsAdapter.ViewHolder headerViewHolder = viewHolder;

    headerViewHolder.binding.setIsLoading(false);
    headerViewHolder.binding.executePendingBindings();

  }

  @Override
  public int getItemCount() {
    return orderedProductsList == null ? 0 : orderedProductsList.size();
  }
}
