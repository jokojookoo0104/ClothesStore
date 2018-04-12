/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.SidePanelContentController._vbox;
import clothesstore_model.TaiKhoan;
import static clothesstore_view.ClothesStore._rootDangNhap;
import static clothesstore_view.ClothesStore.stageDangNhap;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author user
 */
public class FXML_DangNhapController implements Initializable {

    /**
     * Initializes the controller class.
     */
       
    @FXML
    private JFXTextField txtUser;
 
    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private Label lbError;
    
    public static Stage stageMain;
    public static Stage stageSplash;
    public static String UserID;
    
    @FXML
    private void Login(ActionEvent event) throws IOException{
        String user = txtUser.getText();
        String password = txtPass.getText().toString();

        TaiKhoan tk = new TaiKhoan(user, password);  
        if(tk.CheckLogin()){
            UserID = user;
            txtUser.clear();
            txtPass.clear();
            stageDangNhap.close();
            loadSplashScreen();
            try {
                    Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_ClothesStore.fxml"));
                    Scene scene = new Scene(root);
                    stageMain = new Stage();
                    stageMain.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(FXML_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            int quyen = tk.GetQuyenFromID(user);
            if(quyen == 1){
                
            } else if (quyen == 0) {
                List<Object> listnode = new ArrayList<Object>();
                for(Node node: _vbox.getChildren()){
                    if(node instanceof JFXButton){
                        if(node.getId().equals("btnTonKho") || node.getId().equals("btnLoiNhuan")
                                || node.getId().equals("btnBieuDo")
                                || node.getId().equals("btnQuanLyTK"))
                            listnode.add(node);    
                    }
                }
                for(Object node: listnode){
                    _vbox.getChildren().remove(node);
                }
            }
        } else
            lbError.setText("Tên tài khoản hoặc mật khẩu không chính xác!");    
    }
    
    private void loadSplashScreen() {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/clothesstore_view/SplashFXML.fxml"));
            Scene scene = new Scene(pane);
            scene.setFill(Color.TRANSPARENT);
            stageSplash = new Stage();
            stageSplash.initStyle(StageStyle.TRANSPARENT);
            stageSplash.setScene(scene);
            stageSplash.show();     
        
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2.5), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                stageMain.show();  
                stageSplash.close();
            });

        } catch (IOException ex) {
            Logger.getLogger(FXML_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void exit(ActionEvent event) {
        ButtonType yes = new ButtonType("Thoát", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc chắn muốn thoát",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            System.exit(0);
        }            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}