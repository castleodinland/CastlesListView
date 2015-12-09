package com.example.castle.castlelistview.SoundStructData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by castle on 2015/12/3.
 */
public class AlbumsDatStt {
    private int id;
    private String title;
    private int tracks_count;

    public AlbumsDatStt(int id, String title, int tracks_count) {
        this.id = id;
        this.title = title;
        this.tracks_count = tracks_count;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getTracksCount() {
        return this.tracks_count;
    }

    public static List<String> getTitleList(List<AlbumsDatStt> alList) {
        List<String> titleList = new ArrayList<String>();
        for (int i = 0; i < alList.size(); i++) {
            titleList.add(alList.get(i).getTitle() + "(" + alList.get(i).getTracksCount() + ")");
        }
        return titleList;
    }
}
