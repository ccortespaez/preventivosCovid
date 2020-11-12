package cl.inacap.preventivoscovid.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.preventivoscovid.dto.Paciente;
import cl.inacap.preventivoscovid.helpers.PacientesSQLiteHelper;

public class PacientesDAOSQLite implements PacientesDAO{

    private PacientesSQLiteHelper pacienteHelper;

    public PacientesDAOSQLite(Context context){
        this.pacienteHelper = new PacientesSQLiteHelper(context, "DBPacientes", null,1);
    }
    @Override
    public List<Paciente> getAll() {
        SQLiteDatabase reader = this.pacienteHelper.getReadableDatabase();
        List<Paciente> pacientes = new ArrayList<>();
        try {
            if (reader != null){
                Cursor c = reader.rawQuery("SELECT id,nombre,apellido,rut,fecha,areaTrabajo,temperatura,presion,sintomas,tos FROM pacientes",null);
                if (c.moveToFirst()){
                    do{
                        Paciente p = new Paciente();
                        p.setId(c.getInt(0));
                        p.setNombre(c.getString(1));
                        p.setApellido(c.getString(2));
                        p.setRut(c.getString(3));
                        p.setFecha(c.getString(4));
                        p.setAreaTrabajo(c.getString(5));
                        p.setTemperatura(c.getInt(6));
                        p.setPresionArterial(c.getInt(7));
                        p.setSintomas(c.getString(8));
                        p.setTos(c.getString(9));
                        pacientes.add(p);
                    }while(c.moveToNext());
                }
                reader.close();
            }
        }catch (Exception ex){
            pacientes = null;
        }
        return pacientes;
    }

    @Override
    public Paciente save(Paciente p) {
        SQLiteDatabase writer = this.pacienteHelper.getWritableDatabase();
        String sql = String.format("INSERT INTO pacientes(nombre,apellido,rut,fecha,areaTrabajo,temperatura,presion,sintomas,tos)"+
                "VALUES('%s','%s','%s','%s','%s','%.2f','%d','%s','%s')",p.getNombre(),p.getApellido(),p.getRut()
                ,p.getFecha(),p.getAreaTrabajo(),p.getTemperatura()
                ,p.getPresionArterial(),p.getSintomas(),p.getTos());
        writer.execSQL(sql);
        writer.close();
        return p;
    }
}
