package com.example.castle.castlelistview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.List;

import com.example.castle.castlelistview.SoundStructData.*;

public class AlbumsActivity extends AppCompatActivity {
    private ListView tListView;
    private ProgressDialog psDiaog;
    private ArrayAdapter<String> jArrayAdapter;
    private List<AlbumsDatStt> index_albums;
    private static final String sitHome = "http://3rd.ximalaya.com/categories/";
    private static final String iDent1 = "/hot_albums?i_am=leyunrui&tag=";
    private static final String iDent2 = "&page=1&per_page=50&uni=mv_test_castle_001";
    final static String TAG = "AlbumsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        psDiaog = new ProgressDialog(this);
        psDiaog.setTitle("Download");
        psDiaog.setCancelable(true);
        psDiaog.setMessage("Downloading list...");

        tListView = (ListView) this.findViewById(R.id.listViewAlbums);

        Intent intent = getIntent();
        int category_id = intent.getIntExtra("category_id", 0);
        String tags_name = intent.getStringExtra("tag_name");
        Log.i(TAG, "Ready url:" + sitHome + category_id + iDent1 + tags_name + iDent2);

        try {
            String tags_name_utf8 = URLEncoder.encode(tags_name, "utf-8");
            new MyTaskAlbums().execute(sitHome + category_id + iDent1 + tags_name_utf8 + iDent2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AlbumsActivity.this, TracksActivity.class);
                intent.putExtra("album_id", index_albums.get(position).getId());
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    public class MyTaskAlbums extends AsyncTask<String, Integer, List<AlbumsDatStt>> {
        final String TAG = "MyTaskAlbums";

        @Override
        protected List<AlbumsDatStt> doInBackground(String... params) {
            return HttpUtils.getAlbumsJson(params[0], AlbumsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDiaog.show();
        }

        @Override
        protected void onPostExecute(List<AlbumsDatStt> strings) {
            super.onPostExecute(strings);
            if (strings != null && strings.size() > 0) {
                jArrayAdapter = new ArrayAdapter<String>(AlbumsActivity.this,
                        android.R.layout.simple_list_item_1,
                        AlbumsDatStt.getTitleList(strings));
                tListView.setAdapter(jArrayAdapter);
                index_albums = strings;
            } else
                Toast.makeText(AlbumsActivity.this, "No item return", Toast.LENGTH_LONG).show();
            psDiaog.dismiss();

            Log.i(TAG, "Castle: Dispaly List.");
        }
    }
}
