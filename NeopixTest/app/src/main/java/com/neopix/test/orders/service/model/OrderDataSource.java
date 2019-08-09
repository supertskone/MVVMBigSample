package com.neopix.test.orders.service.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.neopix.test.orders.service.repository.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDataSource extends ItemKeyedDataSource<Integer, Order> {

  public static final int PAGE_SIZE = 10;
  private static int FIRST_PAGE = 1;
  private int NEXT_ID = 15;
  public static final String ERROR = "ERROR";

  @Inject
  public OrderDataSource() {
  }

  @Override
  public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Order> callback) {
    NEXT_ID = getNextId();

    // Idea is to find the number of the elements in Orders list, and if it's e.g. 15, to start with this as value of FIRST_PAGE:

    if (NEXT_ID == 0) {
      FIRST_PAGE = 15;
    } else {
      FIRST_PAGE = NEXT_ID;
    }

    RetrofitClient.getInstance().getApi().getOrderList(FIRST_PAGE, String.valueOf(PAGE_SIZE)).enqueue(new Callback<Orders>() {
      @Override
      public void onResponse(Call<Orders> call, Response<Orders> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            Orders newResponse = addOrUpdateOrders(response);
            callback.onResult(newResponse.data);
          }
        }

      }

      @Override
      public void onFailure(Call<Orders> call, Throwable t) {
        Log.e(ERROR, t.getLocalizedMessage());
      }
    });
  }

  @Override
  public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Order> callback) {
    if (NEXT_ID - PAGE_SIZE > 0) {
      NEXT_ID = NEXT_ID - PAGE_SIZE;

      RetrofitClient.getInstance().getApi().getOrderList(NEXT_ID, String.valueOf(PAGE_SIZE)).enqueue(new Callback<Orders>() {
        @Override
        public void onResponse(Call<Orders> call, Response<Orders> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              Orders newResponse = addOrUpdateOrders(response);
              callback.onResult(newResponse.data);
            }
          }
        }

        @Override
        public void onFailure(Call<Orders> call, Throwable t) {
          Log.e(ERROR, t.getLocalizedMessage());
        }
      });
    }
  }

  @Override
  public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Order> callback) {
  }


  private int getNextId() {
    RetrofitClient.getInstance().getApi().getOrderList().enqueue(new Callback<Orders>() {
      @Override
      public void onResponse(Call<Orders> call, Response<Orders> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            NEXT_ID = response.body().meta.totalCount;
          }
        }
      }

      @Override
      public void onFailure(Call<Orders> call, Throwable t) {
        Log.e(ERROR, t.getLocalizedMessage());
      }
    });
    return NEXT_ID;
  }


  @NonNull
  @Override
  public Integer getKey(@NonNull Order item) {
    return NEXT_ID - PAGE_SIZE;
  }

  private Orders addOrUpdateOrders(Response<Orders> response) {
    Date date = new Date();
    List<Order> orderList = response.body().data;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");

    for (int i = 0; i < orderList.size(); i++) {

      String monthFromAPI = formatter.format(new Date(orderList.get(i).orderedAt.getTime()));
      String month = formatter.format(new Date(date.getTime()));

      if (!month.equals(monthFromAPI)) {
        date = orderList.get(i).orderedAt;
        Order newOrder = new Order(date);
        newOrder.setType(ItemType.Header);
        orderList.add(i, newOrder);
      } else {
        orderList.get(i).setType(ItemType.Post);
      }
    }

    Orders orders = new Orders();

    // Duplicated it to have more data for testing purposes
    // orderList.addAll(orderList);

    orders.data = orderList;

    return orders;
  }
}
