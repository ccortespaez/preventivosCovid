package cl.inacap.preventivoscovid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cl.inacap.preventivoscovid.R;
import cl.inacap.preventivoscovid.dto.Paciente;

public class PacientesArrayAdapter extends ArrayAdapter<Paciente> {
    private List<Paciente> pacientes;
    private Activity activity;

    public PacientesArrayAdapter(@NonNull Activity context, int resource, @NonNull List<Paciente> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.pacientes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View fila = inflater.inflate(R.layout.pacientes_list, null, true);
        TextView datos = fila.findViewById(R.id.nombre_paciente_lv);
        Paciente actual = pacientes.get(position);
        datos.setText("Nombre: "+actual.getNombre()+" "+actual.getApellido()+" Rut:"+ actual.getRut()+" Fecha:"+actual.getFecha());
        return fila;
    }


}
