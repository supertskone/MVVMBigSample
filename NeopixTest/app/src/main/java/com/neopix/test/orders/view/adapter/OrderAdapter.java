package com.neopix.test.orders.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.ItemHeaderBinding;
import com.neopix.test.orders.databinding.OrderMainListItemBinding;
import com.neopix.test.orders.service.model.ItemType;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.Orders;
import com.neopix.test.orders.view.callback.OrderClickCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.neopix.test.orders.service.model.StatusType.*;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Orders orderList;
    private Context context;
    private int position;

    @Nullable
    private final OrderClickCallback orderClickCallback;

    public OrderAdapter(@Nullable OrderClickCallback orderClickCallback) {
        this.orderClickCallback = orderClickCallback;
    }


    public void setOrderList(Context context, Orders orderList) {
        this.context = context;
        if (this.orderList == null) {
            List<Order> orders = orderList.data;
            orderList.data = orders;
            this.orderList = orderList;


            notifyItemRangeInserted(0, orders.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OrderAdapter.this.orderList.data.size();
                }

                @Override
                public int getNewListSize() {
                    return orderList.data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return OrderAdapter.this.orderList.data.get(oldItemPosition).id ==
                            orderList.data.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Order order = orderList.data.get(newItemPosition);
                    Order old = orderList.data.get(oldItemPosition);
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
        this.position = position;
        //viewHolder = null;
        if (orderList.data.get(position).type == ItemType.Header) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

            headerViewHolder.binding.setOrder(orderList.data.get(position));
            headerViewHolder.binding.executePendingBindings();
        }
        if (orderList.data.get(position).type == ItemType.Post) {
            OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;
            Order order = orderList.data.get(position);

            if(order.venue.name.length() > 12) {
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
        return orderList == null ? 0 : orderList.data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (orderList.data.get(position).type == ItemType.Post) {
            return ItemType.Post;
        } else {
            return ItemType.Header;
        }
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
