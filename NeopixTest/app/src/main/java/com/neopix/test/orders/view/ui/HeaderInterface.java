package com.neopix.test.orders.view.ui;

import android.view.View;

public interface HeaderInterface {

    /**
     * This method gets called by {@link HeaderItemDecoration} to fetch the position of the header item in the adapter
     * that is used for (represents) item at specified position.
     * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
     * @return int. Position of the header item in the adapter.
     */
    int getHeaderPositionForItem(int itemPosition);

    /**
     * This method gets called by {@link HeaderItemDecoration} to get layout resource id for the header item at specified adapter's position.
     * @param headerPosition int. Position of the header item in the adapter.
     * @return int. Layout resource id.
     */
    int getHeaderLayout(int headerPosition);

    /**
     * This method gets called by {@link HeaderItemDecoration} to setup the header View.
     * @param header View. Header to set the data on.
     * @param headerPosition int. Position of the header item in the adapter.
     */
    void bindHeaderData(View header, int headerPosition);

    /**
     * This method gets called by {@link HeaderItemDecoration} to verify whether the item represents a header.
     * @param itemPosition int.
     * @return true, if item at the specified adapter's position represents a header.
     */
    boolean isHeader(int itemPosition);
}