package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    DatabaseHandler DB;
    //Importar la clase cursor
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //mostrar datos en el ListActivity
        showData();
    }

    private void showData(){
        DB = new DatabaseHandler(ListActivity.this);
        c= DB.getData();

        //evaluar si existen los datos en la tabla
        if(c.moveToFirst()){
            ListView listData = (ListView) findViewById(R.id.listData);

            //Crear un array list
            final ArrayList<String> allData = new ArrayList<String>();

            //crear un array adapter
            final ArrayAdapter<String> aData = new ArrayAdapter<String>(ListActivity.this,
                    android.R.layout.simple_expandable_list_item_1, allData);

            //agregar el adaptador al listview
            listData.setAdapter(aData);

            //mostrar registros
            do{
                allData.add(c.getString(1));
            }while (c.moveToNext());
        }else{
            Toast.makeText(ListActivity.this, "El registro no se pudo eliminar",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void addData(View view){
        Intent add= new Intent(ListActivity.this, MainActivity.class);
        startActivity(add);
    }
}