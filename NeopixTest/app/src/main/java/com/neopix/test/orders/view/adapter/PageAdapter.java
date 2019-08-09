package com.neopix.test.orders.view.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.MutableLiveData;

import com.neopix.test.orders.service.model.Notes;
import com.neopix.test.orders.service.model.OrderedProducts;
import com.neopix.test.orders.view.ui.IncludedProductsFragment;
import com.neopix.test.orders.view.ui.NotesFragment;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {

  private int numOfTabs;
  private MutableLiveData<ArrayList<OrderedProducts>> orderedProducts = new MutableLiveData<>();
  private MutableLiveData<ArrayList<Notes>> notes = new MutableLiveData<>();


  public PageAdapter(FragmentManager fm, int numOfTabs) {
    super(fm);
    this.numOfTabs = numOfTabs;
  }

  public void setOrderedProducts(ArrayList<OrderedProducts> orderedProducts) {
    this.orderedProducts.setValue(orderedProducts);
  }

  public void setNotes(ArrayList<Notes> notes) {
    this.notes.setValue(notes);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new IncludedProductsFragment(this.orderedProducts.getValue());
      case 1:
        return new NotesFragment(this.notes.getValue());
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return numOfTabs;
  }
}
