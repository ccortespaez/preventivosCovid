package cl.inacap.preventivoscovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cl.inacap.preventivoscovid.dao.PacientesDAO;
import cl.inacap.preventivoscovid.dao.PacientesDAOSQLite;
import cl.inacap.preventivoscovid.dto.Paciente;

public class CrearPacienteActivity extends AppCompatActivity{
    private PacientesDAO pacientesDAO = new PacientesDAOSQLite(this);
    private List<Paciente> pacientes = new ArrayList<>();
    private Spinner spinnerArea;
    private Toolbar toolbar;
    private EditText nombreTxt;
    private EditText apellidoTxt;
    private EditText rutTxt;
    private EditText temperaturaTxt;
    private EditText presionTxt;
    private Button calendarioTxt;
    private Button registrarBtn;
    private Switch sintomasSw;
    private Switch tosSw;
    private ArrayAdapter<Paciente> adapter;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_paciente);
        this.toolbar = findViewById(R.id.toolbar);
        this.nombreTxt = findViewById(R.id.nombre_paciente_txt);
        this.apellidoTxt = findViewById(R.id.apellido_paciente_txt);
        this.rutTxt = findViewById(R.id.rut_paciente_txt);
        this.temperaturaTxt = findViewById(R.id.temperatura_paciente_txt);
        this.presionTxt = findViewById(R.id.presion_paciente_txt);
        this.spinnerArea = findViewById(R.id.spinnerArea);
        this.calendarioTxt = findViewById(R.id.fecha);
        this.sintomasSw = findViewById(R.id.switchSintoma);
        this.tosSw = findViewById(R.id.switchTos);
        this.registrarBtn = findViewById(R.id.registrarBtn);
        this.adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, pacientes);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        String [] areaTrabajo= {"Seleccione","Atencion a publico", "Otros"};
        ArrayAdapter <String> adapterGenero = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, areaTrabajo);
        spinnerArea.setAdapter(adapterGenero);

        this.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();
                String nombre = nombreTxt.getText().toString().trim();
                String apellido = apellidoTxt.getText().toString().trim();
                String rut = rutTxt.getText().toString().trim();
                String fecha = calendarioTxt.getText().toString();
                String temperaturaStr = temperaturaTxt.getText().toString().trim();
                String areaTrabajo = spinnerArea.getSelectedItem().toString();

                int temperatura = 0;
                if (nombre.isEmpty()){
                    errores.add(" Debe ingresar el nombre del paciente");
                }
                if (apellido.isEmpty()){
                    errores.add(" Debe ingresar el apellido del paciente");
                }
                if (validaRut(rut)==false){
                    errores.add(" Debe ingresar un rut valido");
                }
                if (fecha.isEmpty()){
                    errores.add(" Debe ingresar una fecha valida");
                }

                try {
                    temperatura = Integer.parseInt(temperaturaStr);
                    if (temperatura<20){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    errores.add(" Debe ingresar la temperatura del paciente");
                }
                if (areaTrabajo.equals("Seleccione")){
                    errores.add(" Debe seleccionar un area de trabajo");
                }

                try {
                    if (errores.isEmpty()){
                        Paciente p = new Paciente();
                        p.setNombre(nombreTxt.getText().toString());
                        p.setApellido(apellidoTxt.getText().toString());
                        p.setRut(rutTxt.getText().toString());
                        p.setFecha(calendarioTxt.getText().toString());
                        p.setAreaTrabajo(spinnerArea.getSelectedItem().toString());
                        p.setTemperatura(Float.parseFloat(temperaturaTxt.getText().toString()));
                        p.setPresionArterial(Integer.parseInt(presionTxt.getText().toString()));
                        p.setSintomas(sintomasSw.getText().toString());
                        p.setTos(tosSw.getText().toString());
                        if(sintomasSw.isChecked()){
                            p.setSintomas("Si");
                        }else{
                            p.setSintomas("No");
                        }
                        if(tosSw.isChecked()){
                            p.setTos("Si");
                        }else{
                            p.setTos("No");
                        }
                        pacientesDAO.save(p);
                        startActivity(new Intent(CrearPacienteActivity.this, PrincipalActivity.class));
                    }else{
                        mostrarErrores(errores);
                    }
                }catch(Exception ex){
                }
            }

            private boolean validaRut(String rut) {
                    Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
                    Matcher matcher = pattern.matcher(rut);
                    if ( matcher.matches() == false ) return false;
                    String[] stringRut = rut.split("-");
                    return stringRut[1].toLowerCase().equals(CrearPacienteActivity.digitoVerificador(stringRut[0]));
            }
        });
    }

    public static String digitoVerificador ( String rut ) {
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        return ( S > 0 ) ? String.valueOf(S-1) : "k";
    }

    private void mostrarErrores(List<String> errores){
        String mensaje = "";
        for (String e: errores) {
            mensaje += "-" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CrearPacienteActivity.this);
        alertBuilder.setTitle("Error de validación")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    public void abrirCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONDAY);
        int anio = cal.get(Calendar.YEAR);
        DatePickerDialog dpd = new DatePickerDialog(CrearPacienteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth +"/"+ (month+1) + "/" +year;
                calendarioTxt.setText(fecha);
            }
        }, dia, mes, anio);
        dpd.show();
    }

}