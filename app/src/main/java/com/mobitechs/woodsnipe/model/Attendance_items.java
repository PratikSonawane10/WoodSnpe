package com.mobitechs.woodsnipe.model;

public class Attendance_items {

    public String Appointmentid;
    public String Appointment_Type;
    public String Full_Name;
    public String Email;
    public String Phone_no;
    public String purpose;
    public String Message;
    public String Appointment_dates;
    public String Appointment_time;
    public String CountryName;
    public String city;
    public String Hotel_name;
    public String HotelMobileNo;
    public String BookedThrough;
    public String CampaignCode;
    public String Appointmentstatus;
    public String AppointmentDate;
    public String AppointmentMonth;
    public String AppointmentYear;

    public Attendance_items() {
    }

    public Attendance_items(String Appointmentid, String Appointment_Type, String Full_Name, String Email, String Phone_no, String purpose, String Message, String Appointment_dates, String Appointment_time, String CountryName, String city, String Hotel_name, String BookedThrough, String HotelMobileNo, String CampaignCode, String Appointmentstatus, String AppointmentDate, String AppointmentMonth, String AppointmentYear) {

        this.Appointmentid = Appointmentid;
        this.Appointment_Type = Appointment_Type;
        this.Full_Name = Full_Name;
        this.Email = Email;

        this.Phone_no = Phone_no;
        this.purpose = purpose;
        this.Message = Message;
        this.Appointment_dates = Appointment_dates;
        this.Appointment_time = Appointment_time;
        this.CountryName = CountryName;
        this.city = city;
        this.Hotel_name = Hotel_name;
        this.BookedThrough = BookedThrough;
        this.HotelMobileNo = HotelMobileNo;
        this.CampaignCode = CampaignCode;
        this.Appointmentstatus = Appointmentstatus;
        this.AppointmentDate = AppointmentDate;
        this.AppointmentMonth = AppointmentMonth;
        this.AppointmentYear = AppointmentYear;

    }

    public String getcity() {
        return city;
    }
    public void setcity(String city) {
        this.city = city;
    }


    public String getHotelMobileNo() {
        return HotelMobileNo;
    }
    public void setHotelMobileNo(String HotelMobileNo) {
        this.HotelMobileNo = HotelMobileNo;
    }


    public String getCampaignCode() {
        return CampaignCode;
    }
    public void setCampaignCode(String CampaignCode) {
        this.CampaignCode = CampaignCode;
    }


    public String getAppointmentstatus() {
        return Appointmentstatus;
    }
    public void setAppointmentstatus(String Appointmentstatus) {
        this.Appointmentstatus = Appointmentstatus;
    }

    public String getBookedThrough() {
        return BookedThrough;
    }
    public void setBookedThrough(String BookedThrough) {
        this.BookedThrough = BookedThrough;
    }

    public String getHotelName() {
        return Hotel_name;
    }
    public void setHotelName(String Hotel_name) {
        this.Hotel_name = Hotel_name;
    }


    public String getCountryName() {
        return CountryName;
    }
    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

    public String getAppointment_time() {
        return Appointment_time;
    }
    public void setAppointment_time(String Appointment_time) {
        this.Appointment_time = Appointment_time;
    }

    public String getAppointment_dates() {
        return Appointment_dates;
    }
    public void setAppointment_dates(String Appointment_dates) {
        this.Appointment_dates = Appointment_dates;
    }

    public String getMessage() {
        return Message;
    }
    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getpurpose() {
        return purpose;
    }
    public void setpurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPhone_no() {
        return Phone_no;
    }
    public void setPhone_no(String Phone_no) {
        this.Phone_no = Phone_no;
    }

    public String getAppointmentid() {
        return Appointmentid;
    }

    public void setAppointmentid(String Appointmentid) {
        this.Appointmentid = Appointmentid;
    }

    public String getAppointment_Type() {
        return Appointment_Type;
    }

    public void setAppointment_Type(String Appointment_Type) {
        this.Appointment_Type = Appointment_Type;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String Full_Name) {
        this.Full_Name = Full_Name;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }


    public String getPresentDate() {
        return AppointmentDate;
    }
    public void setPresentDate(String AppointmentDate) {
        this.AppointmentDate = AppointmentDate;
    }


    public String getPresentMonth() {
        return AppointmentMonth;
    }
    public void setPresentMonth(String AppointmentMonth) {
        this.AppointmentMonth = AppointmentMonth;
    }


    public String getPresentYear() {
        return AppointmentYear;
    }
    public void setPresentYear(String AppointmentYear) {
        this.AppointmentYear = AppointmentYear;
    }

}