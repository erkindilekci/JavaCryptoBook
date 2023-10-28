package com.erkindilekci.simplecryptobook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erkindilekci.simplecryptobook.adapter.CryptoAdapter;
import com.erkindilekci.simplecryptobook.databinding.ActivityMainBinding;
import com.erkindilekci.simplecryptobook.model.Crypto;
import com.erkindilekci.simplecryptobook.service.CryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Crypto> cryptoList;
    private final String BASE_URL = "https://raw.githubusercontent.com/";
    private Retrofit retrofit;

    private CryptoAdapter cryptoAdapter;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cryptoList = new ArrayList<>();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cryptoAdapter = new CryptoAdapter(cryptoList);
        binding.recyclerView.setAdapter(cryptoAdapter);
    }

    private void getData() {
        CryptoApi cryptoApi = retrofit.create(CryptoApi.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoApi.getAllCryptos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    cryptoList.addAll(list);
                    cryptoAdapter.notifyDataSetChanged();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
