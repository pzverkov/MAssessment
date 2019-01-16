package com.mobiquity.massessment.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;

import com.mobiquity.massessment.App;
import com.mobiquity.massessment.R;
import com.mobiquity.massessment.network.data.Category;
import com.mobiquity.massessment.network.data.Product;
import com.mobiquity.massessment.ui.ScreenSlidePagerAdapter;
import com.mobiquity.massessment.ui.detail.ProductDetailsActivity;
import com.mobiquity.massessment.ui.detail.ProductModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements IMainScreen, ProductListFragment.ProductClickListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    MainPresenter presenter;

    private ViewPager viewPager;
    private ScreenSlidePagerAdapter slidePagerAdapter;
    private List<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        ((App) getApplication()).getComponent().inject(this);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        categories = new ArrayList<>();
        slidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), categories);
        viewPager.setAdapter(slidePagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getSupportActionBar().setTitle(slidePagerAdapter.getCategoryAtPosition(i).getDescription());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.handleRefresh());
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(int selected, List<Category> categories) {
        slidePagerAdapter.setCategories(categories);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProductDetail(ProductModel model) {
        ProductDetailsActivity.startActivity(this, model);
    }

    @Override
    public void onClick(Product product) {
        presenter.handleProductSelection(product);
    }
}