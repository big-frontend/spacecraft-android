package com.hawksjamesf.map;

import java.util.List;

public class Resp {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * objectId : dUYUVwHnxb
         * myId : 1
         * auth : Basic X2FuZHJvaWQubnVsbDpIVUFXRUktTkVYVVMtbnVsbA==
         * appCellInfos : [[{"bid":0,"cdmalat":0,"cdmalon":0,"cgiage":0,"cid":8563724,"lac":6188,"mcc":460,"mnc":1,"nci":0,"nid":0,"pci":0,"radio_type":"gsm_lte","rss":-78,"sid":0,"tac":0}]]
         * AppLocation : {"lat":31.225014,"lon":121.344552,"accu":191,"altit":4.9E-324,"bearing":359,"speed":0}
         * createdAt : 2020-04-27T10:59:20.115Z
         * updatedAt : 2020-04-27T10:59:20.115Z
         */

        private String objectId;
        private int myId;
        private String auth;
        private AppLocationBean AppLocation;
        private String createdAt;
        private String updatedAt;
        private List<List<AppCellInfosBean>> appCellInfos;

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public int getMyId() {
            return myId;
        }

        public void setMyId(int myId) {
            this.myId = myId;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public AppLocationBean getAppLocation() {
            return AppLocation;
        }

        public void setAppLocation(AppLocationBean AppLocation) {
            this.AppLocation = AppLocation;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<List<AppCellInfosBean>> getAppCellInfos() {
            return appCellInfos;
        }

        public void setAppCellInfos(List<List<AppCellInfosBean>> appCellInfos) {
            this.appCellInfos = appCellInfos;
        }

        public static class AppLocationBean {
            /**
             * lat : 31.225014
             * lon : 121.344552
             * accu : 191
             * altit : 4.9E-324
             * bearing : 359
             * speed : 0
             */

            private double lat;
            private double lon;
            private double accu;
            private double altit;
            private double bearing;
            private double speed;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getAccu() {
                return accu;
            }

            public void setAccu(int accu) {
                this.accu = accu;
            }

            public double getAltit() {
                return altit;
            }

            public void setAltit(double altit) {
                this.altit = altit;
            }

            public double getBearing() {
                return bearing;
            }

            public void setBearing(int bearing) {
                this.bearing = bearing;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }
        }

        public static class AppCellInfosBean {
            /**
             * bid : 0
             * cdmalat : 0
             * cdmalon : 0
             * cgiage : 0
             * cid : 8563724
             * lac : 6188
             * mcc : 460
             * mnc : 1
             * nci : 0
             * nid : 0
             * pci : 0
             * radio_type : gsm_lte
             * rss : -78
             * sid : 0
             * tac : 0
             */

            private int bid;
            private int cdmalat;
            private int cdmalon;
            private int cgiage;
            private int cid;
            private int lac;
            private int mcc;
            private int mnc;
            private int nci;
            private int nid;
            private int pci;
            private String radio_type;
            private int rss;
            private int sid;
            private int tac;

            public int getBid() {
                return bid;
            }

            public void setBid(int bid) {
                this.bid = bid;
            }

            public int getCdmalat() {
                return cdmalat;
            }

            public void setCdmalat(int cdmalat) {
                this.cdmalat = cdmalat;
            }

            public int getCdmalon() {
                return cdmalon;
            }

            public void setCdmalon(int cdmalon) {
                this.cdmalon = cdmalon;
            }

            public int getCgiage() {
                return cgiage;
            }

            public void setCgiage(int cgiage) {
                this.cgiage = cgiage;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getLac() {
                return lac;
            }

            public void setLac(int lac) {
                this.lac = lac;
            }

            public int getMcc() {
                return mcc;
            }

            public void setMcc(int mcc) {
                this.mcc = mcc;
            }

            public int getMnc() {
                return mnc;
            }

            public void setMnc(int mnc) {
                this.mnc = mnc;
            }

            public int getNci() {
                return nci;
            }

            public void setNci(int nci) {
                this.nci = nci;
            }

            public int getNid() {
                return nid;
            }

            public void setNid(int nid) {
                this.nid = nid;
            }

            public int getPci() {
                return pci;
            }

            public void setPci(int pci) {
                this.pci = pci;
            }

            public String getRadio_type() {
                return radio_type;
            }

            public void setRadio_type(String radio_type) {
                this.radio_type = radio_type;
            }

            public int getRss() {
                return rss;
            }

            public void setRss(int rss) {
                this.rss = rss;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public int getTac() {
                return tac;
            }

            public void setTac(int tac) {
                this.tac = tac;
            }
        }
    }
}
