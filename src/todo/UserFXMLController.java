/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo;

import ECUtils.MyUtils;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static todo.AdminFXMLController.si;
import static todo.FXMLConst.TODO_XML;
import static todo.FXMLConst.UPDATE_TASK_XML;
import todo.bean.MyTask;
import todo.dao.TaskDAO;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class UserFXMLController implements Initializable {
    static String sc = "id";
    static String si = "";

    @FXML
    private Button btnBack;
    @FXML
    private TableView<?> tblList;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtSi;
    @FXML
    private ComboBox<?> cmbSc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyUtils.populateColumnNames(cmbSc, "task");
        txtSi.setText(si);
        MyUtils.selectComboBoxValue(cmbSc, sc);
        refreshTbl();
    }    

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(TODO_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == btnUpdate) {
            try {
                String id = MyUtils.getSelColValue("id", tblList);
                if (id == null || "".equals(id)) {
                    JOptionPane.showMessageDialog(null, "Please select a row!!");
                } else {
                UpdateTaskFXMLController.id=id;    
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(UPDATE_TASK_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
    }

    @FXML
    private void kr(KeyEvent event) {
        si=txtSi.getText();
        refreshTbl();        
    }
    
    private void refreshTbl() {
        LinkedList<MyTask> res = TaskDAO.search(cmbSc.getValue().toString(), txtSi.getText(), ToDoFXMLController.u1.getUserName());
        MyUtils.populateTable(tblList, res, MyTask.class);
    }

    @FXML
    private void cmbHe(ActionEvent event) {
        sc=cmbSc.getValue().toString();
        refreshTbl();        
    }
    
}
