package com.neopix.test.orders.view.callback;

import com.neopix.test.orders.service.model.Order;

public interface OrderClickCallback {
    void onClick(Order order);
}
