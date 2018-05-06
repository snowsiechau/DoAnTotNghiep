package com.example.chaut.hieuthuoc.map.network;

import java.util.List;

/**
 * Created by SNOW on 11/21/2017.
 */

public class MainObjectJSON {
    private List<ResultJSON>  results;

    public List<ResultJSON> getResult() {
        return results;
    }


    public class ResultJSON{
        private GeometryJSON geometry;
        private String name;
        private String vicinity;

        public ResultJSON(GeometryJSON geometry, String name, String vicinity) {
            this.geometry = geometry;
            this.name = name;
            this.vicinity = vicinity;
        }

        public String getName() {
            return name;
        }

        public GeometryJSON getGeometry() {
            return geometry;
        }

        public String getVicinity() {
            return vicinity;
        }

        public class GeometryJSON{
            private LocationJSON location;

            public GeometryJSON(LocationJSON location) {
                this.location = location;
            }

            public LocationJSON getLocation() {
                return location;
            }

            public class LocationJSON{
                private double lat;
                private double lng;

                public LocationJSON(double lat, double lng) {
                    this.lat = lat;
                    this.lng = lng;
                }

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }
            }
        }
    }
}
