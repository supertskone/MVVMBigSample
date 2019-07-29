package com.neopix.test.orders.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.service.model.ItemType;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.Orders;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class OrderRepository {
    public static final String ERROR = "ERROR";
    private NeopixService neopixService;

    @Inject
    public OrderRepository(NeopixService neopixService) {
        this.neopixService = neopixService;
    }

    public LiveData<Orders> getOrderList() {
        final MutableLiveData<Orders> data = new MutableLiveData<>();

        neopixService.getOrderList().enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                if (response.isSuccessful()) {
                    data.setValue(addOrUpdateOrders(response));
                }
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    private Orders addOrUpdateOrders(Response<Orders> response) {
        Date date = new Date();
        List<Order> orderList = response.body().data;


        // Duplicated it to have more data for testing purposes
        orderList.addAll(response.body().data);

        // I'm comparing current and previous date for order, and if they differ in any way, a new header will be added
        // Also, I can check if they differ in months, but then I would have one header - because all these orders are for April
        for (int i = 0; i < orderList.size(); i++) {
            if (!date.equals(orderList.get(i).orderedAt)) {
                date = orderList.get(i).orderedAt;
                Order newOrder = new Order(date);
                newOrder.setType(ItemType.Header);
                orderList.add(i, newOrder);
            } else {
                orderList.get(i).setType(ItemType.Post);
            }
        }

        Orders orders = new Orders();
        orders.data = orderList;
        return orders;
    }

    public LiveData<OrderDetails> getOrderDetails(int orderId) {
        final MutableLiveData<OrderDetails> data = new MutableLiveData<>();

        neopixService.getOrderDetails(String.valueOf(orderId)).enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
