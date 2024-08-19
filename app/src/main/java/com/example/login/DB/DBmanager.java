package com.example.login.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBmanager {
    //TABLA USER
    protected static final String TABLA_USER = "user";
    private static final String USER_ID = "id";
    private static final String USER_NOMBRE = "nombre";
    private static final String USER_CORREO = "correo";
    private static final String USER_USUARIO = "usuario";
    private static final String USER_CLAVE = "clave";
    protected static final String TABLA_USER_CREATE = "CREATE TABLE user (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "correo TEXT NOT NULL UNIQUE, " +
            "usuario TEXT NOT NULL UNIQUE, " +
            "clave TEXT NOT NULL)";

    //TABLA CATEGORIA
    protected static final String TABLA_CATEGORIA = "categoria";
    private static final String CATEGORIA_NOMBRE = "nombre";
    private static final String CATEGORIA_USER_ID = "user_id";
    private static final String CATEGORIA_DESCRIPCION = "descripcion";
    protected static final String TABLA_CATEGORIA_CREATE = "CREATE TABLE " + TABLA_CATEGORIA + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "nombre TEXT NOT NULL UNIQUE, " +
            "descripcion TEXT NULL, " +
            "user_id INTEGER, " +
            "FOREIGN KEY(user_id) REFERENCES " + TABLA_USER + "(id))";

    //TABLA REGISTRO
    protected static final String TABLA_REGISTRO = "registro";
    private static final String REGISTRO_CATEGORIA_ID = "categoria_id";
    private static final String REGISTRO_CANT_MATERIAL = "cant_material";
    private static final String REGISTRO_VALOR_GANADO = "valor_ganado";
    private static final String REGISTRO_FECHA = "fecha";
    private static final String REGISTRO_USER_ID = "user_id";
    protected static final String TABLA_REGISTRO_CREATE = "CREATE TABLE " + TABLA_REGISTRO + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "categoria_id INTEGER, " +
            "cant_material INTEGER, " +
            "valor_ganado INTEGER, " +
            "fecha DATETIME, " +
            "user_id INTEGER, " +
            "FOREIGN KEY(categoria_id) REFERENCES " + TABLA_CATEGORIA + "(id)," +
            "FOREIGN KEY(user_id) REFERENCES " + TABLA_USER + "(id))";

    //TABLA CONSEJOS
    protected static final String TABLA_CONSEJOS = "consejos";
    private static final String CONSEJOS_MENSAJE = "mensaje";
    private static final String CONSEJOS_USER_ID = "user_id";
    private static final String CONSEJOS_ESTADO = "estado";
    protected static final String TABLA_CONSEJOS_CREATE = "CREATE TABLE " + TABLA_CONSEJOS + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "mensaje TEXT NOT NULL UNIQUE, " +
            "estado BOOLEAN NOT NULL DEFAULT FALSE, " +
            "user_id INTEGER, " +
            "FOREIGN KEY(user_id) REFERENCES " + TABLA_USER + "(id))";
    private DBconexion _conexion;
    private SQLiteDatabase _basededatos;

    public DBmanager(Context context) {
        _conexion = new DBconexion(context);
    }

    public DBmanager open() throws SQLException {
        _basededatos = _conexion.getWritableDatabase();
        return this;
    }

    public void close() {
        _conexion.close();
    }

    public void insertarUser(String nombre, String correo, String usuario, String clave) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NOMBRE, nombre);
        contentValues.put(USER_CORREO, correo);
        contentValues.put(USER_USUARIO, usuario);
        contentValues.put(USER_CLAVE, clave);

        long id = this._basededatos.insert(TABLA_USER, null, contentValues);
        if (id == -1) {
            Log.e("insercion", "Error al insertar usuario");
        } else {
            Log.d("insercion", "Usuario creado con ID: " + id);
        }
    }

    public Cursor validarCredencialesUser(String usuario, String clave) {
        String[] columnas = {USER_ID, USER_NOMBRE, USER_CORREO, USER_USUARIO, USER_CLAVE};
        String seleccion = USER_USUARIO + " = ? AND " + USER_CLAVE + " = ?";
        String[] seleccionArgs = {usuario, clave};
        Log.d("insercion", "validacion");
        return _basededatos.query(TABLA_USER, columnas, seleccion, seleccionArgs, null, null, null);
    }

    public void insertarCategoria(String nombre, int user_id, String descripcion) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIA_NOMBRE, nombre);
        contentValues.put(CATEGORIA_USER_ID, user_id);
        contentValues.put(CATEGORIA_DESCRIPCION, descripcion);

        long id = this._basededatos.insert(TABLA_CATEGORIA, null, contentValues);
        if (id == -1) {
            Log.e("insercion", "Error al insertar categoría");
        } else {
            Log.d("insercion", "Categoría creada con ID: " + id);
        }
    }

    public Cursor obtenerCategorias() {
        String table = TABLA_CATEGORIA;
        String[] columns = {"id", "nombre"};
        return _basededatos.query(
                table,    // Tabla
                columns,  // Columnas a devolver
                null,     // Clausula WHERE
                null,     // Argumentos para la clausula WHERE
                null,     // GROUP BY
                null,     // HAVING
                null      // ORDER BY
        );
    }

    public void insertarRegistros(int categoria_id, int cant_material, int valor_ganado, String fecha, int user_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(REGISTRO_CATEGORIA_ID, categoria_id);
        contentValues.put(REGISTRO_CANT_MATERIAL, cant_material);
        contentValues.put(REGISTRO_VALOR_GANADO, valor_ganado);
        contentValues.put(REGISTRO_FECHA, fecha);
        contentValues.put(REGISTRO_USER_ID, user_id);

        long id = this._basededatos.insert(TABLA_REGISTRO, null, contentValues);
        if (id == -1) {
            Log.e("insercion", "Error al insertar registro");
        } else {
            Log.d("insercion", "Registro creado con ID: " + id);
        }
    }

    public Cursor obtenerRegistroConMayorValorGanado() {
        String query = "select categoria.nombre as nombre, valor_ganado from registro inner join categoria on categoria.id = registro.categoria_id order by  valor_ganado desc limit 1";
        return _basededatos.rawQuery(query, null);
    }


}
