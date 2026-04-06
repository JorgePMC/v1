package cl.FixNow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.FixNow.model.Operacion;
import cl.FixNow.service.OperacionService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/FixNow")
public class OperacionController {
    @Autowired
    //se define la clase anterior como objeto usable
    private OperacionService opeSer;
    //renombrar la lista por la lista anterior(Buscar existentes)
    @GetMapping
    public List<Operacion> listarOpe(){
        return opeSer.getOpe();
    }
    //Consulta especifica
    @GetMapping("{id}")
    public Operacion BuscarOpe(@PathVariable int id){
        return opeSer.getOpeId(id);
    }
    //guardar
    @PostMapping
    public Operacion GuardarOpe(@RequestBody Operacion ope){
        return opeSer.saveOpe(ope);
    }
    //actualizar
    @PutMapping("{id}")
    public Operacion ActualizarOpe(@PathVariable int id, @RequestBody Operacion ope){
        return opeSer.updateOpe(ope);
    }
    //Eliminar
    @DeleteMapping("{id}")
    public String EliminarOpe(@PathVariable int id){
        return opeSer.deleteOpe(id);
    }


}
