package com.nuist.you.smarthome.http;

import org.java_websocket.drafts.Draft;

/**
 * Created by youxuan on
 * webSocket 协议
 */
public class DraftInfo {

    public final String draftName;
    public final Draft draft;

    public DraftInfo(String draftName, Draft draft) {
        this.draftName = draftName;
        this.draft = draft;
    }

    public String getDraftName() {
        return draftName;
    }

    public Draft getDraft() {
        return draft;
    }

    @Override
    public String toString() {
        return "DraftInfo{" +
                "draftName='" + draftName + '\'' +
                ", draft=" + draft +
                '}';
    }
}