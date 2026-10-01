package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        "colegio.db",
        null,
        1
    )
    {
        override fun onCreate(db: SQLiteDatabase) {
            val query = """
                CREATE TABLE alumnos(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                edad INTEGER
                )
            """.trimIndent()

            db.execSQL(query)
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            db.execSQL("DROP TABLE IF EXISTS alumnos")
            onCreate(db)
        }

        fun insertarAlumno(nombre: String, edad: Int) {
            val db = writableDatabase
            val datos = ContentValues()

            datos.put("nombre", nombre)
            datos.put("edad", edad)

            db.insert(
                "alumnos",
                null,
                datos
            )
            db.close()
        }

        fun obtenerAlumnos(): List<Alumno>{
            val lista = mutableListOf<Alumno>()

            val db = readableDatabase

            val cursor = db.rawQuery(
                "SELECT * FROM alumnos",
                null
            )

            while(cursor.moveToNext()) {

                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val edad = cursor.getInt(2)

                lista.add(
                    Alumno(
                        id,
                        nombre,
                        edad
                    )
                )
            }

            cursor.close()
            db.close()

            return lista

        }
    }