package com.example.andresserrano.marvelheroes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresserrano.marvelheroes.Models.SuperHero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Esteban Serrano on 13/05/2017.
 */

public class HeroListAdapter extends RecyclerView.Adapter<HeroListAdapter.MyViewHolder>{

    ArrayList<SuperHero> superHeroArrayList;
    Context context;

    HeroListFragment.HeroClickListener heroClickListener;

    public HeroListAdapter(ArrayList superHeroArrayList, Context context, HeroListFragment.HeroClickListener heroClickListener){

        this.superHeroArrayList = superHeroArrayList;
        this.context = context;
        this.heroClickListener = heroClickListener;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.hero_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SuperHero superHero = superHeroArrayList.get(position);

        holder.bind(context, superHero, heroClickListener);

    }

    @Override
    public int getItemCount() {
        return superHeroArrayList.size();
    }

    static public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView heroPictureImageView;
        public TextView heroDetailNameTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            heroPictureImageView = (ImageView) itemView.findViewById(R.id.heroPictureImageView);
            heroDetailNameTextView = (TextView) itemView.findViewById(R.id.heroDetailNameTextView);

        }

        public void bind(Context context, final SuperHero superHero, final HeroListFragment.HeroClickListener heroClickListener){

            heroDetailNameTextView.setText(superHero.getName());
            Picasso.with(context).load(superHero.getThumbnail().getFullPath()).into(heroPictureImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heroClickListener.onHeroClicked(superHero);
                }
            });

        }
    }

}
