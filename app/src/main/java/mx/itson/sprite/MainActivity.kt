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

    lateinit var etProducto: EditText
    lateinit var etPrecio: EditText
    lateinit var etTienda: EditText
    lateinit var etFecha: EditText
    lateinit var btnGuardar: Button
    lateinit var btnVerLista: Button


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        etProducto = findViewById(R.id.etProducto)
        etPrecio = findViewById(R.id.etPrecio)
        etTienda = findViewById(R.id.etTienda)
        etFecha = findViewById(R.id.etFecha)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVerLista = findViewById(R.id.btnVerLista)


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


        btnGuardar.setOnClickListener {

            val producto = etProducto.text.toString()
            val precioTexto = etPrecio.text.toString()
            val tienda = etTienda.text.toString()
            val fecha = etFecha.text.toString()


            if (producto.isEmpty() || precioTexto.isEmpty() || tienda.isEmpty() || fecha.isEmpty()) {
                vibrate()
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioTexto.toDoubleOrNull()

            if (precio == null) {
                vibrate()
                Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gasto = Gasto()
            gasto.save(this, producto, precio, tienda, fecha)

            val lista = gasto.getAll(this)

            etProducto.text.clear()
            etPrecio.text.clear()
            etTienda.text.clear()
            etFecha.text.clear()

            vibrate()
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show()
        }

        btnVerLista.setOnClickListener {
            val intent = Intent(this, GastoListActivity::class.java)
            startActivity(intent)
        }


    }
}