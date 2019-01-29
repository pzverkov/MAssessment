package com.massessment.ui.main;

import android.os.Build;

import com.massessment.di.NetworkModule;
import com.massessment.network.ConnectivityHelper;
import com.massessment.network.NetworkApi;
import com.massessment.network.data.Category;
import com.massessment.network.data.Product;
import com.massessment.ui.detail.ProductModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class MainPresenterImpl implements MainPresenter {

    private final CompositeDisposable subscription = new CompositeDisposable();
    private final NetworkApi api;
    private final ConnectivityHelper helper;

    private IMainScreen view;
    private List<Category> cachedCategories;
    private int selectedCategory;

    public MainPresenterImpl(NetworkApi api, ConnectivityHelper helper) {
        this.api = api;
        this.helper = helper;
    }

    @Override
    public void onAttach(IMainScreen view) {
        this.view = view;
        updateView();
    }

    private void updateView() {
        if (cachedCategories == null) {
            refreshData();
        } else {
            view.setData(selectedCategory, cachedCategories);
        }
    }

    @Override
    public void onDetach() {
        view = null;
        subscription.clear();
    }

    @Override
    public void handleCategorySelection(int position) {
        selectedCategory = position;
    }

    @Override
    public void handleRefresh() {
        refreshData();
    }

    @Override
    public void handleProductSelection(Product product) {
        view.showProductDetail(new ProductModel.Builder()
                .setName(product.getName())
                .setUrl(product.getUrl())
                .setSalePrice(product.getSalePrice())
                .build());
    }

    private void refreshData() {
        subscription.add(
                api.getData()
                        .subscribeOn(Schedulers.io())
                        .doOnNext(category -> patchUrlField(category))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> handleSuccess(categories),
                                this::handleError)
        );
    }

    private void patchUrlField(List<Category> categories) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categories.forEach(category -> category.getProducts().forEach(this::updateUrl));
        } else {
            //TODO: implementation
        }
    }

    private void updateUrl(Product product) {
        String fullUrl = getFullUrl(NetworkModule.URL, product.getUrl());
        product.setUrl(fullUrl);
    }

    private void handleError(Throwable error) {
        error.printStackTrace();

        if (view == null) {
            return;
        }

        if (helper.isNetworkAvailable()) {
            view.showError("Server is not available");
        } else {
            view.showError("No internet");
        }
    }

    private void handleSuccess(List<Category> categories) {
        cachedCategories = Collections.emptyList();

        if (categories == null || categories.isEmpty()) {
            return;
        }
        cachedCategories = categories;
        if (selectedCategory >= cachedCategories.size()) {
            selectedCategory = 0;
        }
        if (view == null) {
            return;
        }
        view.setData(selectedCategory, cachedCategories);
    }

    public static String getFullUrl(String mBaseUrl, String file) {
        URL url1;
        try {
            url1 = new URL(mBaseUrl);
            URL url2 = new URL(url1.getProtocol(), url1.getHost(), url1.getPort(), file, null);
            return url2.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
