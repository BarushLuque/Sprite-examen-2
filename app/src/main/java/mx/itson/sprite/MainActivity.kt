package mx.itson.sprite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.sprite.entities.Gasto
import android.app.DatePickerDialog
import java.util.Calendar
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class MainActivity : AppCompatActivity() {

    // Campos de entrada
    lateinit var etProducto: EditText
    lateinit var etPrecio: EditText
    lateinit var etTienda: EditText
    lateinit var etFecha: EditText

    //Botones
    lateinit var btnGuardar: Button
    lateinit var btnVerLista: Button


    /**
     * Metodo para generar una vibración en el dispositivo
     */
    fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(300)
        }
    }


    /**
     * Metodo principal que se ejecuta al crear la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Ajusta los margenes de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Inicialización de los componentes visuales
        etProducto = findViewById(R.id.etProducto)
        etPrecio = findViewById(R.id.etPrecio)
        etTienda = findViewById(R.id.etTienda)
        etFecha = findViewById(R.id.etFecha)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVerLista = findViewById(R.id.btnVerLista)


        /**
         * Evento para seleccionar la fecha a modo de calendario y
         * evitar que el usuario ingrese texto invalido
         */
        etFecha.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val fecha = "%02d/%02d/%d".format(selectedDay, selectedMonth + 1, selectedYear)
                    etFecha.setText(fecha)

                }, year, month, day)

            datePicker.show()
        }


        /**
         * Evento para el boton guardar.
         * Primero valida los campos, después los guarda y al final los limpia.
         */
        btnGuardar.setOnClickListener {

            val producto = etProducto.text.toString()
            val precioTexto = etPrecio.text.toString()
            val tienda = etTienda.text.toString()
            val fecha = etFecha.text.toString()


            //Valida que no haya campos vacios y de haberlos le notifica al usuario con
            // un mensaje y vibración.
            if (producto.isEmpty() || precioTexto.isEmpty() || tienda.isEmpty() || fecha.isEmpty()) {
                vibrate()
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Valida que el precio sea numerico
            val precio = precioTexto.toDoubleOrNull()

            if (precio == null) {
                vibrate()
                Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guarda el objeto gasto en la base de datos con los datos asignados
            val gasto = Gasto()
            gasto.save(this, producto, precio, tienda, fecha)

            //Limpia los campos
            etProducto.text.clear()
            etPrecio.text.clear()
            etTienda.text.clear()
            etFecha.text.clear()

            // Vibra y muestra un mensaje notificando al usuario de que
            // se guardo correctamente.
            vibrate()
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show()
        }

        /**
         * Cambiar a la pantalla donde se muestra la lista de gastos
         */
        btnVerLista.setOnClickListener {
            val intent = Intent(this, GastoListActivity::class.java)
            startActivity(intent)
        }




    }
}