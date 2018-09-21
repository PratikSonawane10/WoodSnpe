package com.mobitechs.woodsnipe.model;

public class School_Items {

    public String schoolId;
    public String schoolName;
    public String schoolContactNo;
    public String schoolEmail;
    public String schoolAddress;
    public String CheckinDate;
    public String CheckoutDate;


    public School_Items() {
    }

    public School_Items(String CheckinDate, String CheckoutDate,String schoolName, String schoolId, String schoolContactNo, String schoolAddress, String schoolEmail) {

        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolContactNo = schoolContactNo;
        this.schoolEmail = schoolEmail;
        this.schoolAddress = schoolAddress;
        this.CheckinDate = CheckinDate;
        this.CheckoutDate = CheckoutDate;

    }
    

    public String getSchoolContactNo() {
        return schoolContactNo;
    }

    public void setSchoolContactNo(String schoolContactNo) {
        this.schoolContactNo = schoolContactNo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getCheckinDate() {
        return CheckinDate;
    }

    public void setCheckinDate(String CheckinDate) {
        this.CheckinDate = CheckinDate;
    }

    public String getCheckoutDate() {
        return CheckoutDate;
    }

    public void setCheckoutDate(String CheckoutDate) {
        this.CheckoutDate = CheckoutDate;
    }
}