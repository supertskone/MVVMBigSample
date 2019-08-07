package com.neopix.test.orders.di;

import com.neopix.test.orders.view.ui.OrderFragment;
import com.neopix.test.orders.view.ui.OrderListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract OrderFragment contributeOrderFragment();

    @ContributesAndroidInjector
    abstract OrderListFragment contributeOrderListFragment();
}
