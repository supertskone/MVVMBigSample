package com.neopix.test.orders.service.model;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;

public class OrderDetails extends LiveData<OrderDetails> {
  @SerializedName("data")
  public Order data;
}
