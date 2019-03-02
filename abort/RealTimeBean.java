package com.hawksjamesf.spacecraft.data.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class RealTimeBean  {
    @Override
    public String toString() {
        return "RealTimeBean{" +
                "status='" + status + '\'' +
                ", lang='" + lang + '\'' +
                ", server_time=" + server_time +
                ", tzshift=" + tzshift +
                ", unit='" + unit + '\'' +
                ", result=" + result +
                ", location=" + location +
                '}';
    }

    private String status;
    private String lang;
    private int server_time;
    private int tzshift;
    private String unit;
    private ResultBean result;
    private List<Double> location;

    public static RealTimeBean objectFromData(String str) {

        return new Gson().fromJson(str, RealTimeBean.class);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public int getTzshift() {
        return tzshift;
    }

    public void setTzshift(int tzshift) {
        this.tzshift = tzshift;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class ResultBean {
        private String status;
        private double temperature;
        private String skycon;
        private int pm25;
        private double cloudrate;
        private double humidity;
        private PrecipitationBean precipitation;
        private WindBean wind;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getSkycon() {
            return skycon;
        }

        public void setSkycon(String skycon) {
            this.skycon = skycon;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public double getCloudrate() {
            return cloudrate;
        }

        public void setCloudrate(double cloudrate) {
            this.cloudrate = cloudrate;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public PrecipitationBean getPrecipitation() {
            return precipitation;
        }

        public void setPrecipitation(PrecipitationBean precipitation) {
            this.precipitation = precipitation;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class PrecipitationBean {
            private NearestBean nearest;
            private LocalBean local;

            public static PrecipitationBean objectFromData(String str) {

                return new Gson().fromJson(str, PrecipitationBean.class);
            }

            public NearestBean getNearest() {
                return nearest;
            }

            public void setNearest(NearestBean nearest) {
                this.nearest = nearest;
            }

            public LocalBean getLocal() {
                return local;
            }

            public void setLocal(LocalBean local) {
                this.local = local;
            }

            public static class NearestBean {
                private String status;
                private double distance;
                private double intensity;

                public static NearestBean objectFromData(String str) {

                    return new Gson().fromJson(str, NearestBean.class);
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public double getIntensity() {
                    return intensity;
                }

                public void setIntensity(double intensity) {
                    this.intensity = intensity;
                }
            }

            public static class LocalBean {
                private String status;
                private double intensity;
                private String datasource;

                public static LocalBean objectFromData(String str) {

                    return new Gson().fromJson(str, LocalBean.class);
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public double getIntensity() {
                    return intensity;
                }

                public void setIntensity(double intensity) {
                    this.intensity = intensity;
                }

                public String getDatasource() {
                    return datasource;
                }

                public void setDatasource(String datasource) {
                    this.datasource = datasource;
                }
            }
        }

        public static class WindBean {
            private double direction;
            private double speed;

            public static WindBean objectFromData(String str) {

                return new Gson().fromJson(str, WindBean.class);
            }

            public double getDirection() {
                return direction;
            }

            public void setDirection(double direction) {
                this.direction = direction;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }
        }
    }
}

