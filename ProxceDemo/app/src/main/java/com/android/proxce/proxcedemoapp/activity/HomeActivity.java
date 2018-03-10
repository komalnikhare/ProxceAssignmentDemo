package com.android.proxce.proxcedemoapp.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.proxce.proxcedemoapp.R;
import com.android.proxce.proxcedemoapp.adapters.Items;
import com.android.proxce.proxcedemoapp.adapters.ListAdapter;
import com.android.proxce.proxcedemoapp.utils.RequestUtils;
import com.android.proxce.proxcedemoapp.utils.UserClickCallback;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;



public class HomeActivity extends AppCompatActivity {

    private TextView txtEmail,txtAddress;
    private RecyclerView recyclerView;
    private ListAdapter adapter;

    private String email, address;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = getIntent().getStringExtra("email");
        address = getIntent().getStringExtra("address");

        init();

    }

    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new ListAdapter(mUserClickCallback, this);
        recyclerView.setAdapter(adapter);

        txtEmail.setText(email);
        txtAddress.setText(address);

        dialog = new ProgressDialog(HomeActivity.this);
        dialog.setMessage("Loading...");

        getData();
    }


    private void getData(){
        final String URL = "http://services.groupkt.com/country/get/all";
        dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    if (response!= null){
                        JSONArray  array = response.optJSONObject("RestResponse").optJSONArray("result");
                        if (array!= null && array.length() > 0){
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Items>>(){}.getType();
                            List<Items> items = (List<Items>) gson.fromJson(array.toString(), listType);
                            adapter.setItemsList(items);
                        }

                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestUtils.addRequest(getApplicationContext(),request);
    }

    private final UserClickCallback mUserClickCallback = new UserClickCallback() {

        @Override
        public void onClick(Object object) {
            Items items = (Items) object;
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {

                Toast.makeText(getApplicationContext(),"Notification sent",Toast.LENGTH_SHORT).show();
                createNotification(items);
            }
        }
    };


    public void createNotification(Items items) {

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle(items.getName())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
