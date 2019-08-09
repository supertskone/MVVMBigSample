package com.neopix.test.orders.service.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.service.model.OrderDetails;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class OrderRepository {
  public static final String ERROR = "ERROR";
  public MutableLiveData<OrderDetails> data;

  @Inject
  public OrderRepository() {
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
        Log.e(ERROR, t.getLocalizedMessage());
      }
    });
    return data;
  }
}
