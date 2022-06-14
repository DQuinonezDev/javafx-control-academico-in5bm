package org.in5bm.davidquiñonez.eldrickhernandez.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.AlumnosController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.AsignacionAlumnosController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.CarrerasTecnicasController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.CursosController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.MenuPrincipalController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.SalonesController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.CursosController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.HorarioController;
import org.in5bm.davidquiñonez.eldrickhernandez.controllers.InstructoresController;

/**
 *
 * @author David Quiñonez
 */
public class Principal extends Application {

    private final String PAQUETE_VIEW = "../views/";
    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";
    private Stage escenarioPrincipal;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico El Bosque");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        //this.escenarioPrincipal.show();
        mostrarEscenaPrincipal();
    }

    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println("Cambiando escena: " + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }

    public void mostrarEscenaPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1200, 700);
            menuController.setEscenarioPrincipal(this);
            //cambiarEscena("MenuPrincipalView.fxml", 560, 950);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al cargar la vista del menu principal");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1200, 700);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al cargar la vista de alumnos");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenaSalones() {
        try {
            SalonesController salonController = (SalonesController) cambiarEscena("SalonesView.fxml", 1200, 700);
            salonController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al cargar la vista de salones");
            ex.printStackTrace();
        }
    }

   public void mostrarEscenaCarrerasTecnicas() {
        try {
            CarrerasTecnicasController carrerasController = (CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml", 1200, 700);
            carrerasController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al cargar la vista de Carreras Tecnicas");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaAsignacionAlumnos(){
        try{
            AsignacionAlumnosController asignacionesController = (AsignacionAlumnosController) cambiarEscena ("AsignacionAlumnosView.fxml", 1200,700);
            asignacionesController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al cargar la vista de Asignacion Alumnos");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenaCursos(){
        try{
            CursosController cursosController = (CursosController) cambiarEscena ("CursosView.fxml", 1200,700);
            cursosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al cargar la vista de Cursos");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios(){
        try{
            HorarioController horarioController = (HorarioController) cambiarEscena ("HorariosView.fxml", 1200,700);
            horarioController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al cargar la vista de Horarios");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaInstructores(){
        try{
            InstructoresController instructorController = (InstructoresController) cambiarEscena ("InstructoresView.fxml", 1200,700);
            instructorController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al cargar la vista de Instructores");
            ex.printStackTrace();
        }
    }
}
