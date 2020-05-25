package com.stucom.socialgamesnetwork;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.callbacks.SgnCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public class HistoryFragment extends Fragment {

    private SgnDAO sgnDAO;
    private IgdbDAO igdbDAO;

    private RecyclerView recyclerView;

    private IgdbCallback igdbCallback;
    private SgnCallback sgnCallback;
    private CustomCallback customCallback;


    private HistoryAdapter adapter;

    int offset = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("History");

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        igdbCallback = new IgdbCallback() {
            @Override
            public void findGenres(Context context, List<Genre> genresAPI) {
            }

            @Override
            public void findGames(Context context, List<Videogame> videogamesAPI, boolean add) {
            }

            @Override
            public void getGame(Context context, Videogame videogame) {

            }

            @Override
            public void getFavouriteGame(Context context, Videogame videogame, TextView txtViewTitle, ImageView imgView, ProgressBar pbRating, TextView tvRating, TextView tvGenres) {
                txtViewTitle.setText(videogame.getName());
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                if (videogame.getGenres() != null) {
                    for (Genre genre : videogame.getGenres()) {
                        stringBuilder.append(genre.getName());
                        if (i < videogame.getGenres().size() - 1) {
                            stringBuilder.append(", ");
                        }
                        i++;
                    }
                }
                tvGenres.setText(stringBuilder.toString());
                String img = "https://images.igdb.com/igdb/image/upload/t_cover_small_2x/" + videogame.getCover().getImageId() + ".jpg";
                Picasso.get().load(img).into(imgView);
                int rating = Integer.valueOf((int) videogame.getRating());
                pbRating.setProgress(rating);
                if (rating == 0) {
                    tvRating.setText("N/A");
                } else {
                    tvRating.setText(String.valueOf(rating));
                }
            }
        };

        sgnCallback = new SgnCallback() {
            @Override
            public void isGameFavourite(Context context, boolean isFavourite) {
            }

            @Override
            public void setListGames(Context context, List<Videogame> videogameList) {
                adapter = new HistoryAdapter(videogameList);
                recyclerView.setAdapter(adapter);
            }
        };

        igdbDAO = new IgdbDAO();
        sgnDAO = new SgnDAO();
        sgnDAO.getHistoryByUser(getContext(), sgnCallback);

        return view;
    }

    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivVideogameImage;
            ProgressBar pbVideogameRating;
            TextView tvVideogameTitle;
            TextView tvVideogameGenre;
            TextView tvVideogameRating;

            ViewHolder(View view) {
                super(view);
                ivVideogameImage = view.findViewById(R.id.ivVideogameImage);
                pbVideogameRating = view.findViewById(R.id.pbVideogameRating);
                tvVideogameTitle = view.findViewById(R.id.tvVideogameTitle);
                tvVideogameGenre = view.findViewById(R.id.tvVideogameGenre);
                tvVideogameRating = view.findViewById(R.id.tvVideogameRating);
            }
        }

        private List<Videogame> history;

        HistoryAdapter(List<Videogame> history) {
            super();
            this.history = history;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videogame_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Videogame videogame = history.get(position);
            igdbDAO.getGameById(getContext(), igdbCallback, videogame.getIdGame(), holder.tvVideogameTitle, holder.ivVideogameImage, holder.pbVideogameRating, holder.tvVideogameRating, holder.tvVideogameGenre);
        }

        @Override
        public int getItemCount() {
            return history.size();
        }
    }

}
