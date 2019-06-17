package com.proj.dao;

import com.proj.domain.Patient;
import com.proj.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.util.Observable;

public class PatientDAOImpl implements PatientDAO {

    private Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setIdPatient(rs.getLong("idPatient"));
        patient.setFirstname(rs.getString("firstname"));
        patient.setMiddlename(rs.getString("middlename"));
        patient.setLastname(rs.getString("lastname"));
        patient.setPhone(rs.getString("phone"));
        patient.setDateOfBirth(rs.getDate("dateOfBirth"));
        patient.setNumOfPassport(rs.getInt("numOfPassport"));
        patient.setAddress(rs.getString("address"));
        return patient;
    }

    @Override
    public Patient getPatientByID(long idPatient) {//отримання інформації про пацієнта по ID
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM patient WHERE idPatient=?")) {
            ps.setLong(1, idPatient);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return extractPatientFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertPatient(Patient patient) {//додати інформацію про пацієнта
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO patient(firstname, middlename,"+
                     "lastname, phone, dateOfBirth, numOfPassport, address) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, patient.getFirstname());
            ps.setString(2, patient.getMiddlename());
            ps.setString(3, patient.getLastname());
            ps.setString(4, patient.getPhone());
            ps.setDate(5, (Date) patient.getDateOfBirth());
            ps.setInt(6, patient.getNumOfPassport());
            ps.setString(7, patient.getAddress());
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePatient(Patient patient) {//оновити інформацію
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE patient set firstname = ?, middlename = ?, lastname = ?,"+
                     "phone = ?, dateOfBirth = ?, numOfPassport = ?, address = ? WHERE idPatient = ?")
        ) {
            ps.setString(1, patient.getFirstname());
            ps.setString(2, patient.getMiddlename());
            ps.setString(3, patient.getLastname());
            ps.setString(4, patient.getPhone());
            ps.setDate(5, (Date) patient.getDateOfBirth());
            ps.setInt(6, patient.getNumOfPassport());
            ps.setString(7, patient.getAddress());
            ps.setLong(8, patient.getIdPatient());

            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePatient(long idPatient) {//видалити інформацію
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM patient WHERE idPatient=?")
        ) {
            ps.setLong(1, idPatient);
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<Patient> getAllPatients() {//отримати інформацію про всіх пацієнтів
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patient")) {
            ObservableList<Patient> patients = FXCollections.observableArrayList();
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  ObservableList<Patient> getPatientByAddress(String address) {//отримати всю інформацію за певною умовою
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM patient WHERE address = ? ")) {
            ps.setString(1, address);
            ResultSet rs = ps.executeQuery();
            ObservableList<Patient> patients = FXCollections.observableArrayList();
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Patient getFirstPatient() {//отримання інформації про першого пацієнта в списку
            try (Connection connection = ConnectionFactory.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM patient ORDER BY idPatient limit 1")) {
                if (rs.next()) {
                    return extractPatientFromResultSet(rs);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
    }
}
