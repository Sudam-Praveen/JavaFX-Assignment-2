package controller;

import dto.CustomerDto;
import dto.Tm.CustomerTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerFormController {

    public Label lblTime;
    @FXML
        private TextField txtId;

        @FXML
        private TextField txtSalary;

        @FXML
        private TextField txtAddress;

        @FXML
        private TextField txtName;

        @FXML
        private TableView<CustomerTm> tblCustomer;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private TableColumn<?, ?> colName;

        @FXML
        private TableColumn<?, ?> colAddress;

        @FXML
        private TableColumn<?, ?> colSalary;

        @FXML
        private TableColumn<?, ?> colOption;
        private final CustomerModel customerModel= new CustomerModelImpl();



        public void initialize(){
            calculateTime();
            loadCustomerTable();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
            colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

            tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVale, newValue) ->{
                setData(newValue);
            });
        }
    private void calculateTime() {

        Timeline timeline=new Timeline(new KeyFrame(
                Duration.ZERO, actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setData(CustomerTm newValue) {
            if(newValue != null) {
                txtId.setEditable(false);
                txtId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtSalary.setText(String.valueOf(newValue.getSalary()));
            }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> customerTmList = customerModel.allCustomer();
            for (CustomerDto dto:customerTmList) {
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6;");
                CustomerTm c = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );
                btn.setOnAction(ActionEvent->{
                    boolean deleted = customerModel.deleteCustomer(dto.getId());
                    if(deleted){
                        new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                        loadCustomerTable();
                        clearFields();
                    }
                });
                tmList.add(c);
                tblCustomer.setItems(tmList);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
            CustomerDto c =new CustomerDto(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            );
        boolean updated = customerModel.updateCustomer(c);
        if(updated){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated").show();
            loadCustomerTable();
            clearFields();
        }
    }

    private void clearFields() {
            txtId.clear();
            txtName.clear();
            txtAddress.clear();
            txtSalary.clear();
            txtId.setEditable(true);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
            CustomerDto c =new CustomerDto(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            );
            boolean saved = customerModel.saveCustomer(c);
            if(saved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved").show();
                loadCustomerTable();
                clearFields();
            }


        }

        public void reloadButtonOnAction(ActionEvent actionEvent) {
            clearFields();
            tblCustomer.refresh();
            loadCustomerTable();
        }


    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) tblCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.show();

    }
}


