package com.wyp.excelDemo;

import java.util.Date;

/**
 * Class TNaNavyPeople
 * PackageName com.wyp.excelDemo
 * DATE 2020/2/25 20:29
 * Describe
 */
public class TNaNavyPeople {

    private String navyId;

    private String navyName;

    private String navyDuty;

    private String navyOutlook;

    private String navyPhone;

    private String navyWeixin;

    private String navyField;

    private String workId;

    private String navyRemarks;

    private String navyPerson;

    private String navyPersonId;

    private String fillPhone;

    private Integer navyWorkload;

    private String workloadRemarks;

    private String teamId;

    private Date navyCreateTime;

    public String getNavyId() {
        return navyId;
    }

    public void setNavyId(String navyId) {
        this.navyId = navyId;
    }

    public String getNavyName() {
        return navyName;
    }

    public void setNavyName(String navyName) {
        this.navyName = navyName;
    }

    public String getNavyDuty() {
        return navyDuty;
    }

    public void setNavyDuty(String navyDuty) {
        this.navyDuty = navyDuty;
    }

    public String getNavyOutlook() {
        return navyOutlook;
    }

    public void setNavyOutlook(String navyOutlook) {
        this.navyOutlook = navyOutlook;
    }

    public String getNavyPhone() {
        return navyPhone;
    }

    public void setNavyPhone(String navyPhone) {
        this.navyPhone = navyPhone;
    }

    public String getNavyWeixin() {
        return navyWeixin;
    }

    public void setNavyWeixin(String navyWeixin) {
        this.navyWeixin = navyWeixin;
    }

    public String getNavyField() {
        return navyField;
    }

    public void setNavyField(String navyField) {
        this.navyField = navyField;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getNavyRemarks() {
        return navyRemarks;
    }

    public void setNavyRemarks(String navyRemarks) {
        this.navyRemarks = navyRemarks;
    }

    public String getNavyPerson() {
        return navyPerson;
    }

    public void setNavyPerson(String navyPerson) {
        this.navyPerson = navyPerson;
    }

    public String getNavyPersonId() {
        return navyPersonId;
    }

    public void setNavyPersonId(String navyPersonId) {
        this.navyPersonId = navyPersonId;
    }

    public String getFillPhone() {
        return fillPhone;
    }

    public void setFillPhone(String fillPhone) {
        this.fillPhone = fillPhone;
    }

    public Integer getNavyWorkload() {
        return navyWorkload;
    }

    public void setNavyWorkload(Integer navyWorkload) {
        this.navyWorkload = navyWorkload;
    }

    public String getWorkloadRemarks() {
        return workloadRemarks;
    }

    public void setWorkloadRemarks(String workloadRemarks) {
        this.workloadRemarks = workloadRemarks;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getNavyCreateTime() {
        return navyCreateTime;
    }

    public void setNavyCreateTime(Date navyCreateTime) {
        this.navyCreateTime = navyCreateTime;
    }

    public TNaNavyPeople() {
        super();
    }


    @Override
    public String toString() {
        return "TNaNavyPeople{" +
                "navyId='" + navyId + '\'' +
                ", navyName='" + navyName + '\'' +
                ", navyDuty='" + navyDuty + '\'' +
                ", navyOutlook='" + navyOutlook + '\'' +
                ", navyPhone='" + navyPhone + '\'' +
                ", navyWeixin='" + navyWeixin + '\'' +
                ", navyField='" + navyField + '\'' +
                ", workId='" + workId + '\'' +
                ", navyRemarks='" + navyRemarks + '\'' +
                ", navyPerson='" + navyPerson + '\'' +
                ", navyPersonId='" + navyPersonId + '\'' +
                ", fillPhone='" + fillPhone + '\'' +
                ", navyWorkload=" + navyWorkload +
                ", workloadRemarks='" + workloadRemarks + '\'' +
                ", teamId='" + teamId + '\'' +
                ", navyCreateTime=" + navyCreateTime +
                '}';
    }
}
