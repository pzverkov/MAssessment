package com.mobiquity.massessment.network;

import com.mobiquity.massessment.network.data.Category;

import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface NetworkApi {
    @GET("/")
    Observable<List<Category>> getData();
}
