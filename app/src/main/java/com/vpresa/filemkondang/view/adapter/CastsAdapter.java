package com.vpresa.filemkondang.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.model.Credit;
import com.vpresa.filemkondang.restapi.RestAPIURL;
import com.vpresa.filemkondang.utility.CommonFunction;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.ListHolder> {
    private Context context;
    private List<Credit.Cast> casts;

    public CastsAdapter(Context context, List<Credit.Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_casts_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailCastsName.setText(casts.get(position).getName());
        holder.detailCastsCharacter.setText(casts.get(position).getCharacter());
        CommonFunction.setImage(context, RestAPIURL.getUrlImage(casts.get(position).getProfilePath()), holder.detailCastsPic);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailCastsName;
        TextView detailCastsCharacter;
        ImageView detailCastsPic;
        CardView detailCastsLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailCastsName = (TextView) itemView.findViewById(R.id.detail_casts_name);
            detailCastsCharacter = (TextView) itemView.findViewById(R.id.detail_casts_character);
            detailCastsPic = (ImageView) itemView.findViewById(R.id.detail_casts_pic);
            detailCastsLayout = (CardView) itemView.findViewById(R.id.detail_casts_layout);
        }
    }
}