package com.example.castle.castlelistview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TagsActivity extends AppCompatActivity {
    private ListView tListView;
    private ProgressDialog psDiaog;
    private ArrayAdapter<String> jArrayAdapter;
    private List<String> index_1st;
    private static final String sitHome = "http://3rd.ximalaya.com/categories/";
    private static final String iDent = "/tags?i_am=leyunrui&uni=mv_test_castle_001";
    private int category_id;
    final static String TAG = "TagsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        psDiaog = new ProgressDialog(this);
        psDiaog.setTitle("Download");
        psDiaog.setCancelable(true);
        psDiaog.setMessage("Downloading list...");

        tListView = (ListView) this.findViewById(R.id.listViewTags);

        Intent intent = getIntent();
        category_id = intent.getIntExtra("category_id", 0);


        new MyTaskTags().execute(sitHome + category_id + iDent);

        tListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "You Select: " + index_1st.get(position), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(TagsActivity.this, AlbumsActivity.class);
                intent.putExtra("tag_name", index_1st.get(position));
                intent.putExtra("category_id", category_id);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    public class MyTaskTags extends AsyncTask<String, Integer, List<String>> {
        final String TAG = "MyTaskTags";

        @Override
        protected List<String> doInBackground(String... params) {
            return HttpUtils.getTagsJson(params[0], TagsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDiaog.show();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if (strings != null && strings.size() > 0) {
                jArrayAdapter = new ArrayAdapter<String>(TagsActivity.this, android.R.layout.simple_list_item_1, strings);
                tListView.setAdapter(jArrayAdapter);
                index_1st = strings;
            } else
                Toast.makeText(TagsActivity.this, "No item return", Toast.LENGTH_LONG).show();
            psDiaog.dismiss();

            Log.i(TAG, "Castle: Dispaly List.");
        }
    }

}
