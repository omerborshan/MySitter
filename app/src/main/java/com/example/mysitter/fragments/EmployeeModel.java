package com.example.mysitter.fragments;

public class EmployeeModel {

    private String fullName;
    private int age;
    private int hourlyPrice;
    private String profileImage;
    private String phoneNumber;

    public EmployeeModel() {

    }

    public EmployeeModel(String name, int age, int price, String profileImage, String phoneNumber) {
        setFullName(name);
        setAge(age);
        setHourlyPrice(price);
        setProfileImage(profileImage);
        setPhoneNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(int hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
