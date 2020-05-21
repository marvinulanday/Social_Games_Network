package com.stucom.socialgamesnetwork;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stucom.socialgamesnetwork.CustomExpandableListView.ExpandableListAdapter;
import com.stucom.socialgamesnetwork.DAO.IgdbDAO;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExploreFragment extends Fragment {

    IgdbDAO dao;
    RecyclerView recyclerView;
    IgdbCallback igdbCallback;
    CustomCallback customCallback;

    private DrawerLayout drawer;
    private ExpandableListView expListView;
    private FrameLayout frameLayout;
    private ExpandableListAdapter listAdapterExpandable;
    private LinearLayout lnrLytFilterGames;
    private Button searchBtn;

    private HashMap<String, List<String>> data;
    private List<Genre> genres;
    private List<String> groups;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        Bundle bundle = getArguments();
        customCallback = (CustomCallback) bundle.getSerializable("callback");

        igdbCallback = new IgdbCallback() {
            @Override
            public void findGenres(Context context, List<Genre> genresAPI) {
                genres = genresAPI;

                groups = new ArrayList<String>();
                data = new HashMap<String, List<String>>();

                groups.add("Genre");

                List<String> genres2 = new ArrayList<>();
                for (Genre genre : genresAPI) {
                    genres2.add(genre.getName());
                }
                data.put(groups.get(0), genres2);

                listAdapterExpandable = new ExpandableListAdapter(context, groups, data);

                expListView.setAdapter(listAdapterExpandable);
            }

            @Override
            public void findGames(Context context, List<Videogame> videogamesAPI) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                VideogameAdapter adapter = new VideogameAdapter(videogamesAPI);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void getGame(Context context, Videogame videogame) {

            }
        };

        dao = new IgdbDAO();
        dao.getGenres(getContext(), igdbCallback);

        drawer = this.getActivity().findViewById(R.id.drawer_layout);
        searchBtn = this.getActivity().findViewById(R.id.buttonId);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Set<Genre>> filterGames = new HashMap<>();
                for (Map.Entry<String, Set<String>> entry : listAdapterExpandable.getItemsChecked().entrySet()) {
                    HashSet<Genre> z = new HashSet<>();
                    filterGames.put(entry.getKey(), z);
                    HashSet<String> y = (HashSet<String>) entry.getValue();
                    for (String genre : y) {
                        for (Genre genre1 : genres) {
                            if (genre1.getName().equals(genre)) {
                                HashSet<Genre> c = (HashSet<Genre>) filterGames.get(entry.getKey());
                                c.add(genre1);
                                filterGames.put(entry.getKey(), c);
                                break;
                            }
                        }
                    }
                }
                Log.d("SGN", filterGames.toString());
                filterGames(getContext(), igdbCallback, filterGames);
            }
        });

        expListView = this.getActivity().findViewById(R.id.expandable_list);
        lnrLytFilterGames = this.getActivity().findViewById(R.id.lnrLytFilterGames);

        dao = new IgdbDAO();
        Set<Genre> x = new HashSet<>();
        dao.getGamesByGenre(getContext(), igdbCallback, x);

        return view;
    }

    private void filterGames(Context context, IgdbCallback callback, HashMap<String, Set<Genre>> itemsChecked) {
        IgdbDAO dao = new IgdbDAO();
        dao.getGamesByGenre(context, callback, itemsChecked.get("Genre"));
    }

    class VideogameAdapter extends RecyclerView.Adapter<VideogameAdapter.ViewHolder> {

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
                        Videogame videogame = videogames.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("videogame", videogame);
                        VideogameDetailsFragment detail = new VideogameDetailsFragment();
                        detail.setArguments(bundle);
                        customCallback.accessFragment(R.id.fragment_container, detail);

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videogame_card, parent, false);

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
                    stringBuilder.append(", ");
                }
                i++;
            }
            holder.tvVideogameGenre.setText(stringBuilder.toString());
            String img = "https://images.igdb.com/igdb/image/upload/t_cover_small_2x/" + videogame.getCover().getImageId() + ".jpg";
            Picasso.get().load(img).into(holder.ivVideogameImage);
            int rating = Integer.valueOf((int) videogame.getRating());
            Log.d("SGN", videogame.getName() + " " + rating);
            holder.pbVideogameRating.setProgress(rating);
            if (rating == 0) {
                holder.tvVideogameRating.setText("N/A");
            } else {
                holder.tvVideogameRating.setText(String.valueOf(rating));
            }


        }

        @Override
        public int getItemCount() {
            return videogames.size();
        }

    }
}
