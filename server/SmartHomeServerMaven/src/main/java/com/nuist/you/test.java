package com.nuist.you;

import com.nuist.you.bean.MessageBean;
import com.nuist.you.bean.Users;
import com.nuist.you.dao.User2Dao;
import com.nuist.you.dao.impl.User2DaoImpl;
import com.nuist.you.mapper.UserDao;
import com.nuist.you.services.IUserServices;
import com.nuist.you.services.impl.UserServiceImpl;
import com.nuist.you.util.LogUtil;
import com.nuist.you.util.gsonutil.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;

public class test {
    @Autowired
    static UserDao userDao;
    public static void main(String[] args) throws IOException {
        /*
        //为了简单起见，所有的异常信息都往外抛
        int port = 8887;
        //定义一个ServerSocket监听在端口8899上
        ServerSocket server = new ServerSocket(port);
        //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
        Socket socket = server.accept();
        if (socket.isConnected()){
            System.out.println("连接成功");
        }
        //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
        InputStream inputStream = socket.getInputStream();

        int len = 0 ;
        byte[] bys = new byte[1024];
        while ((len = inputStream.read(bys)) != -1) {
            System.out.println(new String(bys,0,len));
        }
        inputStream.close();

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好".getBytes());
        outputStream.close();

        InputStream inputStream2 = socket.getInputStream();

        int len2 = 0 ;
        byte[] bys2 = new byte[1024];
        while ((len2 = inputStream2.read(bys)) != -1) {
            System.out.println(new String(bys2,0,len2));
        }
        inputStream2.close();
        socket.close();
        server.close();
        */
//
//        String json = "{\"code\":1,\"message\":\"你好\",\"object\":[{\"temperature\":\"11\",\"time\":\"2020年4月5日19:57:33\",\"user_id\":\"尤旋\"}]}";
//        MessageBean messageBean = GsonUtil.getMessageBean(json);
//        LogUtil.info(messageBean.toString());



    }

}
