package com.proj.UI;

import com.proj.dao.PatientDAO;
import com.proj.dao.PatientDAOImpl;
import com.proj.domain.Patient;

import com.proj.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class PatientUI extends BorderPane {
    //Label
    private Label msgLabel = new Label();
    //TextField
    private TextField idPatientField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField middleNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField phoneField = new TextField();
    private TextField dateOfBirthField = new TextField();
    private DatePicker birthDatePicker = new DatePicker();
    private TextField numOfPassportField = new TextField();
    private TextField addressField = new TextField();
    //Button
    private Button createButton = new Button("New...");
    private Button updateButton = new Button("Update...");
    private Button deleteButton = new Button("Delete...");
    //TableColumn
    private TableView<Patient> PatientTable = new TableView();
    private TableColumn<Patient, Long> idColumn = new TableColumn("ID");
    private TableColumn<Patient, String> firstNameColumn = new TableColumn("Ім'я");
    private TableColumn<Patient, String> middleNameColumn = new TableColumn("По-батькові");
    private TableColumn<Patient, String> lastNameColumn = new TableColumn("Прізвище");
    private TableColumn<Patient, String> phoneColumn = new TableColumn("Телефон");
    private TableColumn<Patient, Date> dateOfBirthColumn = new TableColumn("Дата народження");
    private TableColumn<Patient, Integer> numOfPassportColumn = new TableColumn("Номер паспорту");
    private TableColumn<Patient, String> addressColumn = new TableColumn("Адреса");
    private ObservableList<Patient> masterData = FXCollections.observableArrayList();
    private PatientDAO PatientDAO = new PatientDAOImpl();

    public PatientUI() {
        this.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.setTop(this.msgLabel);
        this.setCenter(this.initFields());
        this.setBottom(this.initTable());
        this.setRight(this.initButtons());
        this.initListeners();
        this.setFieldData(this.PatientDAO.getFirstPatient());
        this.setTabledData();
        this.initDatePickerEvents(this.birthDatePicker, this.dateOfBirthField);
    }

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

    private Pane initButtons() {
        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(5.0D);
        box.getChildren().add(this.createButton);
        this.createButton.setOnAction(new PatientUI.ButtonHandler((PatientUI.ButtonHandler)null));
        box.getChildren().add(this.updateButton);
        this.updateButton.setOnAction(new PatientUI.ButtonHandler((PatientUI.ButtonHandler)null));
        box.getChildren().add(this.deleteButton);
        this.deleteButton.setOnAction(new PatientUI.ButtonHandler((PatientUI.ButtonHandler)null));
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
        grid.add(new Label("По-Батькові"), 1, 1);
        grid.add(this.middleNameField, 2, 1);
        grid.add(new Label("Прізвище"), 1, 2);
        grid.add(this.lastNameField, 2, 2);
        grid.add(new Label("Телефон"), 1, 3);
        grid.add(this.phoneField, 2, 3);
        grid.add(new Label("Дата народження"), 1, 4);
        grid.add(this.birthDatePicker, 2, 4);
        grid.add(new Label("Номер паспорту"), 1, 5);
        grid.add(this.numOfPassportField, 2, 5);
        grid.add(new Label("Адреса"), 1, 6);
        grid.add(this.addressField, 2, 6);
        return grid;
    }

    private Pane initTable() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        PropertyValueFactory<Patient, Long> idCellValueFactory = new PropertyValueFactory("idPatient");
        this.idColumn.setCellValueFactory(idCellValueFactory);
        PropertyValueFactory<Patient, String> firstNameCellValueFactory = new PropertyValueFactory("firstname");
        this.firstNameColumn.setCellValueFactory(firstNameCellValueFactory);
        PropertyValueFactory<Patient, String> middleNameCellValueFactory = new PropertyValueFactory("middlename");
        this.middleNameColumn.setCellValueFactory(middleNameCellValueFactory);
        PropertyValueFactory<Patient, String> lastNameCellValueFactory = new PropertyValueFactory("lastname");
        this.lastNameColumn.setCellValueFactory(lastNameCellValueFactory);
        PropertyValueFactory<Patient, String> phoneCellValueFactory = new PropertyValueFactory("phone");
        this.phoneColumn.setCellValueFactory(phoneCellValueFactory);
        PropertyValueFactory<Patient, Date> birthDtCellValueFactory = new PropertyValueFactory("dateOfBirth");
        this.dateOfBirthColumn.setCellValueFactory(birthDtCellValueFactory);
        PropertyValueFactory<Patient, Integer> numOfPassportCellValueFactory = new PropertyValueFactory("numOfPassport");
        this.numOfPassportColumn.setCellValueFactory(numOfPassportCellValueFactory);
        PropertyValueFactory<Patient, String> addressCellValueFactory = new PropertyValueFactory("address");
        this.addressColumn.setCellValueFactory(addressCellValueFactory);
        this.PatientTable.getColumns().addAll(new TableColumn[]{this.idColumn, this.firstNameColumn, this.middleNameColumn, this.lastNameColumn, this.phoneColumn, this.dateOfBirthColumn, this.numOfPassportColumn, this.addressColumn});
        pane.getChildren().add(this.PatientTable);
        return pane;
    }

    private void setTabledData() {
        this.masterData = this.PatientDAO.getAllPatients();
        this.PatientTable.setItems(this.masterData);
    }

    private void initListeners() {
        this.PatientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.setFieldData(newSelection);
            }
        });
    }

    private Patient getFieldData() {
        Patient patient = new Patient();
        patient.setIdPatient((long)Integer.parseInt(this.idPatientField.getText()));
        patient.setFirstname(this.firstNameField.getText());
        patient.setMiddlename(this.middleNameField.getText());
        patient.setLastname(this.lastNameField.getText());
        patient.setPhone(this.phoneField.getText());
        patient.setDateOfBirth(DateUtil.convertStringIntoSqlDate(this.dateOfBirthField.getText()));
        patient.setNumOfPassport(Integer.parseInt(this.numOfPassportField.getText()));
        patient.setAddress(this.addressField.getText());
        return patient;
    }

    private void setFieldData(Patient patient) {
        this.idPatientField.setText(String.valueOf(patient.getIdPatient()));
        this.firstNameField.setText(patient.getFirstname());
        this.middleNameField.setText(patient.getMiddlename());
        this.lastNameField.setText(patient.getLastname());
        this.phoneField.setText(patient.getPhone());
        this.dateOfBirthField.setText(String.valueOf(patient.getDateOfBirth()));

        if (patient.getDateOfBirth() != null) {
            this.birthDatePicker.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth())));
        } else {
            this.birthDatePicker.setValue(null);
        }
        this.numOfPassportField.setText(String.valueOf(patient.getNumOfPassport()));
        this.addressField.setText(patient.getAddress());
    }

    private boolean isEmptyFieldData() {
        return (
                        idPatientField.getText().trim().isEmpty()
                        && firstNameField.getText().trim().isEmpty()
                        && middleNameField.getText().trim().isEmpty()
                        && lastNameField.getText().trim().isEmpty()
                        && phoneField.getText().trim().isEmpty())
                        && dateOfBirthField.getText().trim().isEmpty()
                        && numOfPassportField.getText().trim().isEmpty()
                        && addressField.getText().trim().isEmpty();
    }

    private void refreshTable() {
        TableViewFocusModel<Patient> personModelFocused = this.PatientTable.getFocusModel();
        this.setTabledData();
        this.PatientTable.setFocusModel(personModelFocused);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        private ButtonHandler(ButtonHandler buttonHandler) {
        }

        public void handle(ActionEvent e) {
            Patient patient = PatientUI.this.getFieldData();
            if (e.getSource().equals(PatientUI.this.createButton) && PatientUI.this.createButton.getText().equals("Save")) {
                if (PatientUI.this.isEmptyFieldData()) {
                    PatientUI.this.msgLabel.setText("Cannot create an empty record");
                    return;
                }

                if (PatientUI.this.PatientDAO.insertPatient(patient)) {
                    PatientUI.this.msgLabel.setText("New record created successfully.");
                }

                PatientUI.this.createButton.setText("New...");
                PatientUI.this.refreshTable();

            } else if (e.getSource().equals(PatientUI.this.createButton) && PatientUI.this.createButton.getText().equals("New...")) {
                patient.setFirstname(null);
                patient.setMiddlename(null);
                patient.setLastname(null);
                patient.setPhone(null);
                patient.setDateOfBirth(null);
                patient.setNumOfPassport(0);
                patient.setAddress(null);
                PatientUI.this.setFieldData(patient);
                PatientUI.this.createButton.setText("Save");
            } else if (e.getSource().equals(PatientUI.this.updateButton)) {
                if (PatientUI.this.isEmptyFieldData()) {
                    PatientUI.this.msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (PatientUI.this.PatientDAO.updatePatient(patient)) {
                    PatientUI.this.msgLabel.setText("Patient with ID: " + patient.getIdPatient() + " is updated successfully");
                }

                PatientUI.this.refreshTable();
            } else if (e.getSource().equals(PatientUI.this.deleteButton)) {
                if (PatientUI.this.isEmptyFieldData()) {
                    PatientUI.this.msgLabel.setText("Cannot delete an empty record");
                    return;
                }

                patient = PatientUI.this.PatientDAO.getPatientByID(patient.getIdPatient());
                PatientUI.this.PatientDAO.deletePatient(patient.getIdPatient());
                PatientUI.this.msgLabel.setText("Patient with ID:" + patient.getIdPatient() + " is deleted successfully");
                PatientUI.this.refreshTable();
            }

        }
    }
}
