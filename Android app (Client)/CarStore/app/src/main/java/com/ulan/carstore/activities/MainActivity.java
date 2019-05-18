package com.ulan.carstore.activities;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ulan.carstore.R;
import com.ulan.carstore.adapter.CarsAdapter;
import com.ulan.carstore.model.Car;
import com.ulan.carstore.retrofit.GetDataService;
import com.ulan.carstore.retrofit.PostDataService;
import com.ulan.carstore.retrofit.RestApi;
import com.ulan.carstore.retrofit.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CarsAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private FloatingActionButton fab;
    private List<Car> mCarsList;
    private final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getResources().getText(R.string.loading));
        progressDialog.show();

        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCustomAlertDialog();
            }
        });
    }

    // GET REQUEST
    private void getData() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RestApi> call = service.getAllCars();
        call.enqueue(new Callback<RestApi>() {
            @Override
            public void onResponse(Call<RestApi> call, Response<RestApi> response) {
                progressDialog.dismiss();
                RestApi api = response.body();
                if (api != null) {
                    generateDataList(api.carsList);
                }
            }

            @Override
            public void onFailure(Call<RestApi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateDataList(List<Car> carsList) {
        recyclerView = findViewById(R.id.cars_recycle_view);
        adapter = new CarsAdapter(this, carsList);
        mCarsList = carsList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // POST REQUEST
    private void getCustomAlertDialog() {
        View inflater = getLayoutInflater().inflate(R.layout.dialog_add_car, null);
        final EditText title = inflater.findViewById(R.id.title_dialog);
        final EditText model = inflater.findViewById(R.id.model_dialog);
        final EditText city = inflater.findViewById(R.id.city_dialog);
        final EditText price = inflater.findViewById(R.id.price_dialog);
        final EditText number = inflater.findViewById(R.id.number_dialog);

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.advertisement);
        alert.setCancelable(false);
        Button cancel = inflater.findViewById(R.id.cancel);
        Button save = inflater.findViewById(R.id.save);
        alert.setView(inflater);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDataService service = RetrofitClientInstance.getRetrofitInstance().create(PostDataService.class);
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(mCarsList.size() + 1));
                params.put("title", title.getText().toString());
                params.put("description", getResources().getString(R.string.empty));
                params.put("model", model.getText().toString());
                params.put("city", city.getText().toString());
                params.put("price", price.getText().toString());
                params.put("number", number.getText().toString());

                Call<RestApi> call = service.sendCar(params);

                call.enqueue(new Callback<RestApi>() {
                    @Override
                    public void onResponse(Call<RestApi> call, Response<RestApi> response) {
                        if(response.isSuccessful()){
                            Log.d(LOG_TAG, "Message was sent (Successful)");
                        }
                    }

                    @Override
                    public void onFailure(Call<RestApi> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                dialog.dismiss();

                getData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_list:
                progressDialog.show();
                getData();
                break;
        }
        return true;
    }
}