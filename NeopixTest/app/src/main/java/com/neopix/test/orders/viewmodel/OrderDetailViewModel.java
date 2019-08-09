package com.neopix.test.orders.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.neopix.test.orders.R;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.repository.OrderRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class OrderDetailViewModel extends AndroidViewModel {
  public static final String TAG = OrderDetailViewModel.class.getName();
  public static final MutableLiveData ABSENT = new MutableLiveData();
  private static Context application;

  {
    ABSENT.setValue(null);
  }

  public LiveData orderObservable;
  public MutableLiveData<Integer> orderID;
  public ObservableField<Order> order = new ObservableField<>();

  @Inject
  public OrderDetailViewModel(@NonNull Application application, @NonNull OrderRepository orderRepository) {
    super(application);
    this.application = application;
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

  public String getLogo() {
    return this.order.get().venue.logo;
  }

  @BindingAdapter("imageUrl")
  public static void loadImage(ImageView imageView, String url) {
    Picasso picasso = new Picasso.Builder(application)
      .listener((picasso1, uri, exception) -> exception.getMessage())
      .build();
    picasso.load(url)
      .fit()
      .error(R.drawable.ic_orders_total_ammount)
      .into(imageView);
  }

  public void setOrderID(int orderID) {
    this.orderID.setValue(orderID);
  }
}
