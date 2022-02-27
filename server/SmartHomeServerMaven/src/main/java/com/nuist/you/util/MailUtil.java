package com.nuist.you.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    public static void SendMail(String sendAddress, String subject, String content) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.163.com");   // smtp服务器地址

        Session session = Session.getInstance(props);
        session.setDebug(true);

        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setText(content);
        msg.setFrom(new InternetAddress("you3xuan@163.com"));//发件人邮箱(我的163邮箱)
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(sendAddress)); //收件人邮箱(我的QQ邮箱)
        msg.saveChanges();

        Transport transport = session.getTransport();
        transport.connect("you3xuan@163.com", "FROBGVQTFIWDRIHQ");//发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)

        transport.sendMessage(msg, msg.getAllRecipients());

        System.out.println("邮件发送成功...");
        transport.close();
    }

    public static void main(String[] args) throws Exception {
        SendMail("973676315@qq.com", "测试邮箱功能呢？", "你好");
    }
}
