package com.example.andresserrano.marvelheroes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresserrano.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class HeroGridFragment extends Fragment {

    public static final String HERO_DETAIL_FRAGMENT = "HERO_DETAIL_FRAGMENT";
    public static final String SUPER_HERO = "Super_Hero";
    @BindView(R.id.superHeroesRecyclerView)
    RecyclerView recyclerView;

    private static final String TAG = HeroListFragment.class.getSimpleName();

    ArrayList<SuperHero> superHeroes;


    public HeroGridFragment() {
        // Required empty public constructor
    }

    public interface HeroClickListener{

        void onHeroClicked(SuperHero superHero);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null){

            superHeroes = getArguments().getParcelableArrayList(MainActivity.HERO_LIST);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hero_grid, container, false);
        ButterKnife.bind(this, view);
        //recyclerView = (RecyclerView) view.findViewById(R.id.superHeroesRecyclerView);


         HeroGridAdapter heroGridAdapter = new HeroGridAdapter(superHeroes, getContext(), new HeroGridFragment.HeroClickListener() {
            @Override
            public void onHeroClicked(SuperHero superHero) {
                //Cambiar de fragment

                goToHeroDetailFragment(superHero);

            }
        });


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColums = (int) (dpWidth/200);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numColums));


        recyclerView.setAdapter(heroGridAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void goToHeroDetailFragment(SuperHero superHero) {

        HeroDetailFragment heroDetailFragment = new HeroDetailFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelable(SUPER_HERO, superHero);

        heroDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.placeholder, heroDetailFragment, HERO_DETAIL_FRAGMENT);

        fragmentTransaction.addToBackStack(HERO_DETAIL_FRAGMENT);

        fragmentTransaction.commit();
    }

}
