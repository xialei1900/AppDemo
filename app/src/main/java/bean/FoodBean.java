package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FoodBean implements Serializable {

    private int Id;
    private int nameId;
    private int sort;
    private String name;
    private String alias;
    private int yunma;
    private String yunma_desc;
    private String big_pic;
    private String small_pic;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getYunma() {
        return yunma;
    }

    public void setYunma(int yunma) {
        this.yunma = yunma;
    }

    public String getYunma_desc() {
        return yunma_desc;
    }

    public void setYunma_desc(String yunma_desc) {
        this.yunma_desc = yunma_desc;
    }

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

    public String getSmall_pic() {
        return small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }
}
