package com.framgia.music_20.screen.list_song;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.Song;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.Holder> {

    private List<Song> mSongs;
    private Context mContext;

    public ListSongAdapter(Context context, List<Song> songList) {
        mContext = context;
        mSongs = songList;
    }

    public void updateSong(List<Song> songList) {
        if (mSongs != null) {
            mSongs.clear();
        }
        mSongs.addAll(songList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListSongAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycleview, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSongAdapter.Holder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircleImageView imageAvatar;
        TextView textName, textArtist;

        Holder(View itemView) {
            super(itemView);
            imageAvatar = itemView.findViewById(R.id.circle_image);
            textName = itemView.findViewById(R.id.text_name);
            textArtist = itemView.findViewById(R.id.text_artist);
        }

        void bindData(Song song) {
            textName.setText(song.getTitle());
            textArtist.setText(song.getArtist().getUsername());
            bindImage(song);
        }

        void bindImage(Song song) {
            Glide.with(itemView.getContext())
                    .load(song.getArtist().getAvatarUrl())
                    .into(imageAvatar);
        }
    }
}
