package com.zhenmei.testrecyclerview.bean;

/**
 * Created by zhenmei on 16/2/21.
 */
public class News {

    private int touxiang;
    private String biaoti;
    private String neirong;

    public int getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(int touxiang) {
        this.touxiang = touxiang;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public News() {

    }

    public News(int touxiang, String biaoti, String neirong) {
        this.touxiang = touxiang;
        this.biaoti = biaoti;
        this.neirong = neirong;
    }
}
