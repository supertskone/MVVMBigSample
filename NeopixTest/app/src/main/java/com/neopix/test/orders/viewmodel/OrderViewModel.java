package com.neopix.test.orders.viewmodel;

import android.app.Application;
import android.util.Log;

import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.repository.OrderRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class OrderViewModel extends AndroidViewModel {
    public static final String TAG = OrderViewModel.class.getName();
    public static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    public final LiveData orderObservable;
    public final MutableLiveData<Integer> orderID;

    public ObservableField<Order> order = new ObservableField<>();

    @Inject
    public OrderViewModel(@NonNull Application application, @NonNull OrderRepository orderRepository) {
        super(application);

        this.orderID = new MutableLiveData<>();

        orderObservable = Transformations.switchMap(orderID, input -> {
            if (input == 0) {
                Log.i(TAG, "OrderID is missing.");
                return ABSENT;
            }

            Log.i(TAG, "OrderID is " + orderID.getValue());

            return orderRepository.getOrderDetails(orderID.getValue());
        });
    }

    public LiveData<OrderDetails> getObservableOrder() {
        return orderObservable;
    }

    public void setOrder(Order order) {
        this.order.set(order);
    }

    public void setOrderID(int orderID) {
        this.orderID.setValue(orderID);
    }
}
