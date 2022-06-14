package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Horarios;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 8 jun. 2022
 * @time 23:21:29
 *
 * Código Técnico: IN5BM
 */
public class HorarioController implements Initializable {

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";
    private Principal escenarioPrincipal;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;
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
    @FXML
    private TextField txtId;
    @FXML
    private CheckBox cbMartes;
    @FXML
    private JFXCheckBox cbLunes;
    @FXML
    private CheckBox cbJueves;
    @FXML
    private CheckBox cbViernes;
    @FXML
    private CheckBox cbMiercoles;
    @FXML
    private JFXTimePicker tpHorarioInicio;

    @FXML
    private JFXTimePicker tpHorarioFinal;
    @FXML
    private TableView<?> tblHorarios;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colInicio;
    @FXML
    private TableColumn<?, ?> colFinal;
    @FXML
    private TableColumn<?, ?> colLunes;
    @FXML
    private TableColumn<?, ?> colMartes;
    @FXML
    private TableColumn<?, ?> colMiercoles;
    @FXML
    private TableColumn<?, ?> colJueves;
    @FXML
    private TableColumn<?, ?> colVienres;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    //DESHABILITAR CAMPOS
    private void deshabilitarCampos() {
        txtId.setEditable(false);
        /*tpHorarioFinal.setEditable(false);
        tpHorarioInicio.setEditable(false);*/

        txtId.setDisable(true);
        tpHorarioFinal.setDisable(true);
        tpHorarioInicio.setDisable(true);
        cbLunes.setDisable(true);
        cbMartes.setDisable(true);
        cbMiercoles.setDisable(true);
        cbJueves.setDisable(true);
        cbViernes.setDisable(true);
    }

    //HABILITAR CAMPOS
    private void habilitarCampos() {
        txtId.setEditable(true);
       /* tpHorarioFinal.setEditable(true);
        tpHorarioInicio.setEditable(true);*/

        txtId.setDisable(false);
        tpHorarioFinal.setDisable(false);
        tpHorarioInicio.setDisable(false);
        cbLunes.setDisable(false);
        cbMartes.setDisable(false);
        cbMiercoles.setDisable(false);
        cbJueves.setDisable(false);
        cbViernes.setDisable(false);
    }

    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtId.clear();
        tpHorarioFinal.getEditor().clear();
        tpHorarioInicio.getEditor().clear();
        cbLunes.setSelected(false);
        cbMartes.setSelected(false);
        cbMiercoles.setSelected(false);
        cbJueves.setSelected(false);
        cbViernes.setSelected(false);
    }

    private ObservableList<Horarios> listaHorarios;
    //OBSEVABLELIST

    public ObservableList getHorarios() {

        ArrayList<Horarios> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setFInicio(rs.getTime(2).toLocalTime());
                horario.setFFinal(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                lista.add(horario);
                System.out.println(horario.toString());
            }

            listaHorarios = FXCollections.observableArrayList(lista);

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

        return listaHorarios;
    }
    
    

    //AGREGAR HORARIOS
    private boolean agregarHorarios() {
        Horarios horario = new Horarios();

        //horario.setId(Integer.parseInt(txtId.getText()));
        horario.setFInicio(tpHorarioInicio.getValue());
        horario.setFFinal(tpHorarioFinal.getValue());
        horario.setLunes(cbLunes.selectedProperty().get());
        horario.setMartes(cbMartes.selectedProperty().get());
        horario.setMiercoles(cbMiercoles.selectedProperty().get());
        horario.setJueves(cbJueves.selectedProperty().get());
        horario.setViernes(cbViernes.selectedProperty().get());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_horarios_create(?,?,?,?,?,?,?)}");
            pstmt.setString(1,  horario.getFInicio().toString());
            pstmt.setString(2,  horario.getFFinal().toString());
            pstmt.setBoolean(3, horario.getLunes());
            pstmt.setBoolean(4, horario.getMartes());
            pstmt.setBoolean(5, horario.getMiercoles());
            pstmt.setBoolean(6, horario.getJueves());
            pstmt.setBoolean(7, horario.getViernes());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horario);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + horario.toString());
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
    
    private boolean actualizarHorarios(){
        Horarios horario = new Horarios();

        horario.setId(Integer.parseInt(txtId.getText()));
        horario.setFInicio(tpHorarioInicio.getValue());
        horario.setFFinal(tpHorarioFinal.getValue());
        horario.setLunes(cbLunes.selectedProperty().get());
        horario.setMartes(cbMartes.selectedProperty().get());
        horario.setMiercoles(cbMiercoles.selectedProperty().get());
        horario.setJueves(cbJueves.selectedProperty().get());
        horario.setViernes(cbViernes.selectedProperty().get());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_update(?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1,  horario.getId());
            pstmt.setString(2,  horario.getFInicio().toString());
            pstmt.setString(3,  horario.getFFinal().toString());
            pstmt.setBoolean(4, horario.getLunes());
            pstmt.setBoolean(5, horario.getMartes());
            pstmt.setBoolean(6, horario.getMiercoles());
            pstmt.setBoolean(7, horario.getJueves());
            pstmt.setBoolean(8, horario.getViernes());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horario);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + horario.toString());
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
    
    
    private boolean eliminarHorarios(){
        
        return false;
    }

    // EXISTE ELEMENTO
    public boolean existeElemento() {
        return ((tblHorarios.getSelectionModel().getSelectedItem() != null));
    }

    // CLIC REGRESAR
    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblHorarios.setDisable(true);

                txtId.setEditable(true);
                txtId.setDisable(false);

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

                /*  if (txtCarne.getText().isEmpty()) {
                    validacionI();

                } else if (txtNombre1.getText().isEmpty()) {
                    validacionI();
                } else if (txtApellido1.getText().isEmpty()) {
                    validacionI();
                } else if (txtCarne.getText().length() > 7) {
                    validacionI();
                } else if (txtNombre1.getText().length() > 15) {
                    validacionI();
                } else if (txtNombre2.getText().length() > 15) {
                    validacionI();
                } else if (txtNombre3.getText().length() > 15) {
                    validacionI();
                } else if (txtApellido1.getText().length() > 15) {
                    validacionI();
                } else if (txtApellido2.getText().length() > 15) {
                    validacionI();
                } else*/ if (agregarHorarios()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblHorarios.setDisable(false);

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

                tblHorarios.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
               /* if (txtCarne.getText().isEmpty()) {
                    validacionI();

                } else if (txtNombre1.getText().isEmpty()) {
                    validacionI();
                } else if (txtApellido1.getText().isEmpty()) {
                    validacionI();
                } else if (txtCarne.getText().length() > 7) {
                    validacionI();
                } else if (txtNombre1.getText().length() > 15) {
                    validacionI();
                } else if (txtNombre2.getText().length() > 15) {
                    validacionI();
                } else if (txtNombre3.getText().length() > 15) {
                    validacionI();
                } else if (txtApellido1.getText().length() > 15) {
                    validacionI();
                } else if (txtApellido2.getText().length() > 15) {
                    validacionI();
                } else */if (existeElemento()) {

                    if (actualizarHorarios()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblHorarios.setDisable(false);

                        tblHorarios.getSelectionModel().clearSelection();

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

                tblHorarios.getSelectionModel().clearSelection();

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

                        if (eliminarHorarios()) {

                            listaHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
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
                        tblHorarios.getSelectionModel().clearSelection();
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
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    /* CARGAR DATOS */
    @FXML
    private void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("fInicio"));
        colFinal.setCellValueFactory(new PropertyValueFactory<>("fFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<>("jueves"));
        colVienres.setCellValueFactory(new PropertyValueFactory<>("viernes"));
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElemento()) {
            txtId.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tpHorarioInicio.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getFInicio());
            tpHorarioFinal.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getFFinal());
            cbLunes.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getLunes()));
            cbMartes.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMartes()));
            cbMiercoles.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMiercoles()));
            cbJueves.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getJueves()));
            cbViernes.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getViernes()));

        }
    }
}
