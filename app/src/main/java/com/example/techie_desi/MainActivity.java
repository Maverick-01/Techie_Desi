package com.example.techie_desi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techie_desi.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    PostAdapter adapter;
    List<Item> items=new ArrayList<>();
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    String token = "";
    SpinKitView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.postList);
        manager = new LinearLayoutManager(this);
        adapter = new PostAdapter(this,items);
        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
        progress =findViewById(R.id.spin_kit);

        setUpToolbar();
        navigationView= findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_credit:
                        Toast.makeText(MainActivity.this, "CREDIT", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                    isScrolling=true;

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=manager.getChildCount();
                totalItems=manager.getItemCount();
                scrollOutItems=manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrollOutItems==totalItems))
                {
                    isScrolling=false;
                    getData();
                }
            }
        });

        getData();
    }

    private void setUpToolbar()
    {
        drawerLayout= findViewById(R.id.drawer);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    private void getData ()
    {
        String url = BloggerApi.url+ "?key=" +BloggerApi.key;
        if (token!="")
        {
            url=url+ "&pageToken=" +token;
        }
        if (token == null)
        {
            return;
        }
        progress.setVisibility(View.VISIBLE);
        final Call<PostList> postList= BloggerApi.getService().getPostList(url);
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();
                token = list.getNextPageToken();
                items.addAll(list.getItems());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}