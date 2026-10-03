package com.example.cargainteligente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private View panelContenido;
    private View panelCarga;
    private TextView txtEstado;
    private Button btnActualizar;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        panelContenido = findViewById(R.id.panelContenido);
        panelCarga = findViewById(R.id.panelCarga);
        txtEstado = findViewById(R.id.txtEstado);
        btnActualizar = findViewById(R.id.btnActualizar);

        // Mostrar círculo de carga
        panelCarga.setVisibility(View.VISIBLE);
        panelContenido.setVisibility(View.GONE);

        // Carga inicial
        prepararDatosIniciales();

        // Botón actualizar
        btnActualizar.setOnClickListener(
                view -> actualizarDatos()
        );
    }

    private void prepararDatosIniciales() {

        executor.execute(() -> {

            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            runOnUiThread(() -> {

                panelCarga.setVisibility(View.GONE);
                panelContenido.setVisibility(View.VISIBLE);

                txtEstado.setText(
                        R.string.mensaje_inicial
                );
            });
        });
    }

    private void actualizarDatos() {

        // Mostrar círculo
        panelCarga.setVisibility(View.VISIBLE);
        panelContenido.setVisibility(View.GONE);

        executor.execute(() -> {

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            runOnUiThread(() -> {

                panelCarga.setVisibility(View.GONE);
                panelContenido.setVisibility(View.VISIBLE);

                txtEstado.setText(
                        R.string.estado_actualizado
                );
            });
        });
    }

    @Override
    protected void onDestroy() {

        executor.shutdownNow();

        super.onDestroy();
    }
}