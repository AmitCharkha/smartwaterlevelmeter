package com.vem_tooling.smartwaterlevelmonitor.vo;

import java.io.Serializable;

/**
 * Created by amit on 20/6/17.
 */

public class AlarmSettingVO implements Serializable {

    int id;
    int tankNo;
    int percentage;
    int onOff;
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

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
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
