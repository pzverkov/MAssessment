package com.massessment.network;

import com.massessment.network.data.Category;

import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface NetworkApi {
    @GET("/")
    Observable<List<Category>> getData();
}
