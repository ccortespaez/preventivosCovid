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
    private TextView apellidoTxt;
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
        this.apellidoTxt = findViewById(R.id.apellidoPacienteTxt);
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
            this.nombreTxt.setText(paciente.getNombre());
            this.apellidoTxt.setText(paciente.getApellido());
            this.rutTxt.setText(paciente.getRut());
            this.fechaTxt.setText(paciente.getFecha());
            this.presionTxt.setText(""+paciente.getPresionArterial());
            this.temperaturaTxt.setText(""+paciente.getTemperatura());
            this.sintomasTxt.setText(paciente.getSintomas());
            this.tosTxt.setText(paciente.getTos());
            this.areaTrabajoTxt.setText(paciente.getAreaTrabajo());
        }
    }
}