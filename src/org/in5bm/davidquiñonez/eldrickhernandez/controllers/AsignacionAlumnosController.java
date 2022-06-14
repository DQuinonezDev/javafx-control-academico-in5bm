package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Timestamp;
import com.jfoenix.controls.JFXDatePicker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;
import org.in5bm.davidquiñonez.eldrickhernandez.models.AsignacionesAlumnos;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Alumnos;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Cursos;

/**
 *
 * @author informatica
 */
public class AsignacionAlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    private ObservableList<Alumnos> listaAlumnos;
    private ObservableList<Cursos> listaCursos;
    private ObservableList<AsignacionesAlumnos> listaAsignaciones;

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
    private TextField txtId;

    @FXML
    private JFXDatePicker dpFechaAsignacion;

    @FXML
    private TableView<AsignacionesAlumnos> tblAsignacionAlumnos;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colid;
    @FXML
    private TableColumn<AsignacionesAlumnos, String> colCarne;
    @FXML
    private TableColumn<AsignacionesAlumnos, String> colNombreCurso;
    @FXML
    private TableColumn<Alumnos, String> colNombreAlumno;
    @FXML
    private TableColumn<Cursos, String> ColCursos;
    @FXML
    private TableColumn<AsignacionesAlumnos, LocalDate> colFechaAsignacion;
    @FXML
    private ComboBox<Alumnos> cmbAlumno;
    @FXML
    private ComboBox<Cursos> cmbIdCursos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deshabilitarCampos();
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        cmbAlumno.setEditable(false);
        cmbIdCursos.setEditable(false);
        dpFechaAsignacion.setEditable(false);
        tblAsignacionAlumnos.setDisable(false);
        
        txtId.setDisable(true);
        cmbAlumno.setDisable(true);
        cmbIdCursos.setDisable(true);
        dpFechaAsignacion.setDisable(true);

    }

    //HABILITAR CAMPOS
    private void habilitarCampos() {
        txtId.setEditable(true);
        cmbAlumno.setEditable(false);
        cmbIdCursos.setEditable(false);
        dpFechaAsignacion.setEditable(false);
        tblAsignacionAlumnos.setDisable(true);

        txtId.setDisable(false);
        cmbAlumno.setDisable(false);
        cmbIdCursos.setDisable(false);
        dpFechaAsignacion.setDisable(false);
    }

    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtId.clear();
        cmbAlumno.valueProperty().set(null);
        cmbIdCursos.valueProperty().set(null);
        dpFechaAsignacion.getEditor().clear();

    }

    //AGREGAR ALUMNOS
    private boolean ageregarAsignacion() {
        /*AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        asignacion.setAlumnoId((((Alumnos)cmbAlumno.getSelectionModel().getSelectedItem()).getCarne()));
        asignacion.setCursoId(((Cursos) cmbIdCursos.getSelectionModel().getSelectedItem()).getId());
        asignacion.setFechaAsignacion(dpFechaAsignacion.getValue().atStartOfDay());*/

        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();

        asignacion.setAlumnoId(((Alumnos) cmbAlumno.getSelectionModel().getSelectedItem()).getCarne());

        asignacion.setCursoId(((Cursos) cmbIdCursos.getSelectionModel().getSelectedItem()).getId());

        asignacion.setFechaAsignacion(dpFechaAsignacion.getValue().atStartOfDay());
       
        /*AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        asignacion.setAlumnoId(cmbAlumno.getValue().toString());
        System.out.println(cmbAlumno.toString());
        System.out.println("--------------------------------");
        System.out.println("- - - - - -" + cmbAlumno.getValue().toString());
        asignacion.setCursoId(Integer.parseInt(cmbIdCursos.getValue().toString()));
        asignacion.setFechaAsignacion(dpFechaAsignacion.getValue().atStartOfDay());*/

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignacion_alumnos_create(?,?,?)}");
            pstmt.setString(1, asignacion.getAlumnoId());
            pstmt.setInt(2, asignacion.getCursoId());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacion.getFechaAsignacion()));

            System.out.println(pstmt.toString());
            pstmt.execute();

            listaAsignaciones.add(asignacion);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + asignacion.toString());
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
    private boolean actualizarAsignacion() {

        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();

        asignacion.setId(Integer.parseInt(txtId.getText()));
        asignacion.setAlumnoId((((Alumnos) cmbAlumno.getSelectionModel().getSelectedItem()).getCarne()));

        System.out.println("--------------------------------");
        System.out.println("- - - - - - SE HA ACTUALIZADO" + cmbAlumno.getValue().toString());

        asignacion.setCursoId((((Cursos) cmbIdCursos.getSelectionModel().getSelectedItem()).getId()));
        asignacion.setFechaAsignacion(dpFechaAsignacion.getValue().atStartOfDay());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignacion_alumnos_update(?,?,?,?)}");
            pstmt.setInt(1, asignacion.getId());
            pstmt.setString(2, asignacion.getAlumnoId());
            pstmt.setInt(3, asignacion.getCursoId());
            pstmt.setTimestamp(4, Timestamp.valueOf(asignacion.getFechaAsignacion()));

            System.out.println(pstmt.toString());
            pstmt.execute();

            listaAsignaciones.add(asignacion);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar el siguiente alumno: " + asignacion.toString());
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
    public boolean eliminarAsignacion() {
        AsignacionesAlumnos asignacion = (AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem();

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignacion_alumnos_delete(?)}");
            pstmt.setInt(1, asignacion.getId());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAsignaciones.remove(tblAsignacionAlumnos.getSelectionModel().getFocusedIndex());

            return true;
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
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
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
            System.err.println("\nSe produjo un error al eliminar el siguiente registro: ");
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
        return ((tblAsignacionAlumnos.getSelectionModel().getSelectedItem() != null));
    }

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
                tblAsignacionAlumnos.setDisable(true);

                txtId.setEditable(false);
                txtId.setDisable(true);

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

                /* if (txtCarne.getText().isEmpty()) {
                    validacionI();

                } else if (txtNombre1.getText().isEmpty()) {
                    validacionI();
                } else if (txtApellido1.getText().isEmpty()) {
                    validacionI();
                } else */
                if (ageregarAsignacion()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblAsignacionAlumnos.setDisable(false);

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
                    //tblAsignacionAlumnos.setDisable(true);

                    txtId.setDisable(true);
                    
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

                tblAsignacionAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElemento()) {
                    if (actualizarAsignacion()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblAsignacionAlumnos.setDisable(false);

                        tblAsignacionAlumnos.getSelectionModel().clearSelection();

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

                tblAsignacionAlumnos.getSelectionModel().clearSelection();

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

                        if (eliminarAsignacion()) {

                            listaAsignaciones.remove(tblAsignacionAlumnos.getSelectionModel().getFocusedIndex());
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
                        tblAsignacionAlumnos.getSelectionModel().clearSelection();
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
        alerta.show();
    }

    private Alumnos buscarAlumnos(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Alumnos alumno = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                /*Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));

                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));*/
                alumno = new Alumnos(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));

                System.out.println(alumno.toString());

            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar al Alumno con el id :" + id);
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLSate" + e.getSQLState());
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
        return alumno;
    }

    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

                arrayListCursos.add(curso);
            }

            listaCursos = FXCollections.observableArrayList(arrayListCursos);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return listaCursos;
    }

    private Cursos buscarCursos(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));

                System.out.println(curso.toString());

            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar al Alumno con el id :" + curso);
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLSate" + e.getSQLState());
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
        return curso;
    }

    @FXML
    private void seleccionarElemento() {
        if (existeElemento()) {
            txtId.setText(String.valueOf(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getId()));
            cmbAlumno.getSelectionModel().select(buscarAlumnos(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getAlumnoId()));
            cmbIdCursos.getSelectionModel().select(buscarCursos(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getCursoId()));
            dpFechaAsignacion.setValue(((AsignacionesAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getFechaAsignacion().toLocalDate());
        }
    }

    public ObservableList getAsignacionAlumnos() {
        ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignacion_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
                asignacion.setId(rs.getInt("id"));
                asignacion.setAlumnoId(rs.getString("alumno_id"));
                asignacion.setCursoId(rs.getInt("curso_id"));
                asignacion.setFechaAsignacion(rs.getTimestamp("fecha_asignacion").toLocalDateTime());
                System.out.println(asignacion);

                arrayListAsignaciones.add(asignacion);
            }
            listaAsignaciones = FXCollections.observableArrayList(arrayListAsignaciones);

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
        return listaAsignaciones;
    }

    /* CARGAR DATOS */
    @FXML
    private void cargarDatos() {
        tblAsignacionAlumnos.setItems(getAsignacionAlumnos());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarne.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        ColCursos.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));

        //((Alumnos) cmbAlumno.getSelectionModel().getSelectedItem()).getCarne());
        cmbIdCursos.setItems(getCursos());
        cmbAlumno.setItems(getAlumnos());
    }

}
