package com.example.chaut.hieuthuoc.map.map_distance;

import java.util.List;

/**
 * Created by SNOW on 12/11/2017.
 */

public class MainDistanceObjecJson {
    private List<RowsObject> rows;

    public List<RowsObject> getRows() {
        return rows;
    }

    public class RowsObject{
        private List<ElemetsObject> elements;

        public RowsObject(List<ElemetsObject> elements) {
            this.elements = elements;
        }

        public List<ElemetsObject> getElements() {
            return elements;
        }

        public class ElemetsObject{
            private DistanceJson distance;

            public ElemetsObject(DistanceJson distance) {
                this.distance = distance;
            }

            public DistanceJson getDistance() {
                return distance;
            }

            public class DistanceJson{
                private String text;

                public DistanceJson(String text) {
                    this.text = text;
                }

                public String getText() {
                    return text;
                }
            }
        }
    }
}
