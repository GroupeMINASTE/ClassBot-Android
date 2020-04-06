package me.nathanfallet.classbot.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import me.nathanfallet.classbot.R;
import me.nathanfallet.classbot.interfaces.HomeContainer;
import me.nathanfallet.classbot.models.APIList;
import me.nathanfallet.classbot.models.APIRequest;
import me.nathanfallet.classbot.models.APIResponseStatus;
import me.nathanfallet.classbot.models.Cours;
import me.nathanfallet.classbot.models.Devoirs;
import me.nathanfallet.classbot.sections.CoursSection;
import me.nathanfallet.classbot.sections.MoreSection;

public class MainActivity extends AppCompatActivity implements HomeContainer {

    private APIList list;

    private SwipeRefreshLayout layout;
    private RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the view
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.background));

        // Initialize sections
        sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(new CoursSection(this));
        sectionAdapter.addSection(new MoreSection(this));

        // Bind adapter to recyclerView
        recyclerView.setAdapter(sectionAdapter);

        // Add refresh
        layout = new SwipeRefreshLayout(this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(recyclerView);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
            }
        });

        // Load content
        loadContent();

        // Set content view
        setContentView(layout);
    }

    public void loadContent() {
        String host = getHost();

        // Check if host is configured
        if (!host.isEmpty()) {
            // Fetch API
            new APIRequest("GET", host, "/api/liste", new APIRequest.CompletionHandler() {
                @Override
                public void completionHandler(@Nullable Object object, APIResponseStatus status) {
                    // Check data
                    if (object instanceof JSONObject) {
                        // Set data
                        list = new APIList((JSONObject) object);
                    } else {
                        // Set empty data
                        list = new APIList(new JSONObject());
                    }

                    // Update recyclerView
                    sectionAdapter.notifyDataSetChanged();

                    // End refreshing
                    if (layout.isRefreshing()) {
                        layout.setRefreshing(false);
                    }
                }
            }).execute();
        } else {
            // Show configuration controller
            showConfiguration();
        }
    }

    public String getHost() {
        SharedPreferences prefs = getSharedPreferences("me.nathanfallet.classbot", Context.MODE_PRIVATE);

        return prefs.getString("host", "");
    }

    public void setHost(String host) {
        SharedPreferences prefs = getSharedPreferences("me.nathanfallet.classbot", Context.MODE_PRIVATE);

        prefs.edit().putString("host", host).apply();
    }

    public void showConfiguration() {
        Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
        startActivityForResult(intent, 667);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 667 && resultCode == Activity.RESULT_OK && data != null) {
            // Save data
            setHost(data.getStringExtra("host"));

            // Load content again
            loadContent();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public Cours[] getCours() {
        return list != null ? list.cours : new Cours[0];
    }

    @Override
    public Devoirs[] getDevoirs() {
        return list != null ? list.devoirs : new Devoirs[0];
    }
}
