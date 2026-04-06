package cl.FixNow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.FixNow.model.Operacion;
import cl.FixNow.repository.OperacionRepository;
import java.util.List;

@Service
public class OperacionService {
    @Autowired
    //definir la clase como un objeto usable
    private OperacionRepository opeRepo;
    //Buscar existentes
    public List<Operacion> getOpe(){
        return opeRepo.obtenerOpe();
    }
    //Consulta especifica
    public Operacion getOpeId(int id){
        return opeRepo.buscarOpeId(id);
    }
    //Guardar
    public Operacion saveOpe(Operacion ope){
        return opeRepo.guardarOpe(ope);
    }
    //Actualizar
    public Operacion updateOpe(Operacion ope){
        return opeRepo.actuOpe(ope);
    }
    //Eliminar
    public String deleteOpe(int id){
        opeRepo.elim(id);
        return "incidencia eliminada";
    }
}
