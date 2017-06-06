package mvp.cool.master.mvp.bean;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/5.
 */

public class OizlModel {

    String oizlModel;

    String oizlMoney;

    public OizlModel(){

    }

    public OizlModel(String oizlModel, String oizlMoney) {
        this.oizlModel = oizlModel;
        this.oizlMoney = oizlMoney;
    }

    public String getOizlModel() {
        return oizlModel;
    }

    public void setOizlModel(String oizlModel) {
        this.oizlModel = oizlModel;
    }

    public String getOizlMoney() {
        return oizlMoney;
    }

    public void setOizlMoney(String oizlMoney) {
        this.oizlMoney = oizlMoney;
    }
}
