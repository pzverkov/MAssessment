package com.mobiquity.massessment.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mobiquity.massessment.R;
import com.mobiquity.massessment.di.NetworkModule;
import com.mobiquity.massessment.network.data.Product;
import com.mobiquity.massessment.ui.GlideApp;
import java.util.List;

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    private List<Product> products;
    private final View.OnClickListener onClickListener;

    ProductsAdapter(List<Product> products, View.OnClickListener onClickListener) {
        this.products = products;
        this.onClickListener = onClickListener;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_holder, parent, false);

        v.setOnClickListener(onClickListener);

        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.textView.setText(product.getName());
        Context context = holder.textView.getContext();

        GlideApp.with(context)
                .load(NetworkModule.URL + product.getUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new Target<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        holder.textView.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, errorDrawable, null);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.textView.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, resource, null);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
    }

    public Product getProduct(int itemPosition) {
        return products.get(itemPosition);
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        public TextView textView;

        public ProductHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
