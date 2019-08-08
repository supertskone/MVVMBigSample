package com.neopix.test.orders;


import android.app.Application;
import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.constraintlayout.utils.widget.MockView;
import androidx.lifecycle.LiveData;

import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.Venue;
import com.neopix.test.orders.service.repository.NeopixService;
import com.neopix.test.orders.service.repository.OrderRepository;
import com.neopix.test.orders.view.ui.MainActivity;
import com.neopix.test.orders.viewmodel.OrderViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class OrdersViewModelTest {

  @Mock
  NeopixService api;
  OrderRepository repository;
  @Mock
  OrderViewModel orderViewModel;
  private MainActivity activity;
  private MockView view;
  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    activity = Mockito.spy(new MainActivity());
    activity.orderDetails = Mockito.mock(OrderDetails.class);
    api = Mockito.mock(NeopixService.class);
    orderViewModel = new OrderViewModel(new Application(), repository);
  }

  @Test
  public void testSetOrder() {
    Date date = new Date();
    orderViewModel.setOrder(new Order(date));
    assertEquals(orderViewModel.order.get().orderedAt, date);
  }

  @Test
  public void testFetchSearchData_whenReturnsData() {
    repository = mock(OrderRepository.class);
    repository.neopixService = mock(NeopixService.class);
    LiveData<OrderDetails> orderDetails = mock(OrderDetails.class);
    Mockito.doReturn(orderDetails).when(repository).getOrderDetails(1);
    Assert.assertEquals(orderDetails, repository.getOrderDetails(1));
  }

  @PrepareForTest(Bundle.class)
  @Test
  public void testMainActivity() {
    activity.sendVenue(new Venue());
    activity.sendOrderedProducts(new ArrayList<>());
  }
}
