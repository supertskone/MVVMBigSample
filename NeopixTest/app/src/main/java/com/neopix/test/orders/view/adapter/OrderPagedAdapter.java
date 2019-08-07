package com.neopix.test.orders.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.ItemHeaderBinding;
import com.neopix.test.orders.databinding.OrderMainListItemBinding;
import com.neopix.test.orders.service.model.ItemType;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.view.callback.OrderClickCallback;
import com.neopix.test.orders.view.ui.HeaderItemDecoration;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.neopix.test.orders.service.model.StatusType.ACCEPTED;
import static com.neopix.test.orders.service.model.StatusType.PARTIALLYACCEPTED;
import static com.neopix.test.orders.service.model.StatusType.PENDING;

public class OrderPagedAdapter extends PagedListAdapter<Order, RecyclerView.ViewHolder> implements HeaderItemDecoration.HeaderInterface {

  private PagedList<Order> orderList;
  private Context context;

  @Nullable
  private final OrderClickCallback orderClickCallback;

  private static DiffUtil.ItemCallback<Order> DIFF_CALLBACK =
    new DiffUtil.ItemCallback<Order>() {
      @Override
      public boolean areItemsTheSame(Order oldItem, Order newItem) {
        return oldItem.id == newItem.id;
      }

      @SuppressLint("DiffUtilEquals")
      @Override
      public boolean areContentsTheSame(Order oldItem, Order newItem) {
        return oldItem.equals(newItem);
      }
    };

  public OrderPagedAdapter(@Nullable OrderClickCallback orderClickCallback, Context context) {
    super(DIFF_CALLBACK);
    this.orderClickCallback = orderClickCallback;
    this.context = context;
  }


  public void setOrderList(Context context, final PagedList<Order> orderList) {
    this.context = context;
    if (this.orderList == null) {
      PagedList<Order> orders = orderList;
      this.orderList = orderList;


      notifyItemRangeInserted(0, orders.size());
    } else {
      DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
          return OrderPagedAdapter.this.orderList.size();
        }

        @Override
        public int getNewListSize() {
          return orderList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
          return OrderPagedAdapter.this.orderList.get(oldItemPosition).id ==
            orderList.get(newItemPosition).id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
          Order order = orderList.get(newItemPosition);
          Order old = orderList.get(oldItemPosition);
          return order.id == old.id;
        }
      });
      this.orderList = orderList;
      result.dispatchUpdatesTo(this);
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ItemType.Post:
        OrderMainListItemBinding orderListItemBinding = DataBindingUtil
          .inflate(LayoutInflater.from(parent.getContext()), R.layout.order_main_list_item,
            parent, false);

        orderListItemBinding.setCallback(orderClickCallback);
        return new OrderViewHolder(orderListItemBinding);

      case ItemType.Header:
        ItemHeaderBinding headerBinding = DataBindingUtil
          .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_header,
            parent, false);
        return new HeaderViewHolder(headerBinding);
      default:
        return null;
    }
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    int position1 = position;
    //viewHolder = null;
    if (getItem(position).type == ItemType.Header) {
      HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

      headerViewHolder.binding.setOrder(orderList.get(position));
      headerViewHolder.binding.executePendingBindings();
    }
    if (getItem(position).type == ItemType.Post) {
      OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;
      Order order = orderList.get(position);

      if (order.venue.name.length() > 12) {
        order.venue.name = order.venue.name.substring(0, 13);
      }
      updateStatus(orderViewHolder, order);

      loadImage(orderViewHolder, order);

      loadDate(orderViewHolder, order);

      orderViewHolder.binding.setOrder(order);

      orderViewHolder.binding.executePendingBindings();
    }
  }

  private void loadDate(OrderViewHolder orderViewHolder, Order order) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    String dateString = formatter.format(new Date(order.orderedAt.getTime()));

    orderViewHolder.binding.orderDate.setText(dateString);
  }

  private void updateStatus(OrderViewHolder orderViewHolder, Order order) {
    order.status = order.status.replace("_", " ").toUpperCase();

    checkOrderStatus(orderViewHolder, order);
  }

  private void checkOrderStatus(OrderViewHolder orderViewHolder, Order order) {
    switch (order.status) {
      case PARTIALLYACCEPTED:
        orderViewHolder.binding.status.setBackgroundResource(R.drawable.partially_accepted);
        break;
      case ACCEPTED:
        orderViewHolder.binding.status.setBackgroundResource(R.drawable.accepted);
        break;
      case PENDING:
        orderViewHolder.binding.status.setBackgroundResource(R.drawable.pending);
        break;
      default:
        orderViewHolder.binding.status.setBackgroundResource(R.drawable.declined);
        break;
    }
  }

  private void loadImage(OrderViewHolder orderViewHolder, Order order) {
    Picasso picasso = new Picasso.Builder(context)
      .listener((picasso1, uri, exception) -> exception.getMessage())
      .build();
    picasso.load(order.venue.logo)
      .fit()
      .error(R.drawable.ic_orders_total_ammount)
      .into(orderViewHolder.binding.orderLogo);
  }

  @Override
  public int getItemCount() {
    return orderList == null ? 0 : orderList.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (orderList.get(position).type == ItemType.Post) {
      return ItemType.Post;
    } else {
      return ItemType.Header;
    }
  }

  @Override
  public int getHeaderPositionForItem(int itemPosition) {
    int headerPosition = 0;
    for (Integer i = itemPosition; i > 0; i--) {
      if (isHeader(i)) {
        headerPosition = i;
        return headerPosition;
      }
    }
    return headerPosition;
  }

  @Override
  public int getHeaderLayout(int headerPosition) {
    return R.layout.item_header;
  }

  @Override
  public void bindHeaderData(View header, int headerPosition) {
    TextView tv = header.findViewById(R.id.orderDate);
    tv.setText(orderList.get(headerPosition).orderedAt.toString().substring(0, 10).toUpperCase());
  }

  @Override
  public boolean isHeader(int itemPosition) {
    if (orderList.get(itemPosition).type.equals(ItemType.Post)) {
      return false;
    }
    return true;
  }

  class OrderViewHolder extends RecyclerView.ViewHolder {
    OrderMainListItemBinding binding;

    public OrderViewHolder(OrderMainListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  class HeaderViewHolder extends RecyclerView.ViewHolder {
    ItemHeaderBinding binding;

    public HeaderViewHolder(ItemHeaderBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }


}
