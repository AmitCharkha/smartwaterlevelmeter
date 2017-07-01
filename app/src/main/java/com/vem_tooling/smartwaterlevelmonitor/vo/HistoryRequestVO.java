package com.vem_tooling.smartwaterlevelmonitor.vo;

/**
 * Created by amit on 21/6/17.
 */

public class HistoryRequestVO {

    private int id;
    private int tankNo;
    private int startValue;
    private int endValue;
    private long createdDate;
    private long updatedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTankNo() {
        return tankNo;
    }

    public void setTankNo(int tankNo) {
        this.tankNo = tankNo;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public void setEndValue(int endValue) {
        this.endValue = endValue;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }
}
