
package org.in5bm.davidquiñonez.eldrickhernandez.models;

import java.time.LocalTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 8 jun. 2022
 * @time 23:00:07
 *
 * Código Técnico: IN5BM
 */
public class Horarios {

    private IntegerProperty id;
    private ObjectProperty<LocalTime> fInicio;
    private ObjectProperty<LocalTime> fFinal;
    private BooleanProperty lunes;
    private BooleanProperty martes;
    private BooleanProperty miercoles;
    private BooleanProperty jueves;
    private BooleanProperty viernes;
    
    public Horarios(){
        this.id = new SimpleIntegerProperty();
        this.fFinal = new SimpleObjectProperty<>();
        this.fInicio = new SimpleObjectProperty<>();
        this.lunes = new SimpleBooleanProperty();
        this.martes = new SimpleBooleanProperty();
        this.miercoles = new SimpleBooleanProperty();
        this.jueves = new SimpleBooleanProperty();
        this.viernes = new SimpleBooleanProperty();
    }
    
    public Horarios(int id, LocalTime fInicio, LocalTime fFinal, boolean lunes,
            boolean martes, boolean miercoles, boolean jueves, boolean viernes){
        this.id = new SimpleIntegerProperty(id);
        this.fFinal = new SimpleObjectProperty<>(fFinal);
        this.fInicio = new SimpleObjectProperty<>(fInicio);
        this.lunes = new SimpleBooleanProperty(lunes);
        this.martes = new SimpleBooleanProperty(martes);
        this.miercoles = new SimpleBooleanProperty(miercoles);
        this.jueves = new SimpleBooleanProperty(jueves);
        this.viernes = new SimpleBooleanProperty(viernes);
    }
    
    public IntegerProperty id(){
        return id;
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public ObjectProperty<LocalTime> fInicio(){
        return fInicio;
    }
    
    public LocalTime getFInicio(){
        return fInicio.get();
    }
    
    public void setFInicio(LocalTime fInicio){
        this.fInicio.set(fInicio);
    }
    
    public ObjectProperty<LocalTime> fFinal(){
        return fFinal;
    }
    
    public LocalTime getFFinal(){
        return fFinal.get();
    }
    
    public void setFFinal(LocalTime fFinal){
        this.fFinal.set(fFinal);
    }
    
    public BooleanProperty lunes(){
        return lunes;
    }
    
    public boolean getLunes(){
        return lunes.get();
    }
    
    public void setLunes(boolean lunes){
        this.lunes.set(lunes);
    }
    public BooleanProperty martes(){
        return martes;
    }
    
    public boolean getMartes(){
        return martes.get();
    }
    
    public void setMartes(boolean martes){
        this.martes.set(martes);
    }
    public BooleanProperty miercoles(){
        return miercoles;
    }
    
    public boolean getMiercoles(){
        return miercoles.get();
    }
    
    public void setMiercoles(boolean miercoles){
        this.miercoles.set(miercoles);
    }
    public BooleanProperty Juees(){
        return jueves;
    }
    
    public boolean getJueves(){
        return jueves.get();
    }
    
    public void setJueves(boolean jueves){
        this.jueves.set(jueves);
    }
    public BooleanProperty viernes(){
        return viernes;
    }
    
    public boolean getViernes(){
        return viernes.get();
    }
    
    public void setViernes(boolean viernes){
        this.viernes.set(viernes);
    }

    @Override
    public String toString() {
        return   id.get() + " | " + fInicio.get() + " | " + fFinal.get() ;
    }
    
    
    
}
