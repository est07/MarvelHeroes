package com.example.andresserrano.marvelheroes.Api;

import com.example.andresserrano.marvelheroes.Models.Basic;
import com.example.andresserrano.marvelheroes.Models.Data;
import com.example.andresserrano.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Esteban Serrano on 13/05/2017.
 */

public interface Marvel {

    String BASE_URL = "https://gateway.marvel.com/";

    String API_KEY_KEY = "apikey";
    String API_KEY_VALUE = "ef2a724aba13278f85950382cf8a6baf";

    String TIME_STAMP_KEY = "ts";
    String TIME_STAMP_VALUE = "1";

    String HASH_KEY = "hash";
    String HASH_VALUE = "317dfc7e823c9ee5e9f6148254fbdd34";


    @GET("v1/public/series/{seriesId}/characters")
    Call<Basic<Data<ArrayList<SuperHero>>>> getHeroes(@Path("seriesId") int seriesId);

}
