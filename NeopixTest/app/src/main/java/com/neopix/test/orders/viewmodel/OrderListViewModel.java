package com.neopix.test.orders.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neopix.test.orders.service.model.Orders;
import com.neopix.test.orders.service.repository.OrderRepository;

import javax.inject.Inject;

public class OrderListViewModel extends AndroidViewModel {
    private final LiveData<Orders> orderListObservable;

    @Inject
    public OrderListViewModel(@NonNull OrderRepository orderRepository, @NonNull Application application) {
        super(application);

        orderListObservable = orderRepository.getOrderList();
    }

    public LiveData<Orders> getOrderListObservable() {
        return orderListObservable;
    }
}
