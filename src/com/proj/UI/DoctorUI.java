package com.proj.UI;


import com.proj.dao.DoctorDAO;
import com.proj.dao.DoctorDAOImpl;
import com.proj.domain.Doctor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.proj.DateUtil;
import java.time.LocalDate;
import java.util.Date;

public class DoctorUI extends BorderPane {
    //Label
    private Label msgLabel = new Label();
    //TextField
    private TextField idDoctorField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField middleNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField dateOfBirthField = new TextField();
    private DatePicker birthDatePicker = new DatePicker();
    private TextField specialistField = new TextField();
    //Button
    private Button createButton = new Button("New...");
    private Button updateButton = new Button("Update...");
    private Button deleteButton = new Button("Delete...");
    //TableColumn
    private TableView<Doctor> doctorTable = new TableView();
    private TableColumn<Doctor, Long> idColumn = new TableColumn("ID");
    private TableColumn<Doctor, String> firstNameColumn = new TableColumn("Ім'я");
    private TableColumn<Doctor, String> middleNameColumn = new TableColumn("По-батькові");
    private TableColumn<Doctor, String> lastNameColumn = new TableColumn("Прізвище");
    private TableColumn<Doctor, Date> dateOfBirthColumn = new TableColumn("Дата народження");
    private TableColumn<Doctor, String> specialistColumn = new TableColumn("Спеціаліст");



    private ObservableList<Doctor> masterData = FXCollections.observableArrayList();
    private DoctorDAO doctorDAO = new DoctorDAOImpl();

    private void initDatePickerEvents(DatePicker datePicker, TextField textField) {
        datePicker.setOnAction((e) -> {
            LocalDate date = datePicker.getValue();
            if (date != null) {
                textField.setText(date.toString());
            } else {
                textField.setText(null);
            }
        });
    }

    public DoctorUI() {

        this.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.setTop(this.msgLabel);
        this.setCenter(this.initFields());
        this.setBottom(this.initTable());
        this.setRight(this.initButtons());
        this.initListeners();
        this.setFieldData(this.doctorDAO.getFirstDoctor());
        this.setTabledData();
        this.initDatePickerEvents(this.birthDatePicker, this.dateOfBirthField);
    }

    private Pane initButtons() {
        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(5.0D);
        box.getChildren().add(this.createButton);
        this.createButton.setOnAction(new DoctorUI.ButtonHandler((DoctorUI.ButtonHandler)null));
        box.getChildren().add(this.updateButton);
        this.updateButton.setOnAction(new DoctorUI.ButtonHandler((DoctorUI.ButtonHandler)null));
        box.getChildren().add(this.deleteButton);
        this.deleteButton.setOnAction(new DoctorUI.ButtonHandler((DoctorUI.ButtonHandler)null));
        return box;
    }

    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        grid.setHgap(20.0D);
        grid.setVgap(2.0D);
        grid.add(new Label("Ім'я"), 1, 0);
        grid.add(this.firstNameField, 2, 0);
        grid.add(new Label("По-батькові"), 1, 1);
        grid.add(this.middleNameField, 2, 1);
        grid.add(new Label("Прізвище"), 1, 2);
        grid.add(this.lastNameField, 2, 2);
        grid.add(new Label("Дата народження"), 1, 3);
        grid.add(this.birthDatePicker, 2, 3);
        grid.add(new Label("Спеціаліст"), 1, 4);
        grid.add(this.specialistField, 2, 4);
        return grid;
    }

    private Pane initTable() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.doctorTable.setMinHeight(490.0D);
        PropertyValueFactory<Doctor, Long> idCellValueFactory = new PropertyValueFactory("idDoctor");
        this.idColumn.setCellValueFactory(idCellValueFactory);

        PropertyValueFactory<Doctor, String> firstNameCellValueFactory = new PropertyValueFactory("firstname");
        this.firstNameColumn.setCellValueFactory(firstNameCellValueFactory);

        PropertyValueFactory<Doctor, String> middleNameCellValueFactory = new PropertyValueFactory("middlename");
        this.middleNameColumn.setCellValueFactory(middleNameCellValueFactory);

        PropertyValueFactory<Doctor, String> lastNameCellValueFactory = new PropertyValueFactory("lastname");
        this.lastNameColumn.setCellValueFactory(lastNameCellValueFactory);

        PropertyValueFactory<Doctor, Date> birthCellValueFactory = new PropertyValueFactory("dateOfBirth");
        this.dateOfBirthColumn.setCellValueFactory(birthCellValueFactory);

        PropertyValueFactory<Doctor, String> specialistCellValueFactory = new PropertyValueFactory("specialist");
        this.specialistColumn.setCellValueFactory(specialistCellValueFactory);

        this.doctorTable.getColumns().addAll(new TableColumn[]{this.idColumn, this.firstNameColumn,
                this.middleNameColumn, this.lastNameColumn, this.dateOfBirthColumn, this.specialistColumn});
        pane.getChildren().add(this.doctorTable);
        return pane;
    }

    private void setTabledData() {
        this.masterData = this.doctorDAO.getAllDoctors();
        this.doctorTable.setItems(this.masterData);
    }

    private void initListeners() {
        this.doctorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.setFieldData(newSelection);
            }
        });
    }

    private Doctor getFieldData() {
        Doctor doctor = new Doctor();
        doctor.setIdDoctor((long)Integer.parseInt(this.idDoctorField.getText()));
        doctor.setFirstname(this.firstNameField.getText());
        doctor.setMiddlename(this.middleNameField.getText());
        doctor.setLastname(this.lastNameField.getText());
        doctor.setDateOfBirth(DateUtil.convertStringIntoSqlDate(this.dateOfBirthField.getText()));
        doctor.setSpecialist(this.specialistField.getText());
        return doctor;
    }

    private void setFieldData(Doctor doctor) {

        this.idDoctorField.setText(String.valueOf(doctor.getIdDoctor()));
        this.firstNameField.setText(doctor.getFirstname());
        this.middleNameField.setText(doctor.getMiddlename());
        this.lastNameField.setText(doctor.getLastname());

        if (doctor.getDateOfBirth() != null) {
            this.birthDatePicker.setValue(doctor.getDateOfBirth().toLocalDate());
        } else {
            this.birthDatePicker.setValue(null);
        }
        this.specialistField.setText(doctor.getSpecialist());
    }


    private boolean isEmptyFieldData() {
        return (
                   idDoctorField.getText().trim().isEmpty()
                && firstNameField.getText().trim().isEmpty()
                && middleNameField.getText().trim().isEmpty()
                && lastNameField.getText().trim().isEmpty()
                && dateOfBirthField.getText().trim().isEmpty()
                && specialistField.getText().trim().isEmpty());
    }

    private void refreshTable() {
        TableViewFocusModel<Doctor> bookModelFocused = this.doctorTable.getFocusModel();
        this.setTabledData();
        this.doctorTable.setFocusModel(bookModelFocused);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        private ButtonHandler(ButtonHandler buttonHandler) {
        }

        public void handle(ActionEvent e) {
            Doctor doctor = DoctorUI.this.getFieldData();
            if (e.getSource().equals(DoctorUI.this.createButton) && DoctorUI.this.createButton.getText().equals("Save")) {
                if (DoctorUI.this.isEmptyFieldData()) {
                    DoctorUI.this.msgLabel.setText("Cannot create an empty record");
                    return;
                }

                if (DoctorUI.this.doctorDAO.insertDoctor(doctor)) {
                    DoctorUI.this.msgLabel.setText("New doctor created successfully.");
                }

                DoctorUI.this.createButton.setText("New...");
                DoctorUI.this.refreshTable();

            } else if (e.getSource().equals(DoctorUI.this.createButton) && DoctorUI.this.createButton.getText().equals("New...")) {

                doctor.setFirstname((String)null);
                doctor.setMiddlename((String)null);
                doctor.setLastname((String)null);
                doctor.setDateOfBirth(null);
                doctor.setSpecialist((String)null);
                DoctorUI.this.setFieldData(doctor);
                DoctorUI.this.createButton.setText("Save");

            } else if (e.getSource().equals(DoctorUI.this.updateButton)) {
                if (DoctorUI.this.isEmptyFieldData()) {
                    DoctorUI.this.msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (DoctorUI.this.doctorDAO.updateDoctor(doctor)) {
                    DoctorUI.this.msgLabel.setText("Doctor with ID:" + doctor.getIdDoctor() + " is updated successfully");
                }

                DoctorUI.this.refreshTable();
            } else if (e.getSource().equals(DoctorUI.this.deleteButton)) {
                if (DoctorUI.this.isEmptyFieldData()) {
                    DoctorUI.this.msgLabel.setText("Cannot delete an empty record");
                    return;
                }

                doctor = DoctorUI.this.doctorDAO.getDoctorByID(doctor.getIdDoctor());
                DoctorUI.this.doctorDAO.deleteDoctor(doctor.getIdDoctor());
                DoctorUI.this.msgLabel.setText("Doctor with ID:" + doctor.getIdDoctor() + " is deleted successfully");
                DoctorUI.this.refreshTable();
            }

        }
    }
}
