package com.vem_tooling.smartwaterlevelmonitor.vo;

/**
 * Created by amit on 20/6/17.
 */

public class TankVO {

    int id;
    int tankNo;
    int percentage;
    long createdDate;
    long updatedDate;

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

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
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
