package com.nuist.you.bean.daobean;

import java.util.Map;

public class MessageData {

    /**
     * message : ok
     * code : 1
     * object : {"hour":{"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2},"today":{"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2},"yesterday":{"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2},"year":{"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2}}
     */
    private String message;
    private int code;
    private ObjectBean object;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * hour : {"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2}
         * today : {"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2}
         * yesterday : {"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2}
         * year : {"id":"123","avg_temp":12.1,"avg_hum":111.2,"avg_mq2":111.1,"avg_beam":11.2}
         */
        private Map<String, AverageDataBean> map;

        public Map<String, AverageDataBean> getMap() {
            return map;
        }

        public void setMap(Map<String, AverageDataBean> map) {
            this.map = map;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "map=" + map +
                    '}';
        }
    }
}


