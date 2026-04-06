package cl.FixNow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Operacion {

    private int id, nvlPrioridad, fechaRegistro;
    private String descripcion, usuarioRepor, estado;
}
