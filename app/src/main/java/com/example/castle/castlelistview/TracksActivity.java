package com.example.castle.castlelistview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.castle.castlelistview.SoundStructData.AlbumsDatStt;
import com.example.castle.castlelistview.SoundStructData.TracksDatStt;

import java.util.List;

public class TracksActivity extends AppCompatActivity {
    private ListView tListView;
    private ProgressDialog psDiaog;
    private ArrayAdapter<String> jArrayAdapter;
    private List<TracksDatStt> index_tracks;
    private static final String sitHome = "http://3rd.ximalaya.com/albums/";
    private static final String iDent1 = "/tracks?i_am=leyunrui&page=1&per_page=50";
    private static final String iDent2 = "&is_asc=true&uni=mv_test_castle_001";
    final static String TAG = "TracksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        psDiaog = new ProgressDialog(this);
        psDiaog.setTitle("Download");
        psDiaog.setCancelable(true);
        psDiaog.setMessage("Downloading list...");

        tListView = (ListView) this.findViewById(R.id.listViewTracks);

        Intent intent = getIntent();
        int album_id = intent.getIntExtra("album_id", 0);
        Log.i(TAG, "Tracks url:" + sitHome + album_id + iDent1 + iDent2);

        new MyTaskTracks().execute(sitHome + album_id + iDent1 + iDent2);

        tListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TracksActivity.this, MPlayerActivity.class);
                intent.putExtra("cverl_url", index_tracks.get(position).getCverL());
                intent.putExtra("title", index_tracks.get(position).getTitle());
                //intent.putExtra("category_id", category_id);
                startActivity(intent);
            }
        });
    }


    public class MyTaskTracks extends AsyncTask<String, Integer, List<TracksDatStt>> {
        final String TAG = "MyTask";

        @Override
        protected List<TracksDatStt> doInBackground(String... params) {
            return HttpUtils.getTracksJson(params[0], TracksActivity.this);
        }

        @Override
        protected void onPostExecute(List<TracksDatStt> tracksDatStts) {
            super.onPostExecute(tracksDatStts);
            if (tracksDatStts != null && tracksDatStts.size() > 0) {
                jArrayAdapter = new ArrayAdapter<String>(TracksActivity.this,
                        android.R.layout.simple_list_item_1,
                        TracksDatStt.getTitleList(tracksDatStts));
                tListView.setAdapter(jArrayAdapter);
                index_tracks = tracksDatStts;
            } else
                Toast.makeText(TracksActivity.this, "No item return", Toast.LENGTH_LONG).show();
            psDiaog.dismiss();

            Log.i(TAG, "Castle: Dispaly List.");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDiaog.show();
        }

    }
}

