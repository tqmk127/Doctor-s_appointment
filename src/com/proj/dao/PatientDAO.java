package com.proj.dao;

import com.proj.domain.Patient;
import javafx.collections.ObservableList;


public interface PatientDAO {

    Patient getPatientByID(long idPatient);

    boolean insertPatient(Patient patient);

    boolean updatePatient(Patient patient);

    boolean deletePatient(long idPatient);

    ObservableList<Patient> getAllPatients();

    ObservableList<Patient> getPatientByAddress(String address);

    Patient getFirstPatient();
}
