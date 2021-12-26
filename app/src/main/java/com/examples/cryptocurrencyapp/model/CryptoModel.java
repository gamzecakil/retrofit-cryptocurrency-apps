package com.examples.cryptocurrencyapp.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {
    @SerializedName("currency")//JSON dosyasındaki değişken ismi(currency) ile aynı olmalı--> ama bu classdaki filed ları farklı verebilirim
    public String currency;
    @SerializedName("price")//JSON dosyasındaki değişken isimleri(price) ile aynı olmalı
    public String price;
}
