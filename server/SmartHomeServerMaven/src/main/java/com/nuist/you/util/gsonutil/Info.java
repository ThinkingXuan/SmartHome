package com.nuist.you.util.gsonutil;

import java.util.List;

/**
 * author: youxuan
 */

public class Info {

    // code 1.信息发送成功  code 2 在线用户发生改变(message里面放人数)。
    /**
     * message : 成功哈哈
     * code : 1
     * object : [{"content":"体贴","time":"Sun Aug 06 18:14:53 GMT+08:00 2017","user_id":"3ca3259d2b2011e787b15ffbce180aea","username":"尤旋"}]
     *
     */

    private String message;
    private int code;
    private List<DataBean> object;

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

    public List<DataBean> getObject() {
        return object;
    }

    public void setObject(List<DataBean> object) {
        this.object = object;
    }

    public static class DataBean {
        /**
         * content : 体贴
         * time : Sun Aug 06 18:14:53 GMT+08:00 2017
         * user_id : 3ca3259d2b2011e787b15ffbce180aea
         * username : 尤旋
         */

        private String content;
        private String time;
        private String user_id;
        private String username;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Info{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", object=" + object +
                '}';
    }
}
