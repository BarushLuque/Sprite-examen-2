package mx.itson.sprite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ListView
import android.widget.TextView
import mx.itson.sprite.adapters.GastoAdapter
import mx.itson.sprite.entities.Gasto

class GastoListActivity : AppCompatActivity() {

    //ListView en donde se mostraran los gastos
    lateinit var listGastos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gasto_list)

        val btnRegresar = findViewById<TextView>(R.id.btnRegresar)
        btnRegresar?.setOnClickListener {
            finish()
        }

        //Inicializar el Listview
        listGastos = findViewById(R.id.listGastos)

        /**
         * Obtener los datos desde la base de datos
         */
        val gasto = Gasto()
        val lista = gasto.getAll(this).toMutableList()

        /**
         * Crea el adaptador con la lista de gastos
         */
        val adapter = GastoAdapter(this, lista)

        /**
         * Asigna el adaptador al ListView para que se muestren con el
         * Layout personalizado
         */
        listGastos.adapter = adapter
    }
}