package com.mobitechs.woodsnipe.model;

public class BDay_Member_Items {


    public String empImage;
    public String contactNo;
    public String name;
    public String empId;


    public BDay_Member_Items() {
    }

    public BDay_Member_Items(String contactNo, String empImage, String name, String empId) {

        this.empImage = empImage;
        this.contactNo = contactNo;
        this.name = name;
        this.empId = empId;

    }


    public String getempImage() {
        return empImage;
    }

    public void setempImage(String empImage) {
        this.empImage = empImage;
    }


    public String getcontactNo() {
        return contactNo;
    }

    public void setcontactNo(String contactNo) {
        this.contactNo = contactNo;
    }


    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getempId() {
        return empId;
    }

    public void setempId(String empId) {
        this.empId = empId;
    }


}
