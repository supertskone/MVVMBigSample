package com.neopix.test.orders.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.service.model.ItemType;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.Orders;

import java.text.SimpleDateFormat;
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
  public NeopixService neopixService;
  public MutableLiveData<OrderDetails> data;

  @Inject
  public OrderRepository(NeopixService neopixService) {
    this.neopixService = neopixService;
  }

  public LiveData<OrderDetails> getOrderDetails(int orderId) {
    data = new MutableLiveData<>();

      RetrofitClient.getInstance().getApi().getOrderDetails(String.valueOf(orderId)).enqueue(new Callback<OrderDetails>() {
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
