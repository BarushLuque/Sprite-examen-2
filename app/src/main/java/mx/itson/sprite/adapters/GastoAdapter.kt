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

/**
 *  Esta clase se encarga de adaptar una lista de objetos tipo Gasto
 *  para mostrarlos dentro de un ListView en la aplicación.
 */
class GastoAdapter(
    var context: Context,

    //Lista de gastos
    var gastoList: MutableList<Gasto>
) : BaseAdapter() {

    /**
     * Retorna la cantidad total de elementos en la lista
     */
    override fun getCount(): Int {
        return gastoList.size
    }

    /**
     * Retorna un elemento especifico de la lista segun su posición
     */
    override fun getItem(position: Int): Any {
        return gastoList[position]
    }

    /**
     * Retorna el ID del elemento en base a la posición
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    /**
     * Metodo para los elementos visuales
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //Reutiliza la vista y si no existe la crea
        val element = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.gasto_item, parent, false)

        //Obtiene el objeto gasto de la posición
        val gasto = gastoList[position]

        //Los elementos visuales del layout
        val txtProducto: TextView = element.findViewById(R.id.txtProducto)
        val txtPrecio: TextView = element.findViewById(R.id.txtPrecio)
        val txtTienda: TextView = element.findViewById(R.id.txtTienda)
        val txtFecha: TextView = element.findViewById(R.id.txtFecha)
        val btnEliminar: Button = element.findViewById(R.id.btnEliminar)

        //Asignar valores a los TextView
        txtProducto.text = gasto.producto
        txtPrecio.text = "$" + gasto.precio.toString()
        txtTienda.text = gasto.tienda
        txtFecha.text = gasto.fecha

        /**
         * Evento de boton eliminar
         */
        btnEliminar.setOnClickListener {

            // Obtiene el gasto de la posición
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