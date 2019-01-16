package com.mobiquity.massessment.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mobiquity.massessment.R;

import static com.mobiquity.massessment.ui.detail.ProductDetailsActivityFragment.PARAM_MODEL;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void startActivity(Context context, ProductModel model) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(PARAM_MODEL, model);
        context.startActivity(intent);
    }
}
