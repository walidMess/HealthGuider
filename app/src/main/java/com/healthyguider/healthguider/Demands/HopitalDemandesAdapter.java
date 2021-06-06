package com.healthyguider.healthguider.Demands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.R;
import com.healthyguider.healthguider.ResultList.HospitalAdapter;

import java.util.ArrayList;
import java.util.List;

public class HopitalDemandesAdapter  extends RecyclerView.Adapter<HopitalDemandesAdapter.ListHolder> {

        Context ctx;
        List<Hospital> HospitalDemandeList;

FetchData fetchData;
public HopitalDemandesAdapter(Context ctx ,ArrayList<Hospital> hospitalList) {
        this.ctx = ctx;
        this.HospitalDemandeList = hospitalList;


        fetchData=new FetchData(ctx);

        }

@NonNull
@Override
public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_element_hopitaldemand,null);
        ListHolder listHolder = new ListHolder(view);
        return listHolder;


        }

@Override
public void onBindViewHolder(@NonNull final ListHolder holder, int position) {

final Hospital hospital = HospitalDemandeList.get(position);

        holder.HospitalAdress.setText(hospital.getAdresse());
        holder.HospitalName.setText(hospital.getNomh());
        holder.HospitalSpecialites.setText(hospital.getSpecialite());
        holder.HospitalPhone.setText(hospital.getMobile());


        holder.HospitalGPS.setText(hospital.getLatitude().toString()+","+hospital.getLongitude());



        holder.DeleteDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchData.AdminDecision("Delete",hospital.getNomh());


            }
        });


        holder.AddDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchData.AdminDecision("Add",hospital.getNomh());


            }
        });

        }

@Override
public int getItemCount()
        {

        return HospitalDemandeList.size();
        }


class ListHolder extends RecyclerView.ViewHolder {

    TextView HospitalName,HospitalAdress,HospitalPhone,HospitalSpecialites,HospitalGPS;
    ImageButton AddDemand,DeleteDemand;


    public ListHolder(View itemView) {
        super(itemView);
        HospitalAdress =itemView.findViewById(R.id.Hopital_adress);
        HospitalName=itemView.findViewById(R.id.Hopital_name);
        HospitalSpecialites=itemView.findViewById(R.id.specialiteList);
        HospitalGPS=itemView.findViewById(R.id.GPSDemand);
        HospitalPhone=itemView.findViewById(R.id.HospitalPhone);
        AddDemand = itemView.findViewById(R.id.AddDemand);
        DeleteDemand = itemView.findViewById(R.id.DeleteDemand);

    }




}
}
