package com.neopix.test.orders.view.ui;

import android.os.Bundle;
import android.view.View;

import com.neopix.test.orders.R;
import com.neopix.test.orders.service.model.Order;
import com.neopix.test.orders.service.model.OrderDetails;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  public OrderDetails order = new OrderDetails();
  public OnOrderDataReceivedListener mOrderDataListener;

    public void showBottomFragment(View view) {
      FragmentBottomDialog bottomSheetDialog = FragmentBottomDialog.getInstance();
      bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
    }

    public void closeDetailsFragment(View view) {
      onBackPressed();
    }

    public interface OnOrderDataReceivedListener {
    void onDataReceived(OrderDetails order);
  }

  public void setOrderDataListener(OnOrderDataReceivedListener listener) {
    this.mOrderDataListener = listener;
  }

  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Add order list fragment if this is first creation
    if (savedInstanceState == null) {
      OrderListFragment fragment = new OrderListFragment();

      getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, fragment, OrderListFragment.TAG).commit();
    }
  }

   // Shows the order detail fragment
  public void show(Order order) {
    this.order.data = order;
    OrderFragment orderFragment = OrderFragment.forOrder(order.id);

    getSupportFragmentManager()
      .beginTransaction()
      .addToBackStack("order")
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
