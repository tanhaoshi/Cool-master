package mvp.cool.master.mvp.bean;

import java.io.Serializable;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/5/23.
 */

public class Download implements Serializable{

    private String url;
    private int length;
    private int start;
    private int now;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
