package com.stucom.socialgamesnetwork.profileFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.callbacks.SgnCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;
import com.stucom.socialgamesnetwork.videogamesDetailsFragments.VideogameDetailsFragment;

import java.util.List;


public class ProfileVideogameListFragment extends Fragment {

    private SgnDAO sgnDAO;
    private IgdbDAO igdbDAO;

    private IgdbCallback igdbCallback;
    private SgnCallback sgnCallback;
    private CustomCallback customCallback;

    private List<Videogame> favourites;

    private FavouriteAdapter adapter;
    private RecyclerView recyclerView;
    private int positionDelete;

    private TextView txtNotFound;

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile_videogame_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFav);
        txtNotFound = view.findViewById(R.id.txtView);


        Bundle bundle = getArguments();
        customCallback = (CustomCallback) bundle.getSerializable("callback");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                positionDelete = viewHolder.getAdapterPosition();
                sgnDAO.deleteFavouriteVideogame(getContext(), sgnCallback, favourites.get(positionDelete).getIdGame());

            }
        };

        igdbDAO = new IgdbDAO();
        sgnDAO = new SgnDAO();
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
            public void getFavouriteGame(Context context, Videogame videogame, TextView tvTitle, ImageView ivImg, ProgressBar pbRating, TextView tvRating, TextView tvGenres) {
                tvTitle.setText(videogame.getName());
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
                Picasso.get().load(img).into(ivImg);
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
                if (!isFavourite) {
                    favourites.remove(positionDelete);
                    adapter.notifyDataSetChanged();
                    if (favourites.isEmpty()) {
                        txtNotFound.setVisibility(View.VISIBLE);
                        txtNotFound.setEnabled(true);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView.setEnabled(false);
                    }
                }
            }

            @Override
            public void setListGames(Context context, List<Videogame> videogameList) {
                if (videogameList == null) {
                    txtNotFound.setVisibility(View.VISIBLE);
                    txtNotFound.setEnabled(true);
                    recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView.setEnabled(false);
                } else {
                    favourites = videogameList;
                    adapter = new FavouriteAdapter(favourites);
                    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
                    recyclerView.setAdapter(adapter);
                }
            }
        };

        sgnDAO.selectFavourites(getContext(), sgnCallback);

        return view;
    }

    class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
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
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Videogame videogame = favourites.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putInt("videogame", videogame.getIdGame());
                        VideogameDetailsFragment detail = new VideogameDetailsFragment();
                        detail.setArguments(bundle);
                        customCallback.accessFragment(R.id.fragment_container, detail);
                    }
                });
            }
        }

        private List<Videogame> favourites;

        FavouriteAdapter(List<Videogame> favourites) {
            super();
            this.favourites = favourites;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videogame_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Videogame videogame = favourites.get(position);
            igdbDAO.getGameById(getContext(), igdbCallback, videogame.getIdGame(), holder.tvVideogameTitle, holder.ivVideogameImage, holder.pbVideogameRating, holder.tvVideogameRating, holder.tvVideogameGenre);
        }

        @Override
        public int getItemCount() {
            return favourites.size();
        }
    }

}

