package com.vpresa.filemkondang.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.model.Video;
import com.vpresa.filemkondang.restapi.RestAPIURL;
import com.vpresa.filemkondang.utility.CommonFunction;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ListHolder> {
    private Context context;
    private List<Video> videos;

    public TrailersAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_trailers_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailTrailersName.setText(videos.get(position).getName());
        holder.detailTrailersSource.setText(videos.get(position).getSite());
        CommonFunction.setImage(context, RestAPIURL.getUrlYoutubeImage(videos.get(position).getKey()), holder.detailTrailersPic);
        holder.detailTrailersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trailerURL = RestAPIURL.getYoutubeLink(videos.get(holder.getAdapterPosition()).getKey());
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailTrailersName;
        TextView detailTrailersSource;
        ImageView detailTrailersPic;
        CardView detailTrailersLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailTrailersName = (TextView) itemView.findViewById(R.id.detail_trailer_name);
            detailTrailersSource = (TextView) itemView.findViewById(R.id.detail_trailer_source);
            detailTrailersPic = (ImageView) itemView.findViewById(R.id.detail_trailer_pic);
            detailTrailersLayout = (CardView) itemView.findViewById(R.id.detail_trailer_layout);
        }
    }
}