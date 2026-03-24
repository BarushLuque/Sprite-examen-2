package mx.itson.sprite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import mx.itson.sprite.R
import mx.itson.sprite.entities.Gasto

class GastoAdapter(
    var context: Context,
    var gastoList: MutableList<Gasto>
) : BaseAdapter() {

    override fun getCount(): Int {
        return gastoList.size
    }

    override fun getItem(position: Int): Any {
        return gastoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val element = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.gasto_item, parent, false)

        val gasto = gastoList[position]

        val txtProducto: TextView = element.findViewById(R.id.txtProducto)
        val txtPrecio: TextView = element.findViewById(R.id.txtPrecio)
        val txtTienda: TextView = element.findViewById(R.id.txtTienda)
        val txtFecha: TextView = element.findViewById(R.id.txtFecha)
        val btnEliminar: Button = element.findViewById(R.id.btnEliminar)

        txtProducto.text = gasto.producto
        txtPrecio.text = "$" + gasto.precio.toString()
        txtTienda.text = gasto.tienda
        txtFecha.text = gasto.fecha

        btnEliminar.setOnClickListener {

            val gastoActual = gastoList[position]

            // Eliminar de la BD
            gastoActual.delete(context)

            // Eliminar de la lista
            gastoList.removeAt(position)

            // Refrescar lista
            notifyDataSetChanged()

            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
        }

        return element
    }
}