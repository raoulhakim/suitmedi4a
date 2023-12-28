package com.example.raoulhakim_suitmedi4a;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserpageActivity extends AppCompatActivity implements OnDataClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<ListUser> userData = new ArrayList<>();
    private int page = 1;
    private int limit = 2;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noDataTextView;
    private NestedScrollView nestedScrollView;
    private GetUser getUser;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setUpRecyclerView();
        setListeners();

        getData(page, limit);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.rviewUser);
        progressBar = findViewById(R.id.pBar);
        noDataTextView = findViewById(R.id.txtNodata);
        nestedScrollView = findViewById(R.id.nestedSV);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpRecyclerView() {
        getUser = new GetUser(UserpageActivity.this, userData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserpageActivity.this));
        recyclerView.setAdapter(getUser);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setListeners() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                    getUser.notifyDataSetChanged();
                }
            }
        });
    }

    private void getData(int page, int limit) {
        noDataTextView.setVisibility(View.GONE);
        String url = "https://reqres.in/api/users?page=" + page + "&per_page=10";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject responseObject = data.getJSONObject(i);
                                String email = responseObject.getString("email");
                                String firstName = responseObject.getString("first_name");
                                String lastName = responseObject.getString("last_name");
                                String avatar = responseObject.getString("avatar");
                                userData.add(new ListUser(firstName, lastName, email, avatar));
                            }
                            getUser.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void OnDataClick(String firstname, String lastname) {
        Intent intent = new Intent();
        intent.putExtra("firstname", firstname);
        intent.putExtra("lastname", lastname);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();
        page++;
        getData(page, limit);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                getUser.notifyDataSetChanged();
            }
        }, 2000);
    }
}
