package mx.itson.sprite.entities

import android.content.ContentValues
import android.content.Context
import android.util.Log
import mx.itson.sprite.persistence.SpriteDB

class Gasto {

    var id = 0
    var producto: String = ""
    var precio: Double = 0.0
    var tienda: String = ""
    var fecha: String = ""

    constructor()

    constructor(id: Int, producto: String, precio: Double, tienda: String, fecha: String) {
        this.id = id
        this.producto = producto
        this.precio = precio
        this.tienda = tienda
        this.fecha = fecha
    }

    fun save(context: Context, producto: String, precio: Double, tienda: String, fecha: String) {
        try {
            val dbSprite = SpriteDB(context, "SpriteDB", null, 1)
            val db = dbSprite.writableDatabase

            val values = ContentValues()
            values.put("producto", producto)
            values.put("precio", precio)
            values.put("tienda", tienda)
            values.put("fecha", fecha)

            db.insert("Gasto", null, values)

        } catch (ex: Exception) {
            Log.e("Error saving", ex.message.toString())
        }
    }


    fun getAll(context: Context): List<Gasto> {
        val gastos: MutableList<Gasto> = ArrayList()

        try {
            val dbSprite = SpriteDB(context, "SpriteDB", null, 1)
            val db = dbSprite.readableDatabase

            val resultSet = db.rawQuery("SELECT id, producto, precio, tienda, fecha FROM Gasto", null)

            while (resultSet.moveToNext()) {
                val gasto = Gasto(
                    resultSet.getInt(0),
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
                )

                gastos.add(gasto)
            }

        } catch (ex: Exception) {
            Log.e("Error getting", ex.message.toString())
        }

        return gastos
    }

    fun delete(context: Context) {

        val dbSprite = SpriteDB(context, "SpriteDB", null, 1)
        val db = dbSprite.writableDatabase

        db.delete(
            "Gasto",
            "id = ?",
            arrayOf(id.toString())
        )

        db.close()
    }


}