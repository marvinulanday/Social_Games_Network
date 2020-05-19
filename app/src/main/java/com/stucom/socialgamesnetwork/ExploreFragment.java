package com.stucom.socialgamesnetwork;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public class ExploreFragment extends Fragment {

    IgdbDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // dao.getGamesByGenre();
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    class VideogameAdapter extends RecyclerView.Adapter<VideogameAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivVideogameImage;
            ImageView ivVideogameRating;
            TextView tvVideogameTitle;
            TextView tvVideogameGenre;

            ViewHolder(View view) {
                super(view);
                ivVideogameImage = view.findViewById(R.id.ivVideogameImage);
                tvVideogameTitle = view.findViewById(R.id.tvVideogameTitle);
                tvVideogameGenre = view.findViewById(R.id.tvVideogameGenre);
                ivVideogameRating = view.findViewById(R.id.ivVideogameRating);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Videogame videogame = videogames.get(position);
                        Fragment newFragment = new RankingFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.fragment_explore, newFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                });
            }
        }

        private List<Videogame> videogames;

        VideogameAdapter(List<Videogame> videogames) {
            super();
            this.videogames = videogames;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("SGN", "onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_videogame_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("SGN", "onBindViewHolder(): " + position);

            Videogame videogame = videogames.get(position);
            holder.tvVideogameTitle.setText(videogame.getName());
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (Genre genre : videogame.getGenres()) {
                stringBuilder.append(genre.getName());
                if (i < videogame.getGenres().size() - 1) {
                    stringBuilder.append(" ,");
                }
                i++;
            }
            holder.tvVideogameGenre.setText(stringBuilder.toString());
            Picasso.get().load(videogame.getImage()).into(holder.ivVideogameImage);
        }

        @Override
        public int getItemCount() {
            return videogames.size();
        }

    }
}
