package com.neopix.test.orders.di;

import com.neopix.test.orders.viewmodel.OrderListViewModel;
import com.neopix.test.orders.viewmodel.OrderDetailViewModel;
import com.neopix.test.orders.viewmodel.OrderViewModelFactory;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link OrderViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    OrderListViewModel orderListViewModel();
    OrderDetailViewModel orderViewModel();
}
