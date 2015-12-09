package com.example.castle.castlelistview.SoundStructData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by castle on 2015/12/3.
 */
public class TracksDatStt {
    private int soundid;
    private String title;
    private String duration;
    private String cverL;

    public TracksDatStt(int soundid, String title, String duration, String cverL) {
        this.soundid = soundid;
        this.title = title;
        this.duration = duration;
        this.cverL = cverL;
    }

    public int getSoundid() {
        return this.soundid;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getCverL() {
        return this.cverL;
    }

    public static List<String> getTitleList(List<TracksDatStt> tracksList) {
        List<String> titleList = new ArrayList<String>();
        for (int i = 0; i < tracksList.size(); i++) {
            titleList.add(tracksList.get(i).getTitle() + "(" + tracksList.get(i).getDuration() + ")");
        }
        return titleList;
    }
}
