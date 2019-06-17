package com.proj.UI;

import com.proj.dao.*;
import com.proj.domain.*;
import java.sql.Date;
import com.proj.DateUtil;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NotationUI extends BorderPane {
    //Label
    private Label msgLabel = new Label();
    //ComboBox
    private ComboBox<Doctor> doctorComboBox = new ComboBox();
    private ComboBox<Patient> patientComboBox = new ComboBox();
    //TextField
    private TextField idField = new TextField();
    private TextField dateOfReceptionField = new TextField();
    private TextField timeOfReceptionField = new TextField();
    private TextField visitedField = new TextField();
    //DataPicker
    private DatePicker dateOfRecPicker = new DatePicker();
    //Button
    private Button createButton = new Button("New...");
    private Button updateButton = new Button("Update...");
    private Button deleteButton = new Button("Delete...");
    private Button refreshButton = new Button("Refresh...");
    private Button doctorButton = new Button("Doctors...");
    private Button patientButton = new Button("Patients...");
    //TableColumn
    private TableView<Notation> notationTable = new TableView();

    private TableColumn<Notation, Long> idColumn = new TableColumn("ID");
    private TableColumn<Notation, Date> dateOfReceptionColumn = new TableColumn("Дата прийому");
    private TableColumn<Notation, String> timeOfReceptionColumn = new TableColumn("Час прийому");
    private TableColumn<Notation, String> visitedColumn = new TableColumn("Відвідано");
    private TableColumn<Notation, Doctor> doctorColumn = new TableColumn("Лікарі");
    private TableColumn<Notation, Patient> patientColumn = new TableColumn("Пацієнти");

    private ObservableList<Notation> masterData = FXCollections.observableArrayList();

    private NotationDAO notationDAO = new NotationDAOImpl();
    private DoctorDAO doctorDAO = new DoctorDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();

    public NotationUI() {
        this.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.setTop(this.msgLabel);
        this.setCenter(this.initFields());
        this.setBottom(this.initTable());
        this.setRight(this.initButtons());
        this.initListeners();
        this.setFieldData(this.notationDAO.getFirstNotation());
        this.setTabledData();
        this.initDatePickerEvents(this.dateOfRecPicker, this.dateOfReceptionField);
    }

    private void initDatePickerEvents(DatePicker datePicker, TextField textField) {
        datePicker.setOnAction((e) -> {
            LocalDate date = (datePicker.getValue());
            if (date != null) {
                textField.setText(date.toString());
            } else {
                textField.setText(null);
            }

        });
    }

    private Pane initButtons() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        grid.setHgap(20.0D);
        grid.setVgap(20.0D);
        grid.add(this.createButton, 0, 0);
        this.createButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        grid.add(this.updateButton, 1, 0);
        this.updateButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        grid.add(this.deleteButton, 2, 0);
        this.deleteButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        grid.add(this.doctorButton, 0, 1);
        this.doctorButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        grid.add(this.patientButton, 1, 1);
        this.patientButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        grid.add(this.refreshButton, 2, 1);
        this.refreshButton.setOnAction(new NotationUI.ButtonHandler((NotationUI.ButtonHandler)null));
        return grid;
    }

    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        grid.setHgap(20.0D);
        grid.setVgap(2.0D);

        this.doctorComboBox.getItems().addAll(this.doctorDAO.getAllDoctors());
        this.doctorComboBox.setMaxWidth(350.0D);

        this.patientComboBox.getItems().addAll(this.patientDAO.getAllPatients());
        this.patientComboBox.setMaxWidth(350.0D);
//Top
        grid.add(new Label("Дата прийому"), 1, 0);
        grid.add(this.dateOfRecPicker, 2, 0);
        grid.add(new Label("Час прийому"), 1, 2);
        grid.add(this.timeOfReceptionField, 2, 2);
        grid.add(new Label("Відвідано"), 1, 3);
        grid.add(this.visitedField, 2, 3);
        grid.add(new Label("Лікар"), 1, 4);
        grid.add(this.doctorComboBox, 2, 4);
        grid.add(new Label("Пацієнт"), 1, 5);
        grid.add(this.patientComboBox, 2, 5);
        return grid;
    }

/////////////////////// OK //////////////////////////

    private Pane initTable() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.notationTable.setMinHeight(560.0D);

        PropertyValueFactory<Notation, Long> idCellValueFactory = new PropertyValueFactory("idNotation");
        this.idColumn.setCellValueFactory(idCellValueFactory);

        PropertyValueFactory<Notation, Date> dateOfRecCellValueFactory = new PropertyValueFactory("dateOfReception");
        this.dateOfReceptionColumn.setCellValueFactory(dateOfRecCellValueFactory);

        PropertyValueFactory<Notation, String> timeOfRecCellValueFactory = new PropertyValueFactory("timeOfReception");
        this.timeOfReceptionColumn.setCellValueFactory(timeOfRecCellValueFactory);

        PropertyValueFactory<Notation, String> visitedCellValueFactory = new PropertyValueFactory("visited");
        this.visitedColumn.setCellValueFactory(visitedCellValueFactory);

        PropertyValueFactory<Notation, Doctor> doctorCellValueFactory = new PropertyValueFactory("doctor");
        this.doctorColumn.setCellValueFactory(doctorCellValueFactory);

        PropertyValueFactory<Notation, Patient> patientCellValueFactory = new PropertyValueFactory("patient");
        this.patientColumn.setCellValueFactory(patientCellValueFactory);

        this.notationTable.getColumns().addAll(new TableColumn[]{this.idColumn, this.dateOfReceptionColumn,
                                                                this.timeOfReceptionColumn, this.visitedColumn, this.doctorColumn,
                                                                this.patientColumn,});
        pane.getChildren().add(this.notationTable);
        return pane;
    }

    private void setTabledData() {
        this.masterData = this.notationDAO.getAllNotations();
        this.notationTable.setItems(this.masterData);
    }

    private void initListeners() {
        this.notationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.setFieldData(newSelection);
            }
        });
    }

    private Notation getFieldData() {

        Notation notation = new Notation();
        Doctor doctor = this.doctorComboBox.getValue();
        Patient patient = this.patientComboBox.getValue();

        notation.setIdNotation((long)Integer.parseInt(this.idField.getText()));
        notation.setDateOfReception(DateUtil.convertStringIntoSqlDate(this.dateOfReceptionField.getText()));
        notation.setTimeOfReception(this.timeOfReceptionField.getText());
        notation.setVisited(this.visitedField.getText());
        notation.setDoctor(doctor);
        notation.setPatient(patient);
        return notation;
    }


    private void setFieldData(Notation notation) {
        this.idField.setText(String.valueOf(notation.getIdNotation()));

        this.dateOfReceptionField.setText(String.valueOf(notation.getDateOfReception()));

        if (notation.getDateOfReception() != null) {
            this.dateOfRecPicker.setValue(notation.getDateOfReception().toLocalDate());
        } else {
            this.dateOfRecPicker.setValue(null);
        }

        this.timeOfReceptionField.setText(notation.getTimeOfReception());

        this.visitedField.setText(notation.getVisited());

        this.doctorComboBox.setValue(notation.getDoctor());

        this.patientComboBox.setValue(notation.getPatient());
    }

    private boolean isEmptyFieldData() {
        return (
                idField.getText().trim().isEmpty()
                && dateOfReceptionField.getText().trim().isEmpty()
                && timeOfReceptionField.getText().trim().isEmpty()
                && visitedField.getText().trim().isEmpty() && doctorComboBox == null
                && patientComboBox == null);
    }

    private void refreshTable() {
        TableViewFocusModel<Notation> bookModelFocused = this.notationTable.getFocusModel();
        this.setTabledData();
        this.notationTable.setFocusModel(bookModelFocused);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        private ButtonHandler(ButtonHandler buttonHandler) {
        }

        public void handle(ActionEvent e) {
            Notation notation = NotationUI.this.getFieldData();
            if (e.getSource().equals(NotationUI.this.createButton) && NotationUI.this.createButton.getText().equals("Save")) {
                if (NotationUI.this.isEmptyFieldData()) {
                    NotationUI.this.msgLabel.setText("Cannot create an empty record");
                    return;
                }
                if (NotationUI.this.notationDAO.insertNotation(notation)) {
                    NotationUI.this.msgLabel.setText("New record created successfully.");
                }
                NotationUI.this.createButton.setText("New...");
                NotationUI.this.refreshTable();

            } else if (e.getSource().equals(NotationUI.this.createButton) && NotationUI.this.createButton.getText().equals("New...")) {
                notation.setDateOfReception(null);
                notation.setTimeOfReception(null);
                notation.setVisited(null);
                notation.setDoctor(null);
                notation.setPatient(null);
                NotationUI.this.setFieldData(notation);
                NotationUI.this.createButton.setText("Save");
            } else if (e.getSource().equals(NotationUI.this.updateButton)) {
                if (NotationUI.this.isEmptyFieldData()) {
                    NotationUI.this.msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (NotationUI.this.notationDAO.updateNotation(notation)) {
                    NotationUI.this.msgLabel.setText("Notation with ID:" + notation.getIdNotation() + " is updated successfully");
                }

                NotationUI.this.refreshTable();
            } else if (e.getSource().equals(NotationUI.this.deleteButton)) {
                if (NotationUI.this.isEmptyFieldData()) {
                    NotationUI.this.msgLabel.setText("Cannot delete an empty record");
                    return;
                }

                notation = NotationUI.this.notationDAO.getNotationByID(notation.getIdNotation());
                NotationUI.this.notationDAO.deleteNotation(notation.getIdNotation());
                NotationUI.this.msgLabel.setText("Notation with ID: " + notation.getIdNotation() + " is deleted successfully");
                NotationUI.this.refreshTable();

            } else if (e.getSource().equals(NotationUI.this.refreshButton)) {
                NotationUI.this.doctorComboBox.getItems().clear();
                NotationUI.this.doctorComboBox.getItems().addAll(NotationUI.this.doctorDAO.getAllDoctors());
                NotationUI.this.patientComboBox.getItems().clear();
                NotationUI.this.patientComboBox.getItems().addAll(NotationUI.this.patientDAO.getAllPatients());
                NotationUI.this.refreshTable();

            } else if (e.getSource().equals(NotationUI.this.doctorButton)) {
                Modal.display("Список лікарів", new DoctorUI());
            } else if (e.getSource().equals(NotationUI.this.patientButton)) {
                Modal.display("Список пацієнтів", new PatientUI());
            }

        }
    }
}

