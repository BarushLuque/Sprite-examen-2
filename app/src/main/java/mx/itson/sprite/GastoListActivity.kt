package mx.itson.sprite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ListView
import mx.itson.sprite.adapters.GastoAdapter
import mx.itson.sprite.entities.Gasto

class GastoListActivity : AppCompatActivity() {

    lateinit var listGastos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gasto_list)

        listGastos = findViewById(R.id.listGastos)

        val gasto = Gasto()
        val lista = gasto.getAll(this).toMutableList()

        val adapter = GastoAdapter(this, lista)
        listGastos.adapter = adapter
    }
}