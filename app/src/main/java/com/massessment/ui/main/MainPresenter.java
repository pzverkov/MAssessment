package com.massessment.ui.main;


import com.massessment.network.data.Product;

public interface MainPresenter  {

    void onAttach(IMainScreen view);

    void onDetach();

    void handleCategorySelection(int position);

    void handleRefresh();

    void handleProductSelection(Product product);
}
