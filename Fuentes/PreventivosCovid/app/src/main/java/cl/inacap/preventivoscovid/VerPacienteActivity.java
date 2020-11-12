package cl.inacap.preventivoscovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;


import cl.inacap.preventivoscovid.dto.Paciente;

public class VerPacienteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView nombreTxt;
    private TextView rutTxt;
    private TextView fechaTxt;
    private TextView temperaturaTxt;
    private TextView sintomasTxt;
    private TextView tosTxt;
    private TextView areaTrabajoTxt;
    private TextView presionTxt;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_paciente);
        this.toolbar = findViewById(R.id.toolbar);
        this.nombreTxt = findViewById(R.id.nombrePacienteTxt);
        this.rutTxt = findViewById(R.id.rutPacienteTxt);
        this.fechaTxt = findViewById(R.id.fechaPacienteTxt);
        this.presionTxt = findViewById(R.id.presionPacienteTxt);
        this.tosTxt = findViewById(R.id.tosPacienteTxt);
        this.sintomasTxt = findViewById(R.id.sintomasPacienteTxt);
        this.areaTrabajoTxt = findViewById(R.id.areaPacienteTxt);
        this.temperaturaTxt = findViewById(R.id.temperaturaPacienteTxt);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent() != null){
            Paciente paciente = (Paciente)getIntent().getSerializableExtra("paciente");
            this.nombreTxt.setText("Nombre: "+paciente.getNombre() + " "+ paciente.getApellido());
            this.rutTxt.setText("Rut: "+paciente.getRut());
            this.fechaTxt.setText("Fecha: "+paciente.getFecha());
            this.presionTxt.setText("Presion Arterial: "+paciente.getPresionArterial());
            this.temperaturaTxt.setText("Temperatura: "+paciente.getTemperatura());
            this.sintomasTxt.setText("Sintomas: = "+paciente.getSintomas());
            this.tosTxt.setText("Tos: "+paciente.getTos());
            this.areaTrabajoTxt.setText("Area de trabajo: "+paciente.getAreaTrabajo());
        }
    }
}