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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bm.davidquiñonez.eldrickhernandez.db.Conexion;
import org.in5bm.davidquiñonez.eldrickhernandez.models.CarrerasTecnicas;
import org.in5bm.davidquiñonez.eldrickhernandez.system.Principal;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 29 abr. 2022
 * @time 10:10:56
 *
 * CodigoTecnico: IN5BM
 */
public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGES = "org/in5bm/davidquiñonez/eldrickhernandez/resources/images/";

    private Principal escenarioPrincipal;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtCodigoTec;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtJornada;
    @FXML
    private TableView tblCarrerasTecnicas;
    @FXML
    private TableColumn ColCodigoTec;
    @FXML
    private TableColumn ColCarrera;
    @FXML
    private TableColumn ColGrado;
    @FXML
    private TableColumn ColSeccion;
    @FXML
    private TableColumn ColJornada;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgModificar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private ObservableList<CarrerasTecnicas> listaCarrera;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /* METODOS */
    // DESHABILITAR CAMPOS
    private void deshabilitarCampos() {
        txtCodigoTec.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTec.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtJornada.setDisable(true);
        txtSeccion.setDisable(true);

    }

    //HABILITAR CAMPOS
    private void habilitarCampos() {
        tblCarrerasTecnicas.setDisable(true);
        txtCodigoTec.setEditable(false);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtCarrera.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTec.setDisable(true);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtCarrera.setDisable(false);
        txtJornada.setDisable(false);
    }

    //LIMPIAR CAMPOS
    public void limpiarCampos() {
        txtCodigoTec.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtCarrera.clear();
        txtJornada.clear();
    }

    // AGREGAR CARRERAS
    private boolean agregarCarreras() {

        CarrerasTecnicas carreras = new CarrerasTecnicas();

        carreras.setCodigoTecnico(txtCodigoTec.getText());
        carreras.setCarrera(txtCarrera.getText());
        carreras.setGrado(txtGrado.getText());
        carreras.setJornada(txtJornada.getText());
        carreras.setSeccion(txtSeccion.getText().charAt(0));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_create(?,?,?,?,?)}");
            pstmt.setString(1, carreras.getCodigoTecnico());
            pstmt.setString(2, carreras.getCarrera());
            pstmt.setString(3, carreras.getGrado());
            pstmt.setString(4, String.valueOf(carreras.getSeccion()));
            pstmt.setString(5, carreras.getJornada());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaCarrera.add(carreras);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al insertar la siguiente  carrera" + carreras.toString());
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

    //ACTUALIZAR CARRERAS
    private boolean actualizarCarrera() {
        CarrerasTecnicas carreras = new CarrerasTecnicas();
        carreras.setCodigoTecnico(txtCodigoTec.getText());
        carreras.setCarrera(txtCarrera.getText());
        carreras.setGrado(txtGrado.getText());
        carreras.setJornada(txtJornada.getText());
        carreras.setSeccion(txtSeccion.getText().charAt(0));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_update(?,?,?,?,?)");
            pstmt.setString(1, carreras.getCodigoTecnico());
            pstmt.setString(2, carreras.getCarrera());
            pstmt.setString(3, carreras.getGrado());
            pstmt.setString(4, String.valueOf(carreras.getSeccion()));
            pstmt.setString(5, carreras.getJornada());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("\nSucedio un error al intentar actualizar el siguiente registro: " + carreras.toString());
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

    //ELIMINAR CARRERAS
    public boolean eliminarCarrera() {
        if (existeElemento()) {
            CarrerasTecnicas carreras = ((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem());
            System.out.println(carreras.toString());
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carreras.getCodigoTecnico());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("\nSe produjo un error al intentar eliminar el siguiente registro " + carreras.toString());
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

    //EXISTE ELEMENTO
    public boolean existeElemento() {
        return ((tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null));

    }

    /* CLICS */
    //CLIC REGRESAR
    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    //CLIC NUEVO
    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblCarrerasTecnicas.setDisable(true);

                txtCodigoTec.setEditable(true);
                txtCodigoTec.setDisable(false);

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

               if (txtCodigoTec.getText().isEmpty()) {
                    validacionI();
                } else if (txtCarrera.getText().isEmpty()) {
                    validacionI();
                } else if (txtGrado.getText().isEmpty()) {
                    validacionI();
                } else if (txtJornada.getText().isEmpty()) {
                    validacionI();
                } else if (txtSeccion.getText().isEmpty()) {
                    validacionI();
                } else if (agregarCarreras()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblCarrerasTecnicas.setDisable(false);

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
                    tblCarrerasTecnicas.setDisable(false);

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

                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElemento()) {
                    if (actualizarCarrera()) {
                        limpiarCampos();
                        cargarDatos();
                        deshabilitarCampos();

                        tblCarrerasTecnicas.setDisable(false);
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();

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

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

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
                        eliminarCarrera();
                        if (eliminarCarrera()) {
                            limpiarCampos();

                            listaCarrera.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
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
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
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
    private void clicReporte(ActionEvent event
    ) {
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

    /* CARGAR DATOS */
    @FXML
    private void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarreras());
        ColCodigoTec.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        ColCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        ColGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        ColJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
        ColSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));

    }

    /* SELECCIONAR ELEMENTOS */
    @FXML
    public void seleccionarElemento() {

        if (existeElemento()) {
            txtCodigoTec.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
            txtSeccion.setText(String.valueOf(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion()));

        }

    }

    public String crecarCodigo(String carrera, String grado, char seccion, String jornada) {
        String codigoConcatenado;

        char letraUno = carrera.charAt(0);
        char letraDos = carrera.charAt(1);
        char letraJornada = jornada.charAt(0);

        codigoConcatenado = String.valueOf(letraUno) + String.valueOf(letraDos)
                + grado + String.valueOf(seccion) + String.valueOf(letraJornada);
        codigoConcatenado = codigoConcatenado.toUpperCase();

        txtCodigoTec.setText(codigoConcatenado);

        return codigoConcatenado;
    }

    private void validacionI() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Control Academico - El Bosque");
        alerta.setHeaderText(null);
        alerta.setContentText("Le hace falta ingresar el Grado");
        Stage stagee = (Stage) alerta.getDialogPane().getScene().getWindow();
        stagee.getIcons().add(new Image(PAQUETE_IMAGES + "aprender-en-linea.png"));
        alerta.show();
        if (txtCodigoTec.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar el Codigo Tecnico");
        } else if (txtCarrera.getText().isEmpty()) {
            alerta.setContentText("Le falta ingresar la Carrera Tecnica");
        } else if (txtGrado.getText().isEmpty()) {
            alerta.setContentText("Le hace falta ingresar el Grado");
        } else if (txtJornada.getText().isEmpty()) {
            alerta.setContentText("Le hace falta ingresar la jornada");
        } else if (txtSeccion.getText().isEmpty()) {
            alerta.setContentText("Le hace falta ingresar la sección");
        }
    }

    public String crearCodigo(String carrera, String grado, char seccion, String jornada) {
        String codigoConcatenado;

        char letraUno = carrera.charAt(0);
        char letraDos = carrera.charAt(1);
        char letraJornada = jornada.charAt(0);

        codigoConcatenado = String.valueOf(letraUno) + String.valueOf(letraDos)
                + grado + String.valueOf(seccion) + String.valueOf(letraJornada);

        codigoConcatenado = codigoConcatenado.toUpperCase();

        txtCodigoTec.setText(codigoConcatenado);

        return codigoConcatenado;

    }

    private boolean evaluacionPK(String codigo) {
        boolean opcion = true;

        for (int i = 0; i < listaCarrera.size(); i++) {
            if (codigo.equals(listaCarrera.get(i).getCodigoTecnico())) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Control Academico Kinal");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo.png"));
                alert.setContentText("Esta carrera ya existe");
                alert.show();
                opcion = false;
                break;
            }
        }
        return opcion;
    }
}
