package cl.inacap.preventivoscovid.dao;

import java.util.List;

import cl.inacap.preventivoscovid.dto.Paciente;

public interface PacientesDAO {
    List<Paciente> getAll();

    Paciente save(Paciente p);
}
