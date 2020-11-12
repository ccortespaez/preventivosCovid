package cl.inacap.preventivoscovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText rutLogin;
    private EditText contrasenaLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        this.rutLogin = findViewById(R.id.rutLogin);
        this.contrasenaLogin = findViewById(R.id.contrasenaLogin);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rut = rutLogin.getText().toString();
                String contrasena = contrasenaLogin.getText().toString();
                try {
                    if (!validaRut(rut)) {
                        Toast.makeText(MainActivity.this
                                , "Nombre de usuario invalido"
                                , Toast.LENGTH_SHORT).show();
                    }
                    //123 4567 -0
                    if (!rut.isEmpty()){
                        if (rut.length() == 9 ){
                            if (contrasena.equals(rut.substring(3,7))){
                                startActivity(new Intent(MainActivity.this, PrincipalActivity.class ));
                            }else{
                                Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Debe ingresar nombre de usuario", Toast.LENGTH_SHORT).show();
                    }
                    //1234 5689 -0
                    if (!rut.isEmpty()){
                        if (rut.length() == 10 ){
                            if (contrasena.equals(rut.substring(4,8))){
                                startActivity(new Intent(MainActivity.this, PrincipalActivity.class ));
                            }else{
                                Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }catch(Exception ex){

                }
            }
        });
    }

    public static Boolean validaRut(String rut) {
        Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
        Matcher matcher = pattern.matcher(rut);
        if ( matcher.matches() == false ) return false;
        String[] stringRut = rut.split("-");
        return stringRut[1].toLowerCase().equals(MainActivity.digitoVerificador(stringRut[0]));
    }
    public static String digitoVerificador ( String rut ) {
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        return ( S > 0 ) ? String.valueOf(S-1) : "k";
    }
}

