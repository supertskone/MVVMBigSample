/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neopix.test.orders.data;

import com.neopix.test.orders.service.model.ItemType;
import com.neopix.test.orders.service.model.Notes;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.Venue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeRandomOrderGeneratorAPI {

  private static final int ORDER_ID_TEST = 11;
  private static final String ORDER_ORDER_NUMBER_TEST = "11";
  private static final String ORDER_ADDRESS_TEST = "164, Kallang Way, 02-16/17 349248, Singapore";
  private static final String ORDER_LOGO_TEST = "http://mobile-test.neopixdev.com.s3-website.eu-central-1.amazonaws.com/images/sakura.png";
  private static final String ORDER_STATUS_TEST = "accepted";
  private static final int ORDER_AMOUNT_TEST = 12345;
  private static final String VENUE_NAME_TEST = "Sakura Sushi bar";

  public static List<Order> getOrderList() {
    List<Order> orders = new ArrayList<Order>() {
    };
    for (int i = 0; i < 10; i++) {
      orders.add(getOrder());
    }
    return orders;
  }

  public static Order getOrder() {
    Order order = new Order();
    order.venue = new Venue(ORDER_ID_TEST, VENUE_NAME_TEST, ORDER_LOGO_TEST, ORDER_ADDRESS_TEST);
    order.id = ORDER_ID_TEST;
    order.orderedProducts = new ArrayList<>();
    order.acceptedAt = new Date();
    order.declinedAt = new Date();
    order.amount = ORDER_AMOUNT_TEST;
    order.notes = new ArrayList<Notes>();
    order.status = ORDER_STATUS_TEST;
    order.totalAmount = ORDER_AMOUNT_TEST;
    order.type = ItemType.Post;
    order.orderNumber = ORDER_ORDER_NUMBER_TEST;
    return order;
  }
}
