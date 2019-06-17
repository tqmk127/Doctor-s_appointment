package com.proj.dao;

import com.proj.domain.Notation;
import com.proj.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.proj.domain.*;

import java.sql.*;

public class NotationDAOImpl implements NotationDAO {

    private Notation extractNotationFromResultSet(ResultSet rs) throws SQLException {

        DoctorDAO doctorDAO = new DoctorDAOImpl();
        PatientDAO patientDAO = new PatientDAOImpl();

        Doctor doctor = doctorDAO.getDoctorByID(rs.getLong("doctorId"));
        Patient patient = patientDAO.getPatientByID(rs.getLong("patientId"));

        Notation notation = new Notation();

        notation.setIdNotation(rs.getLong("idNotation"));
        notation.setDateOfReception(rs.getDate("dateOfReception"));
        notation.setTimeOfReception(rs.getString("timeOfReception"));
        notation.setVisited(rs.getString("visited"));
        notation.setDoctor(doctor);
        notation.setPatient(patient);
        return notation;
    }

    @Override
    public Notation getNotationByID(long idNotation) {//отримання інформаціії по ID запису до лікаря
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM notation WHERE idNotation=?")) {
            ps.setLong(1, idNotation);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return extractNotationFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertNotation(Notation notation) {//додання інформації про запис до лікаря
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO notation(dateOfReception, timeOfReception, visited, doctorId, patientId)"+
                     " VALUES (?, ?, ? ,? ,?)")) {
            ps.setDate(1, notation.getDateOfReception());
            ps.setString(2, notation.getTimeOfReception());
            ps.setString(3,notation.getVisited());
            ps.setLong(4, notation.getDoctor().getIdDoctor());
            ps.setLong(5, notation.getPatient().getIdPatient());
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateNotation(Notation notation) {//оновлення інформації
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE notation set DateOfReception = ?, TimeOfReception = ?"
                     + ",visited = ?, doctorId = ?, patientId = ? WHERE idNotation = ?")
        ) {
            ps.setDate(1, notation.getDateOfReception());
            ps.setString(2, notation.getTimeOfReception());
            ps.setString(3,notation.getVisited());
            ps.setLong(4, notation.getDoctor().getIdDoctor());
            ps.setLong(5, notation.getPatient().getIdPatient());
            ps.setLong(6, notation.getIdNotation());
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteNotation(long idNotation) {//видалення інформації
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM notation WHERE idNotation=?")
        ) {
            ps.setLong(1, idNotation);
            if (ps.executeUpdate() == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<Notation> getAllNotations() {//отримання всієї інформації
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM notation")) {
            ObservableList<Notation> notations = FXCollections.observableArrayList();
            while (rs.next()) {
                notations.add(extractNotationFromResultSet(rs));
            }
            return notations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Notation> getNotationByVisited(String visited) {//отимання інформації за первної умови
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM notation WHERE visited = ?")) {
            ps.setString(1, visited);
            ResultSet rs = ps.executeQuery();
            ObservableList<Notation> patients = FXCollections.observableArrayList();
            while (rs.next()) {
                patients.add(extractNotationFromResultSet(rs));
            }
            return patients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notation getFirstNotation() {//отримання першого запису до лікаря в списку
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM notation ORDER BY idNotation limit 1")) {
            if (rs.next()) {
                return extractNotationFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
