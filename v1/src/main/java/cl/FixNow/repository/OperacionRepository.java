package cl.FixNow.repository;

import org.springframework.stereotype.Repository;

import cl.FixNow.model.Operacion;
import java.util.ArrayList;
import java.util.List;
@Repository

public class OperacionRepository {
    //definir como lista
    private List <Operacion> listaOpe= new ArrayList<>(); 
    //Realizar consultas específicas sobre las incidencias registradas
    public List<Operacion> obtenerOpe(){
        return listaOpe;
    }
    //Consultar incidencias existentes
    public Operacion buscarOpeId(int id){
        for(Operacion ope: listaOpe){
            if(ope.getId() == id){
                return ope;
            }
        }
        return null;
    }
    //Registrar nuevas incidencias
    public Operacion guardarOpe(Operacion ope){
        listaOpe.add(ope);
        return null;
    }
    //Actualizar información de incidencias
    public Operacion actuOpe (Operacion ope){
        int id= 0;
        int idPos= 0;
        for (int i= 0; i<listaOpe.size(); i++){
            if(listaOpe.get(i).getId()== ope.getId()){
                id= ope.getId();
                idPos= 1;
            }
        }

        Operacion ope1= new Operacion();
        ope1.setId(id);
        ope1.setNvlPrioridad(ope.getNvlPrioridad());
        ope1.setFechaRegistro(ope.getFechaRegistro());
        ope1.setDescripcion(ope.getDescripcion());
        ope1.setUsuarioRepor(ope.getUsuarioRepor());
        ope1.setEstado(ope.getEstado());

        listaOpe.set(idPos, ope1);

        return ope1;

    }
    //Eliminar incidencias del sistema
    public void elim(int id){
        int idPos= 0;
        for(int i= 0; i< listaOpe.size(); i++){
            if(listaOpe.get(i).getId()== id){
                idPos=1;
                break;
            }
        }
        if(idPos> 0){
            listaOpe.remove(idPos);
        }
    }
    //Realizar consultas específicas sobre las incidencias registradas

}
