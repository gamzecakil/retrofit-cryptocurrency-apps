package com.examples.cryptocurrencyapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.examples.cryptocurrencyapp.adapter.RecyclerViewAdapter;
import com.examples.cryptocurrencyapp.model.CryptoModel;
import com.examples.cryptocurrencyapp.R;
import com.examples.cryptocurrencyapp.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //API -->Application Programming Interface  :Sunucu ve uygulamamız arasında iletişim sağlarlar.
    //Sunucuya veri gonderme ve sunucudan veri çekme işlemleri yapabiliriz.
    //API verileri genellikle JSON formatında verir.
    //JSON JavaScript Object Notation
    //Retrofit bir kütüphanedir.Arka plan işlerini asenkron olarak gerçekleştirmemizi sağlar..AsynTask'ın  gelişmiş halidir.FrameWork olarak da adlandırabiliriz.
    //RxJava Reactive Extensions

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL="https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);

        //https://api.nomics.com/v1/prices?key=2187154b76945f2373394aa34f7dc98a
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        //Retrofit & JSON

        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder().
                baseUrl("https://api.nomics.com/v1/").addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();

    }
    private void loadData(){
        CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);
        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())//hangi thread gözlemlenecek
                .observeOn(AndroidSchedulers.mainThread())//Sonuç hangi thread de gösterilecek
                .subscribe(this::handleResponse));//sonucunda çıkan durumu nerde ele alacağız.(Hangi method da)

        /*

        Call<List<CryptoModel>> call=cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> responseList=response.body();
                    cryptoModels=new ArrayList<>(responseList);//verileri cryptoModels aldık

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));//veriler recyclerview nasıl gösterilecek
                    recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                     for(CryptoModel cryptoModel:cryptoModels){
                        System.out.println(cryptoModel.price);
                        System.out.println(cryptoModel.currency);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                System.out.println("Hata var");
              t.printStackTrace();
            }
        });//asenkron olarak verileri çekmemi sağlar

         */
    }
    private void handleResponse(List<CryptoModel> cryptoModelList){

        cryptoModels=new ArrayList<>(cryptoModelList);//verileri cryptoModels aldık

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));//veriler recyclerview nasıl gösterilecek
        recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}