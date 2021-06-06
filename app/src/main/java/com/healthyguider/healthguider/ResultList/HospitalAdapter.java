package com.healthyguider.healthguider.ResultList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.Direction.DrawDirections;
import com.healthyguider.healthguider.MapsActivity;
import com.healthyguider.healthguider.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wezzy on 29/04/2018.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ListHolder>{


    Context ctx;
    List <Hospital> HospitalList;
     //AdapterCallback callback;

    public HospitalAdapter(Context ctx ,ArrayList<Hospital> hospitalList) {
        this.ctx = ctx;
        this.HospitalList = hospitalList;



    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_element_layout,null);
        ListHolder listHolder = new ListHolder(view);
        return listHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final ListHolder holder, int position) {

        final Hospital hospital = HospitalList.get(position);

        holder.HospitalAdress.setText(hospital.getAdresse());
        holder.HospitalName.setText(hospital.getNomh());
        holder.HospitalSpecialites.setText(hospital.getSpecialite());
        holder.HospitalPhone.setText(hospital.getMobile());


            holder.HospitalDistance.setText(hospital.getDistance().toString()+"  Km");



        holder.DrawPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapsActivity.LocateMe.callOnClick();

                MapsActivity.DrawPaths(new LatLng(hospital.getLatitude(),hospital.getLongitude()),hospital);





            }
        });

        holder.LocationIcon.setImageResource(R.mipmap.location);
        holder.DrawPath.setImageResource(R.mipmap.rout);

    }

    @Override
    public int getItemCount()
    {

        return HospitalList.size();
    }


    class ListHolder extends RecyclerView.ViewHolder {

        ImageView LocationIcon;
        TextView HospitalName,HospitalAdress,HospitalPhone,HospitalSpecialites,HospitalDistance;
        ImageButton DrawPath;


        public ListHolder(View itemView) {
            super(itemView);
            LocationIcon = itemView.findViewById(R.id.LocationImage);
            HospitalAdress =itemView.findViewById(R.id.Hopital_adress);
            HospitalName=itemView.findViewById(R.id.Hopital_name);
            HospitalSpecialites=itemView.findViewById(R.id.specialiteList);
            HospitalDistance=itemView.findViewById(R.id.distance);
            HospitalPhone=itemView.findViewById(R.id.HospitalPhone);
            LocationIcon = itemView.findViewById(R.id.LocationImage);
            DrawPath =itemView.findViewById(R.id.DrawPath);

        }




    }


}
