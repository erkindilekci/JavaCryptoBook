package com.erkindilekci.simplecryptobook.service;

import com.erkindilekci.simplecryptobook.model.Crypto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface CryptoApi {

    @GET("erkindil/Json/main/newcryptolist.json")
    Observable<List<Crypto>> getAllCryptos();
}
