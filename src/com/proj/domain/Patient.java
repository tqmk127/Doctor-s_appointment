package com.proj.domain;

import java.util.Date;

public class Patient {

    private long idPatient;//ID пацієнта
    private String firstname;//ім'я
    private String middlename;//по-батькові
    private String lastname;//прізвище
    private String phone;//телефон
    private Date dateOfBirth;//дата народження
    private int numOfPassport;//номер паспорта
    private String address;//адреса

    public Patient() {
        super();
    }

    //конструктори з параметрами
    public Patient(long idPatient, String firstname, String middlename,
                   String lastname, String phone, Date dateOfBirth, int numOfPassport, String address) {
        this.idPatient = idPatient;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.numOfPassport = numOfPassport;
        this.address = address;
    }

    public Patient( String firstname, String middlename,
                   String lastname, String phone, Date dateOfBirth, int numOfPassport, String address) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.numOfPassport = numOfPassport;
        this.address = address;
    }

    public Patient(String phone, String address, long idPatient) {
        this.phone = phone;
        this.address = address;
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", numOfPassport=" + numOfPassport +
                ", address='" + address + '\'' +
                '}';
    }

    //гетери та сетери
    public void setIdPatient(long idPatient) { this.idPatient = idPatient; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setMiddlename(String middlename) { this.middlename = middlename; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setNumOfPassport(int numOfPassport) { this.numOfPassport = numOfPassport; }
    public void setAddress(String address) { this.address = address; }
    public long getIdPatient() { return idPatient; }
    public String getFirstname() { return firstname; }
    public String getMiddlename() { return middlename; }
    public String getLastname() { return lastname; }
    public String getPhone() { return phone; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public int getNumOfPassport() { return numOfPassport; }
    public String getAddress() { return address; }
}

