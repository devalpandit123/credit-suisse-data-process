package com.test.creditsuisse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataProcessEvent {
    private String id;
    private long duration;
    private boolean alert;
    private String type;
    private String host;


//    public String getId() {
//        return this.id;
//    }
//
//    public long getDuration() {
//        return this.duration;
//    }
//
//    public String getType() {
//        return this.type;
//    }
//
//    public String getHost() {
//        return this.host;
//    }
//
//    public boolean isAlert() {
//        return this.alert;
//    }

//    public static class Builder {
//        private final String id;
//        private final long duration;
//        private final boolean alert;
//        private String type;
//        private String host;
//
//        public Builder(String id, long duration, boolean alert) {
//            this.id = id;
//            this.duration = duration;
//            this.alert = alert;
//        }
//
//        public Builder withType(String type) {
//            this.type = type;
//            return this;
//        }
//
//        public Builder withHost(String host) {
//            this.host = host;
//            return this;
//        }
//
//        public DataProcessEvent build() {
//            DataProcessEvent dataProcessEvent = new DataProcessEvent();
//            dataProcessEvent.alert = this.alert;
//            dataProcessEvent.duration = this.duration;
//            dataProcessEvent.type = this.type;
//            dataProcessEvent.host = this.host;
//            dataProcessEvent.id = this.id;
//            return dataProcessEvent;
//        }
//    }
}
