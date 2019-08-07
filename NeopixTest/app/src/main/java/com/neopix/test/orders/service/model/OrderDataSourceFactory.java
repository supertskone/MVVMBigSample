package com.neopix.test.orders.service.model;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

public class OrderDataSourceFactory extends DataSource.Factory {

  private MutableLiveData<ItemKeyedDataSource<Integer, Order>> itemLiveDataSource = new MutableLiveData<>();


  @Override
  public DataSource<Integer, Order> create() {
    OrderDataSource itemDataSource = new OrderDataSource();
    itemLiveDataSource.postValue(itemDataSource);
    return itemDataSource;
  }

  public MutableLiveData<ItemKeyedDataSource<Integer, Order>> getItemLiveDataSource() {
    return itemLiveDataSource;
  }
}