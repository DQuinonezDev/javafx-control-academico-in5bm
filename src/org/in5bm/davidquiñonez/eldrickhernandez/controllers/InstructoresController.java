
package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 8 jun. 2022
 * @time 23:21:49
 *
 * Código Técnico: IN5BM
 */
public class InstructoresController implements Initializable{

    private Principal escenarioPrincipal;
    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
    }

    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicReporte(ActionEvent event) {
    }

    @FXML
    private void seleccionarElemento(MouseEvent event) {
    }

    @FXML
    private void seleccionarElemento(KeyEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
