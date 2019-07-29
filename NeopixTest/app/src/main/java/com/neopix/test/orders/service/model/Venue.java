package com.neopix.test.orders.service.model;

import com.google.gson.annotations.SerializedName;

public class Venue {
  @SerializedName("id")
  public int id;

  @SerializedName("name")
  public String name;

  @SerializedName("logo")
  public String logo;

  @SerializedName("address")
  public String address;

  @SerializedName("primaryContactName")
  public String primaryContactName;

  @SerializedName("primaryContactPhone")
  public String primaryContactPhone;

  @SerializedName("secondaryContactName")
  public String secondaryContactName;

  @SerializedName("secondaryContactPhone")
  public String secondaryContactPhone;

}
