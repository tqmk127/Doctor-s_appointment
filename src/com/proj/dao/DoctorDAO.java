package com.proj.dao;

import com.proj.domain.Doctor;
import javafx.collections.ObservableList;


public interface DoctorDAO {

    Doctor getDoctorByID(long idDoctor);

    boolean insertDoctor(Doctor doctor);

    boolean updateDoctor(Doctor doctor);

    boolean deleteDoctor(long idDoctor);

    ObservableList<Doctor> getAllDoctors();

    ObservableList<Doctor> getDoctorBySpeciality(String specialist);

    Doctor getFirstDoctor();
}
