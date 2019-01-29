package com.massessment.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.massessment.R;
import com.massessment.network.data.Category;
import com.massessment.network.data.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    public static final String CATEGORY = "category";
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.no_data_text)
    TextView noDataTextView;

    private ProductsAdapter adapter;
    private ProductClickListener productClickListener;
    private Category category;

    public static ProductListFragment getInstance(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, new Gson().toJson(category));
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CATEGORY, new Gson().toJson(category));
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        String categoryString = null;
        if (savedInstanceState != null) {
            categoryString = savedInstanceState.getString(CATEGORY);
        } else {
            categoryString = getArguments().getString(CATEGORY);
        }

        category = new Gson().fromJson(categoryString, Category.class);


        View rootView = inflater.inflate(R.layout.product_list, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new ProductsAdapter(new ArrayList<>(), new RecyclerViewOnClickListener());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        setProducts(category.getProducts());

        productClickListener = ((ProductClickListener)getActivity());

        return rootView;
    }

    public void setProducts(List<Product> products) {
        adapter.setProducts(products);
        adapter.notifyDataSetChanged();

        noDataTextView.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private class RecyclerViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            Product product = adapter.getProduct(itemPosition);
            productClickListener.onClick(product);
        }
    }

    interface ProductClickListener {
        void onClick(Product product);
    }
}
