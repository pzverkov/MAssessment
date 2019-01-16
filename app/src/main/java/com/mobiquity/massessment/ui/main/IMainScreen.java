package com.mobiquity.massessment.ui.main;

import com.mobiquity.massessment.network.data.Category;
import com.mobiquity.massessment.ui.detail.ProductModel;

import java.util.List;

public interface IMainScreen {
    void setData(int selected, List<Category> products);
    void showProductDetail(ProductModel model);
    void showError(String message);
}
