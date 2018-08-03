package com.framgia.music_20.screen.list_song;

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
import java.util.ArrayList;
import java.util.List;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.Holder> {

    private List<Song> mSongs;
    private ItemClickListener mItemClickListener;

    public ListSongAdapter() {
        mSongs = new ArrayList<>();
    }

    public void updateSong(List<Song> songList) {
        if (mSongs != null) {
            mSongs.clear();
        }
        mSongs.addAll(songList);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ListSongAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycleview, parent, false);
        return new Holder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSongAdapter.Holder holder, final int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mCircleImageView;
        private TextView mTextname, mTextArtist;
        private ItemClickListener mItemClickListener;

        Holder(final View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            mCircleImageView = itemView.findViewById(R.id.circle_image);
            mTextname = itemView.findViewById(R.id.text_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        void bindData(Song song) {
            mTextname.setText(song.getTitle());
            mTextArtist.setText(song.getArtist().getUsername());
            bindImage(song);
        }

        void bindImage(Song song) {
            Glide.with(itemView.getContext())
                    .load(song.getArtist().getAvatarUrl())
                    .into(mCircleImageView);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClickListen(getAdapterPosition());
        }
    }
}
