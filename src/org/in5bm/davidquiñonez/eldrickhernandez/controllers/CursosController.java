package org.in5bm.davidquiñonez.eldrickhernandez.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;
import org.in5bm.davidquiñonez.eldrickhernandez.models.CarrerasTecnicas;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Cursos;
//import org.in5bm.davidquiñonez.eldrickhernandez.models.Horarios;
//import org.in5bm.davidquiñonez.eldrickhernandez.models.Instructores;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Salones;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Horarios;
import org.in5bm.davidquiñonez.eldrickhernandez.models.Instructores;

/**
 *
 * @author Jorge Luis Pérez Canto
 * @date 2/06/2022
 * @time 10:55:00
 *
 */
public class CursosController implements Initializable {

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
    private TableView<Cursos> tblCursos;
    @FXML
    private TableColumn<Cursos, Integer> colCarne;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMaximo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMinimo;
    @FXML
    private TableColumn<Cursos, String> colCarreraTecnica;
    @FXML
    private TableColumn<Cursos, Integer> colHorario;
    @FXML
    private TableColumn<Cursos, Integer> colInstructor;
    @FXML
    private TableColumn<Cursos, String> colSalon;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombreCursos;

    @FXML
    private Spinner<Integer> spnCiclo;

    private SpinnerValueFactory<Integer> valueFactoryCiclo;

    @FXML
    private Spinner<Integer> spnCupoMaximo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;

    @FXML
    private Spinner<Integer> spnCupoMinimo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;

    @FXML
    private ComboBox cmbCarreraTecnica;
    @FXML
    private ComboBox cmbHorarios;
    @FXML
    private ComboBox cmbInstructor;
    @FXML
    private ComboBox cmbSalon;
    @FXML
    private ImageView imgRegresar;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;
    private ObservableList<Salones> listaSalones;
    private ObservableList<CarrerasTecnicas> listaCarrera;
    private ObservableList<Horarios> listaObservableHorarios;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        deshabilitarCampos();
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);

        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);

        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 22, 5);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);

        cargarDatos();

    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombreCursos.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorarios.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);
        txtId.setDisable(true);
        txtNombreCursos.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorarios.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtNombreCursos.setEditable(true);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorarios.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);
        txtId.setDisable(true);
        txtNombreCursos.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorarios.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombreCursos.clear();
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorarios.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }

    // read -> Listar todos los registros
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

            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

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

        return listaObservableCursos;
    }

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

    public ObservableList getCarreras() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas carrera = new CarrerasTecnicas();
                carrera.setCodigoTecnico(rs.getString(1));
                carrera.setCarrera(rs.getString(2));
                carrera.setGrado(rs.getString(3));
                carrera.setSeccion(rs.getString(4).charAt(0));
                carrera.setJornada(rs.getString(5));

                lista.add(carrera);
                System.out.println(carrera.toString());
            }
            listaCarrera = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la lista Carreras Tecnicas");
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

        return listaCarrera;
    }

    public void cargarDatos() {
        tblCursos.setItems(getCursos());
        colCarne.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCarreraTecnica.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));

        cmbSalon.setItems(getSalones());
        cmbCarreraTecnica.setItems(getCarreras());
        cmbHorarios.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());

        /*ObservableList<Salones> listaSalon = FXCollections.observableArrayList(getSalones());
        cmbSalon.getItems().addAll(listaSalones);*/

 /*ObservableList<CarrerasTecnicas> listaCarrera = FXCollections.observableArrayList(getCarrerasTecnicas());
        cmbCarrera.getItems().addAll(listaCarreras);

        ObservableList<Horarios> listaHorario = FXCollections.observableArrayList(getHorarios());
        cmbHorario.getItems().addAll(listaHorarios);

        ObservableList<Instructores> listaInstructor = FXCollections.observableArrayList(getInstructores());
        cmbIndtructor.getItems().addAll(listaInstructores);*/
    }

    private boolean agregarCursos() {
        Cursos curso = new Cursos();
        curso.setNombreCurso(txtNombreCursos.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        curso.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica.getSelectionModel().getSelectedItem()).getCodigoTecnico());
        curso.setHorarioId(((Horarios) cmbHorarios.getSelectionModel().getSelectedItem()).getId());
        curso.setInstructorId(((Instructores) cmbInstructor.getSelectionModel().getSelectedItem()).getId());
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_create(?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, curso.getNombreCurso());
            pstmt.setInt(2, curso.getCiclo());
            pstmt.setInt(3, curso.getCupoMaximo());
            pstmt.setInt(4, curso.getCupoMinimo());
            pstmt.setString(5, curso.getCarreraTecnicaId());
            pstmt.setInt(6, curso.getHorarioId());
            pstmt.setInt(7, curso.getInstructorId());
            pstmt.setString(8, curso.getSalonId());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + curso.toString());
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

    public boolean actualizarCursos() {
        Cursos cursos = new Cursos();

        cursos.setId(Integer.parseInt(txtId.getText()));
        cursos.setNombreCurso(txtNombreCursos.getText());
        cursos.setCiclo(spnCiclo.getValue());
        cursos.setCupoMaximo(spnCupoMaximo.getValue());
        cursos.setCupoMinimo(spnCupoMinimo.getValue());
        cursos.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica.getSelectionModel().getSelectedItem()).getCodigoTecnico());
        cursos.setHorarioId(((Horarios) cmbHorarios.getSelectionModel().getSelectedItem()).getId());
        cursos.setInstructorId(((Instructores) cmbInstructor.getSelectionModel().getSelectedItem()).getId());
        cursos.setSalonId(((Salones) cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_cursos_update(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, cursos.getId());
            pstmt.setString(2, cursos.getNombreCurso());
            pstmt.setInt(3, cursos.getCiclo());
            pstmt.setInt(4, cursos.getCupoMaximo());
            pstmt.setInt(5, cursos.getCupoMinimo());
            pstmt.setString(6, cursos.getCarreraTecnicaId());
            pstmt.setInt(7, cursos.getHorarioId());
            pstmt.setInt(8, cursos.getInstructorId());
            pstmt.setString(9, cursos.getSalonId());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("\nSucedio un error al intentar actualizar el siguiente registro: " + cursos.toString());
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

    public boolean eliminarCursos() {
        if (existeElementoSeleccionado()) {
            Cursos cursos = ((Cursos) tblCursos.getSelectionModel().getSelectedItem());
            System.out.println(cursos.toString());
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_cursos_delete(?)}");
                pstmt.setString(1, String.valueOf(cursos.getId()));
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("\nSe produjo un error al intentar eliminar el siguiente registro " + cursos.toString());
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

    private boolean existeElementoSeleccionado() {
        return (tblCursos.getSelectionModel().getSelectedItem() != null);
    }

    // CLIC NUEVO
    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblCursos.setDisable(true);

                txtId.setEditable(true);
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
                if (txtNombreCursos.getText().isEmpty()) {
                    validacionI();
                } else if (cmbCarreraTecnica.getValue() == null) {
                    validacionI();
                } else if (cmbHorarios.getValue() == null) {
                    validacionI();
                } else if (cmbInstructor.getValue() == null) {
                    validacionI();
                } else if (cmbSalon.getValue() == null) {
                    validacionI();
                }else
                if (agregarCursos()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblCursos.setDisable(false);

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
                if (existeElementoSeleccionado()) {
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

                tblCursos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (txtNombreCursos.getText().isEmpty()) {
                    validacionI();
                } else if (cmbCarreraTecnica.getValue() == null) {
                    validacionI();
                } else if (cmbHorarios.getValue() == null) {
                    validacionI();
                } else if (cmbInstructor.getValue() == null) {
                    validacionI();
                } else if (cmbSalon.getValue() == null) {
                    validacionI();
                }else
                 if (existeElementoSeleccionado()) {
                    if (actualizarCursos()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblCursos.setDisable(false);

                        tblCursos.getSelectionModel().clearSelection();

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

                tblCursos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert alertaC = new Alert(Alert.AlertType.CONFIRMATION);
                    alertaC.setTitle("Control Academico - El Bosque");
                    alertaC.setHeaderText(null);
                    alertaC.setContentText("¿Seguro que quieres eliminar el registro?");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alertaC.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));

                    Optional<ButtonType> botonC = alertaC.showAndWait();

                    if (botonC.get().equals(ButtonType.OK)) {

                        if (eliminarCursos()) {

                            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
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
                        tblCursos.getSelectionModel().clearSelection();
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
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getId()));
            txtNombreCursos.setText(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getNombreCurso());
            spnCiclo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCiclo());
            spnCupoMaximo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCupoMaximo());
            spnCupoMinimo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCupoMinimo());

            cmbCarreraTecnica.getSelectionModel().select(buscarCarrerasTecnicas(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCarreraTecnicaId()));

            cmbHorarios.getSelectionModel().select(buscarHorario(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getHorarioId()));

            cmbInstructor.getSelectionModel().select(buscarInstructor(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getInstructorId()));

            cmbSalon.getSelectionModel().select(buscarSalones(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getSalonId()));
        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Horarios horario = new Horarios(
                        rs.getInt("id"),
                        rs.getTime("horario_inicio").toLocalTime(),
                        rs.getTime("horario_final").toLocalTime(),
                        rs.getBoolean("lunes"),
                        rs.getBoolean("martes"),
                        rs.getBoolean("miercoles"),
                        rs.getBoolean("jueves"),
                        rs.getBoolean("viernes")
                );

                System.out.println(horario.toString());

                arrayListHorarios.add(horario);
            }

            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Horarios");
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

        return listaObservableHorarios;
    }

    private Horarios buscarHorario(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Horarios horario = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horario_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                horario = new Horarios(
                        rs.getInt("id"),
                        rs.getTime("horario_inicio").toLocalTime(),
                        rs.getTime("horario_final").toLocalTime(),
                        rs.getBoolean("lunes"),
                        rs.getBoolean("martes"),
                        rs.getBoolean("miercoles"),
                        rs.getBoolean("jueves"),
                        rs.getBoolean("viernes")
                );

                System.out.println(horario.toString());
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Horarios");
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

        return horario;
    }

    private CarrerasTecnicas buscarCarrerasTecnicas(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        CarrerasTecnicas carreraTecnica = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                carreraTecnica = new CarrerasTecnicas();

                carreraTecnica.setCodigoTecnico(rs.getString("codigo_tecnico"));
                carreraTecnica.setCarrera(rs.getString("carrera"));
                carreraTecnica.setGrado(rs.getString("grado"));
                carreraTecnica.setSeccion(rs.getString("seccion").charAt(0));
                carreraTecnica.setJornada(rs.getString("jornada"));

                System.out.println(carreraTecnica);
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carreras Técnicas");
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

        return carreraTecnica;
    }

    private Salones buscarSalones(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Salones salon = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());

            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Salones");
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
        return salon;
    }

    private ObservableList getInstructores() {
        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                System.out.println(instructor);

                arrayListInstructores.add(instructor);
            }

            listaObservableInstructores = FXCollections.observableArrayList(arrayListInstructores);

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

        return listaObservableInstructores;
    }

    private Instructores buscarInstructor(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Instructores instructor = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                System.out.println(instructor);
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Instructores");
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

        return instructor;
    }

    private void validacionI() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Control Academico - El Bosque");
        alerta.setHeaderText(null);
        Stage stagee = (Stage) alerta.getDialogPane().getScene().getWindow();
        stagee.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        alerta.show();

        if (txtNombreCursos.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar Nombre Curso");
        } else if (cmbCarreraTecnica.getValue() == null) {
            alerta.setContentText("Le falta ingresar el id Carreras Tecnicas");
        } else if (cmbHorarios.getValue() == null) {
            alerta.setContentText("Le falta ingresar el id Carreras Tecnicas");
        } else if (cmbInstructor.getValue() == null) {
            alerta.setContentText("Le falta ingresar el id Carreras Tecnicas");
        } else if (cmbSalon.getValue() == null) {
            alerta.setContentText("Le falta ingresar el id Carreras Tecnicas");
        }
    }
}
