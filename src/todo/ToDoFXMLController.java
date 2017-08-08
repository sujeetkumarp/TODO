/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo;

import ECUtils.ECConst;
import ECUtils.GUIValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import todo.bean.AppUser;
import todo.dao.UserDAO;

/**
 *
 * @author computer
 */
public class ToDoFXMLController implements Initializable, FXMLConst {

    GUIValidator v1 = new GUIValidator();
    static AppUser u1 = null;
    @FXML
    private TextField txtUName;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnReset;
    @FXML
    private PasswordField txtDBPass;
    @FXML
    private TextField txtDBUser;
    @FXML
    private TextField txtServerIP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        v1.addRequiredValidator(txtUName);
        v1.addRequiredValidator(txtPass);
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            try {
                if (v1.validateAll()) {
                    if (txtServerIP.getText() != null && !"".equals(txtServerIP.getText().trim())) {
                        ECConst.DB_HOST = txtServerIP.getText().trim();
                    }
                    if (txtDBUser.getText() != null && !"".equals(txtDBUser.getText().trim())) {
                        ECConst.DB_USER = txtDBUser.getText().trim();
                    }
                    if (txtDBPass.getText() != null && !"".equals(txtDBPass.getText().trim())) {
                        ECConst.DB_PASS = txtDBPass.getText().trim();
                    }
                    u1 = UserDAO.validate(txtUName.getText(), txtPass.getText());
                    if (u1 != null) {
                        Parent root = null;
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        if ("admin".equalsIgnoreCase(u1.getRoll())) {
                            root = FXMLLoader.load(getClass().getResource(ADMIN_XML));
                        } else {
                            root = FXMLLoader.load(getClass().getResource(USER_XML));
                        }
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setX(0);
                        stage.setY(0);
                        stage.show();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Infor!!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Stage stage = (Stage) btnReset.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(TODO_XML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
