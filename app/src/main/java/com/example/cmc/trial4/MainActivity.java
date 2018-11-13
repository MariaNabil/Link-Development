package com.example.cmc.trial4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements RvAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener{
    private String URL_JSON = "https://newsapi.org/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<ItemModel> lstNews = new ArrayList<>();
    private RecyclerView myrv ;
    private ImageView imageView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myrv = findViewById(R.id.rv);
        //jsoncall();
        jsonParse();

    }



    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        ItemModel itemModel = new ItemModel();

                        itemModel.setAuthor(jsonObject.getString("author"));
                        itemModel.setTitle(jsonObject.getString("title"));
                        itemModel.setDescription(jsonObject.getString("description"));
                        itemModel.setImage_url(jsonObject.getString("urlToImage"));
                        itemModel.setPublishedDate(jsonObject.getString("publishedAt"));
                        itemModel.setUrl(jsonObject.getString("url"));
                        //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                        lstNews.add(itemModel);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(MainActivity.this,"Size of Liste "+String.valueOf(lstNews.size()),Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,lstNews.get(1).toString(),Toast.LENGTH_SHORT).show();

                setRvadapter(lstNews);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }
    private void jsonParse() {

        String url = "https://newsapi.org/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject article = jsonArray.getJSONObject(i);
                                ItemModel itemModel = new ItemModel();
                                itemModel.setAuthor(article.getString("author"));
                                itemModel.setTitle(article.getString("title"));
                                itemModel.setDescription(article.getString("description"));
                                itemModel.setImage_url(article.getString("urlToImage"));
                                itemModel.setPublishedDate(article.getString("publishedAt"));
                                itemModel.setUrl(article.getString("url"));
                                lstNews.add(itemModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setRvadapter(lstNews);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }
    public void setRvadapter (List<ItemModel> lst) {

        RvAdapter myAdapter = new RvAdapter(this,lst,this) ;
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);




    }


    @Override
    public void onClick(View view, int position) {

        //intent.putExtra("ItemModel",mData.get(getAdapterPosition()));
        //String  t=lstNews.get(position).getTitle();
        Intent intent = new Intent(this, DetailsActivity.class);
        //intent.putExtra(Intent.EXTRA_TEXT,t);
        intent.putExtra("parcel_data", lstNews.get(position));

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

 /*   @Override
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
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_explore: {
                Toast.makeText(MainActivity.this, "Explore", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_live_chat: {
                Toast.makeText(MainActivity.this, "Live Chat", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.nav_gallery: {
                Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.nav_wish_list: {
                Toast.makeText(MainActivity.this, "Wish List", Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.nav_E_Magazine: {
                Toast.makeText(MainActivity.this, "E-Magazine", Toast.LENGTH_SHORT).show();
                break;

            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}

