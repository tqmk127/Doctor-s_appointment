package com.proj.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Doctor {

    private long idDoctor;//ID доктора
    private String firstname;//ім'я
    private String middlename;//по-батькові
    private String lastname;//прізвище
    private Date dateOfBirth;//дата народження
    private String specialist;//спеціаліст

    public Doctor() {
        super();
    }

    //конструктори з параметрами
    public Doctor(long idDoctor, String firstname, String middlename, String lastname, Date dateOfBirth, String specialist){
        this.idDoctor= idDoctor;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.specialist = specialist;
    }

    public Doctor( String firstname, String middlename, String lastname, Date dateOfBirth, String specialist){
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.specialist = specialist;
    }

    public Doctor(String specialist, long idDoctor) {
        this.specialist = specialist;
        this.idDoctor= idDoctor;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "idDoctor=" + idDoctor +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth) +
                ", specialist='" + specialist + '\'' +
                '}';
    }

    //гетери та сетери
    public void setIdDoctor(long idDoctor) { this.idDoctor = idDoctor; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setMiddlename(String middlename) { this.middlename = middlename; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setSpecialist(String specialist) { this.specialist = specialist; }

    public long getIdDoctor() { return idDoctor; }

    public String getFirstname() { return firstname; }

    public String getMiddlename() { return middlename; }

    public String getLastname() { return lastname; }

    public Date getDateOfBirth() { return dateOfBirth; }

    public String getSpecialist() { return specialist; }
}
