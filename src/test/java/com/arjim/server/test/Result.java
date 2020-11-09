package com.arjim.server.test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wyy on 2016/4/7.
 */
public class Result {
    public int code;
    public Data1 data;


    public static class Data1{
        public Page page;

    }

    public  static class  Page{
        public List<Data2> data = new ArrayList<>();

    }

    public static class Data2{
        public Comp company;

    }

    public static class Comp{
        public String operationStatus;
        public Date updateDate;
        public String name;
    }
}