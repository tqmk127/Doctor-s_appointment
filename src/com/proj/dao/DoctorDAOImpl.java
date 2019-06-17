package com.proj.dao;

import com.proj.domain.Doctor;
import com.proj.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DoctorDAOImpl implements DoctorDAO {

    private Doctor extractDoctorFromResultSet(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setIdDoctor(rs.getLong("idDoctor"));
        doctor.setFirstname(rs.getString("firstname"));
        doctor.setMiddlename(rs.getString("middlename"));
        doctor.setLastname(rs.getString("lastname"));
        doctor.setDateOfBirth(rs.getDate("DateOfBirth"));
        doctor.setSpecialist(rs.getString("specialist"));
        return doctor;
    }

    @Override
    public Doctor getDoctorByID(long idDoctor) {//отримання лікаря за його ID
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM doctor WHERE idDoctor=?")) {
            ps.setLong(1, idDoctor);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return extractDoctorFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertDoctor(Doctor doctor) { //створення інформації про лікаря
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO doctor(firstname, middlename, lastname, dateOfBirth, specialist) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, doctor.getFirstname());
            ps.setString(2, doctor.getMiddlename());
            ps.setString(3, doctor.getLastname());
            ps.setDate(4,  doctor.getDateOfBirth());
            ps.setString(5, doctor.getSpecialist());

            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDoctor(Doctor doctor) {//оновлення інформації про лікаря
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE doctor set firstname = ?, middlename = ?, lastname = ?,"+
                     "dateOfBirth = ?, specialist = ?  WHERE idDoctor=?")
        ) {
            ps.setString(1, doctor.getFirstname());
            ps.setString(2, doctor.getMiddlename());
            ps.setString(3, doctor.getLastname());
            ps.setDate(4,  doctor.getDateOfBirth());
            ps.setString(5, doctor.getSpecialist());
            ps.setLong(6, doctor.getIdDoctor());
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDoctor(long idDoctor) {//видалення інформації про лікаря
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM doctor WHERE idDoctor=?")
        ) {
            ps.setLong(1, idDoctor);
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<Doctor> getAllDoctors() {//виведення інформації про всіх лікарів
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM doctor")) {
            ObservableList<Doctor> doctors = FXCollections.observableArrayList();
            while (rs.next()) {
                doctors.add(extractDoctorFromResultSet(rs));
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Doctor> getDoctorBySpeciality(String specialist) {//інформація про лікаря
        try (Connection connection = ConnectionFactory.getConnection();// залежно від спеціальності
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM doctor WHERE specialist = ? ")) {
            ps.setString(1, specialist);
            ResultSet rs = ps.executeQuery();
            ObservableList<Doctor> doctors = FXCollections.observableArrayList();
            while (rs.next()) {
                doctors.add(extractDoctorFromResultSet(rs));
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doctor getFirstDoctor() {//отримання інформація про першого лікаря в списку

        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM doctor ORDER BY idDoctor limit 1")) {
            if (rs.next()) {
                return extractDoctorFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
