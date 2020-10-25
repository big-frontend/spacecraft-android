package com.hawksjamesf.map.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class L7{
        public String objectId;
        public String auth;
        public String createdAt;
        public String updatedAt;
        public int myId;
        public List<List<AppCellInfo>> appCellInfos;
        @SerializedName("AppLocation")
        public AppLocation appLocation;
}