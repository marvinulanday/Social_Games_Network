package com.stucom.socialgamesnetwork.videogamesDetailsFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.model.GameMode;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Platform;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.lang.reflect.Field;


public class VideogameDetailsInfoFragment extends Fragment {

    Videogame videogame;
    TableLayout tableLayoutInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videogame_details_info, container, false);
        Bundle bundle = getArguments();
        videogame = (Videogame) bundle.getSerializable("videogame");
        tableLayoutInfo = view.findViewById(R.id.tableLayoutInfoVideogame);
        if (videogame.getParentVideogame() != null) {

        }
        TableRow[] x = new TableRow[8];
        Field[] fields = videogame.getClass().getDeclaredFields();
        for (Field f : fields) {

            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.custom_table_row, tableLayoutInfo, false);

            TextView textViewTitle = tableRow.findViewById(R.id.tableRowTxtViewTitle);
            LinearLayout linearLayout = tableRow.findViewById(R.id.tableRowLinearLayout);

            String type = f.getName();

            switch (type) {
                case "name":
                    textViewTitle.setText(type.toUpperCase());
                    TextView name = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                    name.setText(videogame.getName());
                    linearLayout.addView(name);
                    x[0] = tableRow;
                    break;
                case "summary":
                    textViewTitle.setText(type.toUpperCase());
                    TextView summary = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                    summary.setText(videogame.getName());
                    linearLayout.addView(summary);
                    x[3] = tableRow;
                    break;
                case "genres":
                    textViewTitle.setText(type.toUpperCase());
                    for (Genre g : videogame.getGenres()) {
                        TextView genres = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                        genres.setText(g.getName());
                        linearLayout.addView(genres);
                    }
                    x[2] = tableRow;
                    break;
                case "platforms":
                    textViewTitle.setText(type.toUpperCase());
                    for (Platform platform : videogame.getPlatforms()) {
                        TextView genres = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                        genres.setText(platform.getName());
                        linearLayout.addView(genres);
                    }
                    x[5] = tableRow;
                    break;
                case "companies":
                    break;
                case "gameModes":
                    textViewTitle.setText("GAME MODES");
                    for (GameMode g : videogame.getGameModes()) {
                        TextView textViewValue2 = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                        textViewValue2.setText(g.getName());
                        linearLayout.addView(textViewValue2);
                    }
                    x[1] = tableRow;
                    break;
                case "releaseDate":
                    textViewTitle.setText("RELEASE DATE");
                    TextView releaseDate = (TextView) getLayoutInflater().inflate(R.layout.custom_text_view, tableLayoutInfo, false);
                    releaseDate.setText(videogame.getReleaseDate());
                    linearLayout.addView(releaseDate);
                    x[4] = tableRow;
                    break;
                case "parentVideogame":
                    break;
                default:
            }
        }
        for (TableRow tableRow : x) {
            if (tableRow != null) tableLayoutInfo.addView(tableRow);
        }
        return view;
    }
}
