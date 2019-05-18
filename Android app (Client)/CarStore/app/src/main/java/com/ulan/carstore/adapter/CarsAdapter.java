package com.ulan.carstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulan.carstore.R;
import com.ulan.carstore.model.Car;

import java.util.List;

/**
 * Created by Ulan on 22.04.2019.
 */
public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {

    private List<Car> carsList;
    private Context context;

    public CarsAdapter(Context context, List<Car> carsList) {
        this.context = context;
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cars_adapter, viewGroup, false);
        return new CarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder carsViewHolder, int i) {
        carsViewHolder.title.setText(carsList.get(i).getTitle());
        carsViewHolder.model.setText(carsList.get(i).getModel());
        carsViewHolder.city.setText(carsList.get(i).getCity());
        carsViewHolder.price.setText(carsList.get(i).getPrice() + context.getResources().getString(R.string.tenge));
        carsViewHolder.number.setText(carsList.get(i).getNumber());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView model;
        TextView city;
        TextView price;
        TextView number;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            model = itemView.findViewById(R.id.model);
            city = itemView.findViewById(R.id.city);
            price = itemView.findViewById(R.id.price);
            number = itemView.findViewById(R.id.number);
        }
    }
}
