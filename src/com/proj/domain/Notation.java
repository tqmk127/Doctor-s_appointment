package com.proj.domain;

import java.sql.Date;

public class Notation {

    private long idNotation;//ID запису
    private Date dateOfReception;//дата запису
    private String timeOfReception;//час запису
    private String visited;//відвідано
    private Doctor doctor;//лікар
    private Patient patient;//пацієнт

    public Notation(){
        super();
    }

//конструктори с параметрами
    public Notation(long idNotation, Date dateOfReception, String timeOfReception, String visited) {
        this.idNotation = idNotation;
        this.dateOfReception = dateOfReception;
        this.timeOfReception = timeOfReception;
        this.visited = visited;
    }

    public Notation( Date dateOfReception, String timeOfReception, String visited) {
        this.dateOfReception = dateOfReception;
        this.timeOfReception = timeOfReception;
        this.visited = visited;
    }

    public Notation(String visited, long idNotation) {
        this.visited = visited;
        this.idNotation = idNotation;
    }


    @Override
    public String toString() {
        return "Notation{" +
                "idNotation=" + idNotation +
                ", dateOfReception=" + dateOfReception +
                ", timeOfReception='" + timeOfReception + '\'' +
                ", visited='" + visited + '\'' +
                '}';
    }

    //гетери та сетери
    public void setIdNotation(long idNotation) { this.idNotation = idNotation; }
    public void setDateOfReception(Date dateOfReception) {this.dateOfReception = dateOfReception; }
    public void setTimeOfReception(String timeOfReception) { this.timeOfReception = timeOfReception; }
    public void setVisited(String visited) { this.visited = visited; }
    public long getIdNotation() { return idNotation; }
    public Date getDateOfReception() { return dateOfReception; }
    public String getTimeOfReception() { return timeOfReception; }
    public String getVisited() { return visited; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Doctor getDoctor(){ return this.doctor; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Patient getPatient(){ return this.patient; }
}
