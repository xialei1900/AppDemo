package bean;

/**
 * Created by Administrator on 2017/5/7.
 */

public class PregnancyCheckBean {
    private int checkTimes;
    private int checkWeek;
    private String guidance;
    private String routine;
    private String mustCheck;
    private String referenceCheck;

    public int getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }

    public int getCheckWeek() {
        return checkWeek;
    }

    public void setCheckWeek(int checkWeek) {
        this.checkWeek = checkWeek;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getMustCheck() {
        return mustCheck;
    }

    public void setMustCheck(String mustCheck) {
        this.mustCheck = mustCheck;
    }

    public String getReferenceCheck() {
        return referenceCheck;
    }

    public void setReferenceCheck(String referenceCheck) {
        this.referenceCheck = referenceCheck;
    }


}
