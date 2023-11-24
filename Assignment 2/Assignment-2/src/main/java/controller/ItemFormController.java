package controller;

import dto.ItemDto;
import dto.Tm.ItemTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
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
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItemFormController {

    public TableView<ItemTm> tblItem;
    public TableColumn<?,?> colCode;
    public Label tblTime;
    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML


    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colOption;
    private final ItemModel itemModel=new ItemModelImpl();

    public void initialize(){
        calculateTime();
        loadItemTable();
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblItem.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->{
            setData(newValue);
        } ));
    }
    private void calculateTime() {

        Timeline timeline=new Timeline(new KeyFrame(
                Duration.ZERO, actionEvent -> tblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setData(ItemTm newValue) {
        if(newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDesc.setText(newValue.getDescription());
            txtPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        List<ItemDto> itemTmList = itemModel.allItem();
        for (ItemDto dto:itemTmList) {
            Button itemBtn = new Button("Delete");
            itemBtn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6;");
            ItemTm t = new ItemTm(
                    dto.getCode(),
                    dto.getDescription(),
                    dto.getUnitPrice(),
                    dto.getQty(),
                    itemBtn
            );
            itemBtn.setOnAction(actionEvent -> {
                boolean deleted = itemModel.deleteItem(t.getCode());
                if(deleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                    loadItemTable();
                    clearFields();
                }
            });
            tmList.add(t);
            tblItem.setItems(tmList);

        }

    }


    public void saveOnAction(ActionEvent actionEvent) {
        ItemDto item = new ItemDto(
                txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        boolean saved = itemModel.saveItem(item);
        if(saved){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Saved").show();
            loadItemTable();
            clearFields();
        }

    }

    private void clearFields() {
        txtCode.clear();
        txtDesc.clear();
        txtPrice.clear();
        txtQty.clear();
    }

    public void updateOnAction(ActionEvent actionEvent) {
        ItemDto t =new ItemDto(
                txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        boolean updated = itemModel.updateItem(t);
        if(updated){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Updated").show();
            loadItemTable();
            clearFields();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) tblItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.show();
    }
}
