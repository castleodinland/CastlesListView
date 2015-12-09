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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView tListView;
    private ArrayAdapter<String> tArrayAdapter;
    private ArrayAdapter<String> jArrayAdapter;
    private ProgressDialog psDiaog;
    private ImageView psImageView;
    private static String rURL = "http://3rd.ximalaya.com/categories?i_am=leyunrui&uni=mv_test_castle_001";
    private List<String> index_1st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Downloading data...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                new MyTask().execute(rURL);
            }
        });

        psDiaog = new ProgressDialog(this);
        psDiaog.setTitle("Download");
        psDiaog.setCancelable(true);
        psDiaog.setMessage("Downloading list...");
        tListView = (ListView) this.findViewById(R.id.listView);

        //Important!!! set Adapter in a AsyncTask if get data from internet
        //tArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getDataSource());
        //tListView.setAdapter(tArrayAdapter);

        psImageView = (ImageView) this.findViewById(R.id.imageView_main);
        psImageView.setImageResource(R.drawable.mvs_icon);

        tListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              Toast.makeText(MainActivity.this, "-->" + tArrayAdapter.getItem(position), Toast.LENGTH_LONG).show();
                Snackbar.make(view, "You Select: " + index_1st.get(position), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, TagsActivity.class);
                intent.putExtra("category_id", position);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    public class MyTask extends AsyncTask<String, Integer, List<String>> {
        final String TAG = "MyTask";

        @Override
        protected List<String> doInBackground(String... params) {
            return HttpUtils.getCategoriesJson(params[0], MainActivity.this);
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
                jArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, strings);
                tListView.setAdapter(jArrayAdapter);
                psImageView.setVisibility(View.GONE);
                index_1st = strings;
            } else
                Toast.makeText(MainActivity.this, "No item return", Toast.LENGTH_LONG).show();
            psDiaog.dismiss();

            Log.i(TAG, "Castle: Dispaly List.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
