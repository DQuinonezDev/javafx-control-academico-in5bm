package org.in5bm.davidquiñonez.eldrickhernandez.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author informatica
 */
public class AsignacionesAlumnos {

    private IntegerProperty id;
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDateTime> fechaAsignacion;

    public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }

    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDateTime fechaAsignacion){
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }
    
    //GETTERS Y SETTERS
    public IntegerProperty id(){
        return id;
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public StringProperty alumnoId(){
        return alumnoId;
    }
    
    public String getAlumnoId(){
        return alumnoId.get();
    }
    
    public void setAlumnoId(String AlumnoId){
        this.alumnoId.set(AlumnoId);
    }
    
    public IntegerProperty cursoId(){
        return cursoId;
    }
    
    public int getCursoId(){
        return cursoId.get();
    }
    
    public void setCursoId(int cursoId){
        this.cursoId.set(cursoId);
    }
    
    public ObjectProperty<LocalDateTime> fechaAsignacion(){
        return fechaAsignacion();
    }
    
    public LocalDateTime getFechaAsignacion(){
        return fechaAsignacion.get();
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion){
        this.fechaAsignacion.set(fechaAsignacion);
    }
    
    
}
