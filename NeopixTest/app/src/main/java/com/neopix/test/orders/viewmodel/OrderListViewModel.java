package com.neopix.test.orders.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.neopix.test.orders.R;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDataSource;
import com.neopix.test.orders.service.model.OrderDataSourceFactory;
import com.neopix.test.orders.service.model.Orders;
import com.neopix.test.orders.service.repository.OrderRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class OrderListViewModel extends AndroidViewModel {

  private static Context application;
  public LiveData<PagedList<Order>> itemPagedList;
  public LiveData<ItemKeyedDataSource<Integer, Order>> liveDataSource;

  @Inject
  public OrderListViewModel(@NonNull Application application) {
    super(application);
    this.application = application;

    OrderDataSourceFactory itemDataSourceFactory = new OrderDataSourceFactory();
    liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

    PagedList.Config pagedListConfig =
      (new PagedList.Config.Builder())
        .setEnablePlaceholders(false)
        .setPrefetchDistance(10)
        .setPageSize(OrderDataSource.PAGE_SIZE).build();

    itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
      .build();
  }

  @BindingAdapter("logoUrl")
  public static void loadImage(ImageView imageView, String url) {
    Picasso picasso = new Picasso.Builder(application)
      .listener((picasso1, uri, exception) -> exception.getMessage())
      .build();
    picasso.load(url)
      .fit()
      .error(R.drawable.ic_orders_total_ammount)
      .into(imageView);
  }
}
