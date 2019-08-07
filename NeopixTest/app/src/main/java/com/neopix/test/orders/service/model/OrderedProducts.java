package com.neopix.test.orders.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderedProducts implements Serializable {
  @SerializedName("id")
  public int id;

  @SerializedName("name")
  public String name;

  @SerializedName("price")
  public int price;

  @SerializedName("quantity")
  public int quantity;

  @SerializedName("totalPrice")
  public int totalPrice;
}
