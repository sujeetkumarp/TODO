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
import static todo.AdminFXMLController.sc;
import static todo.AdminFXMLController.si;
import static todo.FXMLConst.TODO_XML;
import todo.bean.AppUser;
import todo.bean.MyTask;
import todo.dao.TaskDAO;
import todo.dao.UserDAO;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class SearchUserFXMLController implements Initializable, FXMLConst {

    static String sc = "id";
    static String si = "";

    @FXML
    private Button btnBack;
    @FXML
    private Button btnDel;
    @FXML
    private TextField txtSi;
    @FXML
    private ComboBox<?> cmbSc;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnExit;
    @FXML
    private TableView<?> tblList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyUtils.populateColumnNames(cmbSc, "app_users");
        txtSi.setText(si);
        MyUtils.selectComboBoxValue(cmbSc, sc);
        refreshTbl();
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADD_USER_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == btnDel) {
            try {
                String id = MyUtils.getSelColValue("id", tblList);
                if (id == null || "".equals(id)) {
                    JOptionPane.showMessageDialog(null, "Please select a row!!");
                } else {
                    int ch = JOptionPane.showConfirmDialog(null, "R u sure u want to delete?");
                    if (ch == 0) {
                        UserDAO.delete(id);
                        refreshTbl();
                        JOptionPane.showMessageDialog(null, "Deleted!!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == btnExit) {
            System.exit(0);
        }
    }

    @FXML
    private void kr(KeyEvent event) {
        si = txtSi.getText();
        refreshTbl();
    }

    @FXML
    private void cmbHe(ActionEvent event) {
        sc = cmbSc.getValue().toString();
        refreshTbl();
    }

    private void refreshTbl() {
        LinkedList<AppUser> res = UserDAO.search(cmbSc.getValue().toString(), txtSi.getText());
        MyUtils.populateTable(tblList, res, AppUser.class);
    }

}
