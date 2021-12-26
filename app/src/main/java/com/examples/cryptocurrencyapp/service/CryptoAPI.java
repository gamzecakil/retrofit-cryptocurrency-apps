package com.examples.cryptocurrencyapp.service;

import com.examples.cryptocurrencyapp.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    //GET,POST,UPDATE,DELETE

    //URL BASE -> www.website.com
    // GET -> price?key=xxx

    //https://api.nomics.com/v1/-->Base URL
    //prices?key=2187154b76945f2373394aa34f7dc98a
    //https://raw.githubusercontent.com/-->Base URL
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("prices?key=2187154b76945f2373394aa34f7dc98a")
    //Call<List<CryptoModel>> getData();
    Observable<List<CryptoModel>> getData();//veri setinde bir değişiklik olduğunda bu değişikliği gözlemleyen objelere bildirecek.
}
