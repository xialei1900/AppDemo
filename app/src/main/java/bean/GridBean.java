package bean;

/**
 * Created by Administrator on 2017/4/5.
 */

public class GridBean {
    private int iId;
    private String iName;

    public GridBean() {
    }

    public GridBean(int iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }

    public int getiId() {
        return iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
