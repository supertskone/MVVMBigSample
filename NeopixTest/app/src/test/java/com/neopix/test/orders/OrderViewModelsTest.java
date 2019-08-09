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

package com.neopix.test.orders;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.data.FakeRandomOrderGeneratorAPI;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.Venue;
import com.neopix.test.orders.service.repository.NeopixService;
import com.neopix.test.orders.service.repository.OrderRepository;
import com.neopix.test.orders.viewmodel.OrderDetailViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderViewModelsTest {

  private static final int ORDER_ID_TEST = 11;
  private static final String ORDER_ORDER_NUMBER_TEST = "11";
  private static final String ORDER_ADDRESS_TEST = "164, Kallang Way, 02-16/17 349248, Singapore";
  private static final String ORDER_LOGO_TEST = "http://mobile-test.neopixdev.com.s3-website.eu-central-1.amazonaws.com/images/sakura.png";
  private static final String ORDER_STATUS_TEST = "accepted";
  private static final int ORDER_AMOUNT_TEST = 12345;
  private static final String VENUE_NAME_TEST = "Sakura Sushi bar";


  private OrderRepository orderRepository;
  private Context mockContext = mock(Context.class);
  private Application application = mock(Application.class);

  @Mock
  private NeopixService neopixService;

  @Mock
  private OrderDetailViewModel orderDetailViewModel;

  @Before
  public void setUpMainViewModelTest() {
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.data = FakeRandomOrderGeneratorAPI.getOrder();

    orderRepository = new OrderRepository();
    orderRepository.data = new MutableLiveData<>();
    orderDetailViewModel.setOrder(FakeRandomOrderGeneratorAPI.getOrder());
  }

  @Test
  public void shouldGetOrderId() throws Exception {
    List<Order> orderList = FakeRandomOrderGeneratorAPI.getOrderList();
    assertNotNull(orderList);
    assertEquals(ORDER_ID_TEST, orderList.get(0).id);
  }

  @Test
  public void shouldGetOrderMail() throws Exception {
    Order order = new Order();
    order.orderNumber = ORDER_ORDER_NUMBER_TEST;
    List<Order> orderList = FakeRandomOrderGeneratorAPI.getOrderList();
    assertEquals(order.orderNumber, orderList.get(0).orderNumber);
  }

  @Test
  public void shouldGetOrderPicture() throws Exception {
    Order order = new Order();
    order.venue = new Venue(1, "", ORDER_LOGO_TEST, "");
    List<Order> orderList = FakeRandomOrderGeneratorAPI.getOrderList();
    assertEquals(order.venue.logo, orderList.get(0).venue.logo);
  }
}
