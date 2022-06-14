package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.naming.spi.InitialContextFactory;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();

    }

    @FXML
    private void clicSalones(ActionEvent event){
        escenarioPrincipal.mostrarEscenaSalones();
    }
    
    @FXML
    private void clicCarreras(ActionEvent event){
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }
    
    @FXML
    private void clicAsignaciones(ActionEvent event){
        escenarioPrincipal.mostrarEscenaAsignacionAlumnos();
    }
    
    @FXML
    private void clicHorarios(ActionEvent event){
        escenarioPrincipal.mostrarEscenaHorarios();
    }
    
    @FXML
    private void clicCursos(ActionEvent event){
        escenarioPrincipal.mostrarEscenaCursos();
    }
    
    @FXML
    private void clicInstructores(ActionEvent event){
        escenarioPrincipal.mostrarEscenaInstructores();
    }
}
