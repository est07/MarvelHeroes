package com.example.andresserrano.marvelheroes;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresserrano.marvelheroes.Api.MarvelService;
import com.example.andresserrano.marvelheroes.Models.Basic;
import com.example.andresserrano.marvelheroes.Models.Data;
import com.example.andresserrano.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String HERO_LIST_FRAGMENT = "hero_list_fragment";

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int SUCCESS_CODE = 200;
    public static final String HERO_LIST = "hero_list" ;
    public static final String HERO_GRID_FRAGMENT = "HERO_GRID_FRAGMENT";

    private FrameLayout frameLayout;
    private ArrayList<SuperHero> superHeros;

    public static final int AVENGERS_COMIC_ID = 354;

    @BindBool(R.bool.is_tablet) boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.placeholder);
        getHeroList();

    }



    private void getHeroList(){


        Call<Basic<Data<ArrayList<SuperHero>>>> superHeroesCall = MarvelService.getMarvelApi().getHeroes(AVENGERS_COMIC_ID);
        superHeroesCall.enqueue(new Callback<Basic<Data<ArrayList<SuperHero>>>>() {
            @Override
            public void onResponse(Call<Basic<Data<ArrayList<SuperHero>>>> call, Response<Basic<Data<ArrayList<SuperHero>>>> response) {

                if (response.code() == SUCCESS_CODE) {

                    superHeros = response.body().getData().getResults();
                    //Toast.makeText(MainActivity.this, "Hero Name: " + superHeros.get(0).getName(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(HERO_LIST, superHeros);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

                    if (isTablet){

                        HeroGridFragment savedFragment = (HeroGridFragment) fragmentManager.findFragmentByTag(HERO_GRID_FRAGMENT);

                        if (savedFragment == null){

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            HeroGridFragment heroGridFragment = new HeroGridFragment();
                            heroGridFragment.setArguments(bundle);
                            fragmentTransaction.add(R.id.placeholder, heroGridFragment, HERO_GRID_FRAGMENT);
                            fragmentTransaction.commit();

                        }
                        
                    }else{

                        Toast.makeText(MainActivity.this, "Este es un telefono", Toast.LENGTH_SHORT).show();

                        HeroListFragment savedFragment = (HeroListFragment) fragmentManager.findFragmentByTag(HERO_LIST_FRAGMENT);

                        if (savedFragment == null){

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            HeroListFragment heroListFragment = new HeroListFragment();
                            heroListFragment.setArguments(bundle);
                            fragmentTransaction.add(R.id.placeholder, heroListFragment, HERO_LIST_FRAGMENT);
                            fragmentTransaction.commit();

                        }

                    }





                }else {

                    displayErrorMessage(getString(R.string.service_error_message));

                }
            }

            @Override
            public void onFailure(Call<Basic<Data<ArrayList<SuperHero>>>> call, Throwable t) {

                displayErrorMessage(getString(R.string.network_error_message));

            }
        });


    }


    public void displayErrorMessage(String mensaje){

        Snackbar snackbar = Snackbar.make(frameLayout,mensaje , Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getHeroList();
                    }
                });

        snackbar.show();

    }

}




