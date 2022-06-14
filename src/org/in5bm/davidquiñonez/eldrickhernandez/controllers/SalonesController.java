package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.CarrerasTecnicas;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Salones;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 29 abr. 2022
 * @time 10:10:33
 *
 * Código Técnico: IN5BM
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCapacidadMax;
    @FXML
    private TextField txtNivel;
    @FXML
    private TextField txtEdificio;
    @FXML
    private TableView tblSalones;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colCapacidaMax;
    @FXML
    private TableColumn colEdificio;
    @FXML
    private TableColumn colNivel;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private ObservableList<Salones> listaSalones;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /* METODOS */
    //DESHABILITAR CAMPOS
    private void deshabilitarCampos() {
        txtCodigo.setEditable(false);
        txtCapacidadMax.setEditable(false);
        txtDescripcion.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);
        tblSalones.setDisable(false);
        
        txtCodigo.setDisable(true);
        txtCapacidadMax.setDisable(true);
        txtDescripcion.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);
    }

    //HABILITAR CAMPOS
    private void habilitarCampos() {
        tblSalones.setDisable(true);
        txtCodigo.setEditable(false);
        txtCapacidadMax.setEditable(true);
        txtDescripcion.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);
        
        txtCodigo.setDisable(true);
        txtCapacidadMax.setDisable(false);
        txtDescripcion.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
    }

    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtCodigo.clear();
        txtDescripcion.clear();
        txtCapacidadMax.clear();
        txtEdificio.clear();
        txtNivel.clear();
    }

    //AGREGAR SALONES
    private boolean agregarSalones() {
        Salones salon = new Salones();

        salon.setCodigoSalon(txtCodigo.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setEdificio(txtEdificio.getText());
        salon.setCapacidadMaxima(txtCapacidadMax.getPrefColumnCount());
        salon.setNivel(txtNivel.getPrefColumnCount());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_create(?,?,?,?,?)}");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaSalones.add(salon);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + salon.toString());
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

    //Actualizar Salones
    private boolean actualizarSalones() {
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigo.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setEdificio(txtEdificio.getText());
        salon.setCapacidadMaxima(txtCapacidadMax.getPrefColumnCount());
        salon.setNivel(txtNivel.getPrefColumnCount());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_update(?,?,?,?,?)}");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("\nSucedio un error al intentar actualizar el siguiente registro: " + salon.toString());
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

    //ELIMINAR SALONES
    public boolean eliminarSalones() {
        if (existeElemento()) {
            Salones salon = ((Salones) tblSalones.getSelectionModel().getSelectedItem());
            System.out.println(salon.toString());
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("\nSe produjo un error al intentar eliminar el siguiente registro " + salon.toString());
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

    //OBSERVABLELIST
    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salones = new Salones();
                salones.setCodigoSalon(rs.getString(1));
                salones.setDescripcion(rs.getString(2));
                salones.setCapacidadMaxima(rs.getInt(3));
                salones.setEdificio(rs.getString(4));
                salones.setNivel(rs.getInt(5));

                lista.add(salones);
                System.out.println(salones.toString());
            }
            listaSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la lista alumnos");
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

        return listaSalones;
    }

    //EXISTE ELEMENTO
    public boolean existeElemento() {
        return ((tblSalones.getSelectionModel().getSelectedItem() != null));
    }

    /* CLICS */
    //CLIC REGRESAR
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
                tblSalones.setDisable(true);

                txtCodigo.setEditable(true);
                txtCodigo.setDisable(false);

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
                if(txtCodigo.getText().isEmpty()){
                    validacionI();
                } else if (txtCapacidadMax.getText().isEmpty()){
                    validacionI();
                }else if (txtCodigo.getText().length() > 5){
                    validacionI();
                }else if (txtDescripcion.getText().length() > 45){
                    validacionI();
                }else if(txtEdificio.getText().length() > 15){
                    validacionI();
                }else if(agregarSalones()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblSalones.setDisable(false);

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

    //CLIC MODIFICAR
    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:
                if (existeElemento()) {
                    habilitarCampos();
                    /*tblSalones.setDisable(false);*/

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));
                    /*AQUI CAMBIE ALGO XD*/
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

                tblSalones.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                
                if(txtCodigo.getText().isEmpty()){
                    validacionI();
                } else if (txtCapacidadMax.getText().isEmpty()){
                    validacionI();
                }else if (txtCodigo.getText().length() > 5){
                    validacionI();
                }else if (txtDescripcion.getText().length() > 45){
                    validacionI();
                }else if(txtEdificio.getText().length() > 15){
                    validacionI();
                }else if (existeElemento()) {
                    if (actualizarSalones()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblSalones.setDisable(false);

                        tblSalones.getSelectionModel().clearSelection();
                        
                        
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);
                        

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

    //CLIC ELIMINAR
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

                tblSalones.getSelectionModel().clearSelection();

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

                        if (eliminarSalones()) {

                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
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
                        tblSalones.getSelectionModel().clearSelection();
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
    private void clicReporte(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("¡AVISO!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion solo esta disponible en la versión PRO");
        Stage dialog = new Stage();
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        // stage.getIcons().add(new Image(this.getClass().getResource("../resources/images/aprender-en-linea.png").toString()));
        alerta.show();
    }

    /* CARGAR DATOS*/
    @FXML
    private void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidaMax.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }

    /* SELECCIONAR ELEMENTOS */
    @FXML
    public void seleccionarElemento() {

        if (existeElemento()) {
            txtCodigo.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtCapacidadMax.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        }
    }
    
    private void validacionI() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Control Academico - El Bosque");
        alerta.setHeaderText(null);
        Stage stagee = (Stage) alerta.getDialogPane().getScene().getWindow();
        stagee.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        alerta.show();
        if (txtCodigo.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar el Codigo");
        } else if (txtCapacidadMax.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar la Capacidad Maxima");
        } else if (txtCodigo.getText().length() > 5 ){
            alerta.setContentText("El Codigo de salones se pasa del valor establecido |5|");
        } else if (txtDescripcion.getText().length() > 45){
            alerta.setContentText("La descripcion se pasa del valor establecido |45|");
        } else if (txtEdificio.getText().length() > 15){
            alerta.setContentText("El edificio se pasa del valor establecido |15|");
        }
    }
    
    

}
