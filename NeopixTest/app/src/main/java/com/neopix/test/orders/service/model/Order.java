package com.neopix.test.orders.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

  @SerializedName("id")
  public int id;

  public Integer type;

  @SerializedName("orderNumber")
  public String orderNumber;

  @SerializedName("orderedAt")
  public Date orderedAt;

  @SerializedName("acceptedAt")
  public Date acceptedAt;

  @SerializedName("declinedAt")
  public Date declinedAt;

  @SerializedName("status")
  public String status;

  @SerializedName("venue")
  public Venue venue;

  @SerializedName("amount")
  public int amount;

  @SerializedName("orderedProducts")
  @Expose
   public ArrayList<OrderedProducts> orderedProducts;

  @SerializedName("totalAmount")
  public int totalAmount;

  @SerializedName("notes")
  @Expose
  public ArrayList<Notes> notes;

  public Order() {
  }

  public Order(Date orderedAt) {
    this.orderedAt = orderedAt;
  }

  public Order(int id, Date orderedAt) {
    this.id = id;
    this.orderedAt = orderedAt;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
