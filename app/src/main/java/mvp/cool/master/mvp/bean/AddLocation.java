package mvp.cool.master.mvp.bean;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/18.
 */

public class AddLocation {

    String name ;

    String city ;

    String cityDetail;

    String cityRegion;

    String location;

    String phone ;

    public AddLocation(String name, String city, String cityDetail, String cityRegion, String location, String phone) {
        this.name = name;
        this.city = city;
        this.cityDetail = cityDetail;
        this.cityRegion = cityRegion;
        this.location = location;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCityDetail() {
        return cityDetail;
    }

    public void setCityDetail(String cityDetail) {
        this.cityDetail = cityDetail;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }
}
