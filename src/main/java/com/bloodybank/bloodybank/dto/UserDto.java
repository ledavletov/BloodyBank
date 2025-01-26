package com.bloodybank.bloodybank.dto;

public class UserDto {
    private String email;
    private String name;
    private String surname;
    private int age;
    private String bloodName;
    private int bloodId;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getBloodName() {
        return bloodName;
    }

    public int getBloodId(){
        return bloodId;
    }

    public UserDto(String email, String name, String surname, int age, String bloodName, int bloodId) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.bloodName = bloodName;
        this.bloodId = bloodId;
    }
}
