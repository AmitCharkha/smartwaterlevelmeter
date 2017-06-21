package com.vem_tooling.smartwaterlevelmonitor.vo;

/**
 * Created by amit on 21/6/17.
 */

public class HistoryVO {

    private int id;
    private int tankNo;
    private long dateTime;
    private int percentage;
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
