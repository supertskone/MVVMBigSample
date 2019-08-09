package com.neopix.test.orders.view.ui;

import android.os.Bundle;
import android.view.View;

import com.neopix.test.orders.R;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.OrderedProducts;
import com.neopix.test.orders.service.model.Venue;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, OrderFragment.DisplayVenue {

  public OrderDetails orderDetails = new OrderDetails();
  public OnOrderDataReceivedListener mOrderDataListener;
  public Venue venue;
  public ArrayList<OrderedProducts> orderedProducts;

  public void showBottomFragment(View view) {
    FragmentBottomDialog bottomSheetDialog = FragmentBottomDialog.getInstance();
    bottomSheetDialog.displayReceivedData(venue);
    bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet");
  }

  public void closeDetailsFragment(View view) {
    onBackPressed();
  }

  @Override
  public void sendVenue(Venue venue) {
    this.venue = venue;
    FragmentBottomDialog fragmentBottomDialog = new FragmentBottomDialog();
    fragmentBottomDialog.displayReceivedData(venue);
  }

  public void closeBottomFragment(View view) {
    view.getRootView().setVisibility(View.GONE);
  }

  public interface OnOrderDataReceivedListener {
    void onDataReceived(OrderDetails order);
  }

  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Add orderDetails list fragment if this is first creation
    if (savedInstanceState == null) {
      OrderListFragment fragment = new OrderListFragment();

      getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, fragment, OrderListFragment.TAG).commit();
    }
  }

  // Shows the orderDetails detail fragment
  public void show(Order order) {
    this.orderDetails.data = order;

    OrderFragment orderFragment = OrderFragment.forOrder(order.id);

    getSupportFragmentManager()
      .beginTransaction()
      .addToBackStack("orderDetails")
      .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
      .addToBackStack(null)
      .replace(R.id.fragment_container,
        orderFragment, null).commit();
  }

  @Override
  public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
  }
}
