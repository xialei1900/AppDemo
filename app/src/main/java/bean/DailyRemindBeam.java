package bean;

/**
 * Created by Administrator on 2017/4/2.
 */

public class DailyRemindBeam {
    private String babyPhysical;
    private String momPhysical;
    private int checkTimes;
    private int checkWeek;
    private int week;
    private int day;
    private String nutritionRecipes;

    public String getBabyPhysical(){
        return babyPhysical;
    }
    public void setBabyPhysical(String babyPhysical){
        this.babyPhysical = babyPhysical;
    }

    public String getMomPhysical(){
        return momPhysical;
    }
    public void setMomPhysical(String momPhysical){
        this.momPhysical = momPhysical;
    }

    public int getCheckTimes(){
        return checkTimes;
    }
    public void setCheckTimes(int checkTimes){
        this.checkTimes = checkTimes;
    }

    public int getCheckWeek(){
        return checkWeek;
    }
    public void setCheckWeek(int checkWeek){
        this.checkWeek = checkWeek;
    }

    public int getWeek(){
        return week;
    }
    public void setWeek(int week){
        this.week = week;
    }

    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public String getNutritionRecipes(){
        return nutritionRecipes;
    }
    public void setNutritionRecipes(String nutritionRecipes){
        this.nutritionRecipes = nutritionRecipes;
    }
}
