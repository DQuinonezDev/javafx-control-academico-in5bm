package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Alumnos;
/*import org.in5bm.davidquiñonez.eldrickhernandez.reports.GenerarReporte;*/

/**
 *
 * @author David-Eldrick
 */
public class AlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    @FXML
    private TextField txtCarne;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableColumn colCarne;
    @FXML
    private TableColumn colNombre1;
    @FXML
    private TableColumn colNombre2;
    @FXML
    private TableColumn colNombre3;
    @FXML
    private TableColumn colApellido1;
    @FXML
    private TableColumn colApellido2;
    @FXML
    private TableView tblAlumnos;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private ObservableList<Alumnos> listaAlumnos;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    // METODOS
    //DESHABILITAR CAMPOS
    private void deshabilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        tblAlumnos.setDisable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    //HABILITAR CAMPOS
    private void habilitarCampos() {
        tblAlumnos.setDisable(true);
        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }

    //AGREGAR ALUMNOS
    private boolean agregarAlumno() {
        Alumnos alumno = new Alumnos();

        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_create(?,?,?,?,?,?)}");
            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAlumnos.add(alumno);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + alumno.toString());
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Actualizar Alumnos
    private boolean actualizarAlumno() {

        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_alumnos_update(?,?,?,?,?,?)");
            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("\nSucedio un error al intentar actualizar el siguiente registro: " + alumno.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    //ELIMINAR ALUMNOS
    public boolean eliminarAlumno() {
        if (existeElemento()) {
            Alumnos alumno = ((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem());
            System.out.println(alumno.toString());
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_delete(?)}");
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("\nSe produjo un error al intentar eliminar el siguiente registro " + alumno.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {

                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //OBSEVABLELIST
    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);
                System.out.println(alumno.toString());
            }
            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista alumnos:");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLSate" + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaAlumnos;
    }

    // EXISTE ELEMENTO
    public boolean existeElemento() {
        return ((tblAlumnos.getSelectionModel().getSelectedItem() != null));
    }

    /* CLICS */
    // CLIC REGRESAR
    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    // CLIC NUEVO
    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblAlumnos.setDisable(true);

                txtCarne.setEditable(true);
                txtCarne.setDisable(false);

                limpiarCampos();

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "icons8-cancelar-100.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
            
                if (txtCarne.getText().isEmpty()) {
                    validacionI();

                } else if (txtNombre1.getText().isEmpty()) {
                    validacionI();
                } else if (txtApellido1.getText().isEmpty()) {
                    validacionI();
                } else if (txtCarne.getText().length() > 7){
                    validacionI();
                } else if (txtNombre1.getText().length() > 15){
                    validacionI();
                } else if(txtApellido1.getText().length() > 15){
                    validacionI();
                }else if (agregarAlumno()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblAlumnos.setDisable(false);

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    operacion = Operacion.NINGUNO;
                }

                break;
        }
    }

    // CLIC MODIFICAR
    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:
                if (existeElemento()) {
                    habilitarCampos();
                    /*tblAlumnos.setDisable(false);*/

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "icons8-cancelar-100.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico - El Bosque");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    Stage stagee = (Stage) alert.getDialogPane().getScene().getWindow();
                    stagee.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
                    alert.show();
                }
                break;
            case GUARDAR: //Cancelar

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElemento()) {
                    if (actualizarAlumno()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblAlumnos.setDisable(false);

                        tblAlumnos.getSelectionModel().clearSelection();

                        btnModificar.setText("Nuevo");
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;

                    }
                }

                break;
        }
    }

    // CLIC ELIMINAR
    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar la actualizacion
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblAlumnos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElemento()) {
                    Alert alertaC = new Alert(Alert.AlertType.CONFIRMATION);
                    alertaC.setTitle("Control Academico - El Bosque");
                    alertaC.setHeaderText(null);
                    alertaC.setContentText("¿Seguro que quieres eliminar el registro?");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alertaC.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));

                    Optional<ButtonType> botonC = alertaC.showAndWait();

                    if (botonC.get().equals(ButtonType.OK)) {

                        if (eliminarAlumno()) {

                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Control Academico");
                            alerta.setHeaderText(null);
                            alerta.setContentText("Eliminacion Exitosa");
                            Stage stag = (Stage) alerta.getDialogPane().getScene().getWindow();
                            stag.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
                            alerta.show();

                            limpiarCampos();
                        }
                    } else if (botonC.get().equals(ButtonType.CANCEL)) {
                        alertaC.close();
                        tblAlumnos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico - El Bosque");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
                    alert.show();

                }
                break;
        }
    }

    //CLIC REPORTE
    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("¡AVISO!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion solo esta disponible en la versión PRO");
        Stage dialog = new Stage();
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        // stage.getIcons().add(new Image(this.getClass().getResource("../resources/images/aprender-en-linea.png").toString()));
        alerta.show();
        /*Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Eldric Aldair");
        
        GenerarReporte.getInstance().mostrarReporte("Pantalla.jasper", parametro);*/
    }


    /* CARGAR DATOS */
    @FXML
    private void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }

    /* SELECCIONAR ELEMENTOS */
    @FXML
    public void seleccionarElemento() {

        if (existeElemento()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }

    }

    private void validacionI() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Control Academico - El Bosque");
        alerta.setHeaderText(null);
        Stage stagee = (Stage) alerta.getDialogPane().getScene().getWindow();
        stagee.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        alerta.show();
        
        if (txtCarne.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar Carne");
        } else if (txtNombre1.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar Primer Nombre");
        } else if (txtApellido1.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar Primer Apellido");
        } else if (txtCarne.getText().length() > 7){
            alerta.setContentText("El Carne se pasa del valor establecido |7|");
        } else if (txtNombre1.getText().length() > 15){
            alerta.setContentText("El nombres se pasa del valor establecido |15|");
        } else if (txtApellido1.getText().length() > 15){
            alerta.setContentText("El nombres se pasa del valor establecido |15|");
        } 
    }

}
