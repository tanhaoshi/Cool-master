package mvp.cool.master.mvp.bean;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/13.
 */

public class OizlType {

    String type;

    Double money;

    public OizlType(String type , Double money){
        this.type = type;
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
