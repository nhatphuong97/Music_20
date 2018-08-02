package com.framgia.music_20.data.source.remote;

import com.framgia.music_20.data.model.Artist;
import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.source.DataCallBack;
import com.framgia.music_20.data.source.SongDataSource;
import com.framgia.music_20.data.source.OnDataListener;
import com.framgia.music_20.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseDataSong implements SongDataSource.RemoteDataSource {
    private static ParseDataSong sInstance;

    public static synchronized ParseDataSong getInstance() {
        if (sInstance == null) {
            sInstance = new ParseDataSong();
        }
        return sInstance;
    }

    private MoreData ParseData(String Data) throws JSONException {
        MoreData moreData = new MoreData();
        List<Song> listSong = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(Data);
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.COLLECTION);
            int lengthArray = jsonArray.length();
            for (int i = 0; i < lengthArray; i++) {
                JSONObject jsonObjectSong = jsonArray.getJSONObject(i);
                JSONObject jsonObjectArtist = jsonObjectSong.getJSONObject(Constant.USER);

                Song song = new Song.Builder().withId(jsonObjectSong.getInt(Song.SongComponent.ID))
                        .withGenre(jsonObjectSong.getString(Song.SongComponent.GENRE))
                        .withTitle(jsonObjectSong.getString(Song.SongComponent.TITLE))
                        .withArtworkUrl(jsonObjectSong.getString(Song.SongComponent.ARTWORK_URL))
                        .withStreamUrl(jsonObjectSong.getString(Song.SongComponent.STREAM_URL))
                        .withDuration(jsonObjectSong.getInt(Song.SongComponent.DURATION))
                        .withArtist(new Artist(jsonObjectArtist.getInt(Artist.ArtistComponent.ID),
                                jsonObjectArtist.getString(Artist.ArtistComponent.USERNAME),
                                jsonObjectArtist.getString(Artist.ArtistComponent.AVATAR_URL)))
                        .build();
                listSong.add(song);
            }
            String nextHref = jsonObject.getString(Constant.NEXT_HREF);
            moreData.setSongArrayList(listSong);
            moreData.setNextHref(nextHref);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moreData;
    }

    @Override
    public void getSongByGenre(String genre, final DataCallBack<MoreData> callBack) {
        new GetDataUrl(new OnDataListener() {

            @Override
            public void onSuccess(String data) {
                MoreData moreData = null;
                try {
                    moreData = ParseData(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess(moreData);
            }

            @Override
            public void onFail(Exception e) {
                callBack.onFail(e);
            }
        }).execute(Constant.ALL_LINK + genre);
    }
}
