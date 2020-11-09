package com.arjim.server.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.*;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class CompanyList {


    public static void main(String[] args) throws Exception {


        /*  1.设置登录和登录后要进入的接口网址*/

        String loginUrl = "http://passport.36kr.com/***/sign_in";    //设置登录页面的接口URL
        String companyUrl = "http://rongtest06.36kr.com/*********"; //设置获取公司列表的URL
        String loginVerify = "http://rongtest06.36kr.com/*****";


        /*2.进行登录*/
        HttpClient httpClient = HttpClients.createDefault();  //定义一个连接器
        HttpPost post = new HttpPost(loginUrl);  //设置请求发送方式
        post.setHeader("User-Agent", "Chrome");
        post.setHeader("Referer", "passport.36kr.com");
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("username", "17710192039"));
        data.add(new BasicNameValuePair("password", "123123123"));
        data.add(new BasicNameValuePair("type", "login"));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data);
        post.setEntity(formEntity);   //UrlEncodedFormEntity类创建的对象可以模拟传统的HTML表单传送POST请求中的参数

        /* 通过响应头中的cookie来检验cookie是否形成*/
        HttpResponse response = httpClient.execute(post);//执行post请求
        HttpEntity entity = response.getEntity();   //打印出响应的内容
        System.out.println("登录后返回的内容" + EntityUtils.toString(entity, "utf-8"));
        System.out.println("第一条cookies是" + response.getFirstHeader("set-cookie"));
        System.out.println("最后一条cookies是" + response.getLastHeader("set-cookie"));
        Header[] hs = response.getHeaders("Set-Cookie");
        System.out.println("cookies的数量是" + hs.length);

        /* 3.get请求登录后身份认证的接口，判断是否登录成功*/

        HttpGet get1 = new HttpGet(loginVerify);
        HttpResponse response2 = httpClient.execute(get1);
        HttpEntity entity2 = response2.getEntity();
        System.out.println("登录成功后的返回信息：" + EntityUtils.toString(entity2, "utf-8"));

        /*4.获取公司列表*/
        HttpGet get = new HttpGet(companyUrl);
        HttpResponse response1 = httpClient.execute(get);
        HttpEntity entity1 = response1.getEntity();
        String e = EntityUtils.toString(entity1, "utf-8");
        System.out.println("获取公司列表返回的json信息为：" + e);


        /* 5.对返回的信息进行json解析*/

        Result result = com.alibaba.fastjson.JSONObject.parseObject(e, Result.class);//将bean和json中的key一一对应
        JSONObject jsonObject = JSONObject.fromObject(e);//一层层剥离，首先是将jsonstring转化为json对象
        String a = jsonObject.getString("data");//提取出json对象的key：data的值，为json字符串
        JSONObject jsonObject1 = JSONObject.fromObject(a);//将data的值，json字符串转化为json对象
        JSONArray array = jsonObject1.getJSONObject("page").getJSONArray("data");//获取company对象个数
        for (int i = 0; i < array.size(); i++) {
            System.out.println("公司列表为：" + result.data.page.data.get(i).company.name);//for循环输出company的name值，ok！！！！！
        }

    }
}
