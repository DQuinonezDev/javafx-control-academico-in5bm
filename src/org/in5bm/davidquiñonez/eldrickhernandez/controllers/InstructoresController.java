 package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Instructores;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author David - Eldrick
 * @date 26 abr. 2022
 * @time 8:32:00
 *
 * Código Técnico: IN5BM
 */
public class InstructoresController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtId;

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
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private JFXDatePicker dpFechaNacimiento;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private TableView tblInstructores;

    @FXML
    private TableColumn colId;

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
    private TableColumn colDireccion;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colFechaNacimiento;

    @FXML
    private ObservableList<Instructores> listaInstructores;
    
    @FXML
    public void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, Integer>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<Instructores, JFXDatePicker>("fechaNacimiento"));

    }

    public boolean existeElementoSeleccionado() {
        return (tblInstructores.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId()));

            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());

            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());

            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());

            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());

            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());

            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());

            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());

            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());

            dpFechaNacimiento.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
        }

    }

    public boolean agregarInstructor() {
        Instructores instructores = new Instructores();

        instructores.setNombre1(txtNombre1.getText());
        instructores.setNombre2(txtNombre2.getText());
        instructores.setNombre3(txtNombre3.getText());
        instructores.setApellido1(txtApellido1.getText());
        instructores.setApellido2(txtApellido2.getText());
        instructores.setDireccion(txtDireccion.getText());
        instructores.setEmail(txtEmail.getText());
        instructores.setTelefono(txtTelefono.getText());

        instructores.setFechaNacimiento(dpFechaNacimiento.getValue());

        PreparedStatement pstmt = null;

        try {

            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, instructores.getNombre1());
            pstmt.setString(2, instructores.getNombre2());
            pstmt.setString(3, instructores.getNombre3());
            pstmt.setString(4, instructores.getApellido1());
            pstmt.setString(5, instructores.getApellido2());
            pstmt.setString(6, instructores.getDireccion());
            pstmt.setString(7, instructores.getEmail());
            pstmt.setString(8, instructores.getTelefono());
            pstmt.setString(9, instructores.getFechaNacimiento().toString());

            //FALTA LA FECHA DE NACIMIENTO
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaInstructores.add(instructores);

            return true;

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar insertar el siguiente alumno: " + instructores.toString());
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

    private boolean actualizarInstructor() {
        Instructores instructores = new Instructores();

        instructores.setId(Integer.valueOf(txtId.getText()));
        instructores.setNombre1(txtNombre1.getText());
        instructores.setNombre2(txtNombre2.getText());
        instructores.setNombre3(txtNombre3.getText());
        instructores.setApellido1(txtApellido1.getText());
        instructores.setApellido2(txtApellido2.getText());
        instructores.setDireccion(txtDireccion.getText());
        instructores.setEmail(txtEmail.getText());
        instructores.setTelefono(txtTelefono.getText());

        /*LocalDate fecha = dpFechaNacimiento.getValue();
        java.sql.Date fecha2 = java.sql.Date.valueOf(dpFechaNacimiento.getValue().toString());*/

        instructores.setFechaNacimiento(dpFechaNacimiento.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, instructores.getId());
            pstmt.setString(2, instructores.getNombre1());
            pstmt.setString(3, instructores.getNombre2());
            pstmt.setString(4, instructores.getNombre3());
            pstmt.setString(5, instructores.getApellido1());
            pstmt.setString(6, instructores.getApellido2());
            pstmt.setString(7, instructores.getDireccion());
            pstmt.setString(8, instructores.getEmail());
            pstmt.setString(9, instructores.getTelefono());
            pstmt.setObject(10, instructores.getFechaNacimiento());

            System.out.println(pstmt.toString());
            pstmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro" + instructores.toString());
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
    
    private boolean eliminarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructor.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_delete(?)}");
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el alumno: " + instructor.toString());
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

    public ObservableList getInstructores() {

        ArrayList<Instructores> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("call sp_instructores_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Instructores instructores = new Instructores();

                instructores.setId(rs.getInt(1));
                instructores.setNombre1(rs.getString(2));
                instructores.setNombre2(rs.getString(3));
                instructores.setNombre3(rs.getString(4));
                instructores.setApellido1(rs.getString(5));
                instructores.setApellido2(rs.getString(6));
                instructores.setDireccion(rs.getString(7));
                instructores.setEmail(rs.getString(8));
                instructores.setTelefono(rs.getString(9));
                instructores.setFechaNacimiento(rs.getDate(10).toLocalDate());

                //FALTA LA FECHA DE NACIMIENTO
                lista.add(instructores);

                System.out.println(instructores.toString());
            }

            listaInstructores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar consultar la lista de Instructores");
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

        return listaInstructores;
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);

        txtId.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        dpFechaNacimiento.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);

        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        dpFechaNacimiento.setDisable(true);
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpFechaNacimiento.getEditor().clear();

    }

    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblInstructores.setDisable(true);

                txtId.setEditable(false);
                txtId.setDisable(true);

                limpiarCampos();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "icons8-cancelar-100.png"));

                btnEliminar.setDisable(true);
                imgEliminar.setDisable(true);

                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);

                btnReporte.setDisable(true);
                imgReporte.setDisable(true);

                btnReporte.setVisible(false);
                imgReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;

            case GUARDAR:

                if (txtNombre1.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("El campo Primer Nombre es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();

                } else if (txtApellido1.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("El campo Primer Apellido es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();

                } else if (txtEmail.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("El campo Email es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();

                } else if (txtTelefono.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("El campo Teléfono es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();
                    
                } else if (dpFechaNacimiento.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("El campo Fecha Nacimiento es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();
                } else {
                    if (agregarInstructor()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();
                        tblInstructores.setDisable(false);

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setDisable(false);
                        imgEliminar.setDisable(false);

                        btnEliminar.setVisible(true);
                        imgEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setDisable(false);

                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }

                break;
        }
    }

    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    imgNuevo.setDisable(true);

                    btnNuevo.setVisible(false);
                    imgNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "icons8-cancelar-100.png"));

                    btnReporte.setDisable(true);
                    imgReporte.setDisable(true);

                    btnReporte.setVisible(false);
                    imgReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccione un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                    alert.show();
                }

                break;

            case GUARDAR:

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setDisable(false);
                imgEliminar.setDisable(false);

                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);

                btnReporte.setDisable(false);
                imgReporte.setDisable(false);

                btnReporte.setVisible(true);
                imgReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblInstructores.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarInstructor()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();
                        tblInstructores.setDisable(false);
                        tblInstructores.getSelectionModel().clearSelection();

                        btnNuevo.setVisible(true);
                        imgNuevo.setVisible(true);

                        btnNuevo.setDisable(false);
                        imgNuevo.setDisable(false);

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "pagina.png"));

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                        btnEliminar.setDisable(false);
                        imgEliminar.setDisable(false);

                        btnEliminar.setVisible(true);
                        imgEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setDisable(false);

                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }
                break;

        }
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR LA ACTUALIZACIÓN
                btnNuevo.setDisable(false);
                imgNuevo.setDisable(false);

                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                imgReporte.setDisable(false);

                btnReporte.setVisible(true);
                imgReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblInstructores.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;

                break;

            case NINGUNO:

                if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logoAlert.png"));
                    alert.setContentText("¿Desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarInstructor()) {
                            listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Control Académico Kinal");
                            alert.setHeaderText(null);
                            alert.setContentText("Registro eliminado exitosamente");
                            dialog = new Stage();
                            stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image(PAQUETE_IMAGES + "logoAlert.png"));
                            alert.show();
                        }

                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alert.close();
                        tblInstructores.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccione un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logoAlert.png"));
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("¡AVISO!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion solo esta disponible en la versión PRO");
        Stage dialog = new Stage();
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "editar.png"));
        alerta.show();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}