package com.example.crudsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)menuInfo;
        c.moveToPosition(info.position);
        menu.setHeaderTitle(c.getString(1)+ " "+ c.getString(2));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.edit:
                try{
                    String person[]={c.getString(1), c.getString(2)};
                    Bundle bundle= new Bundle();
                    bundle.putString("action", "edit");
                    bundle.putString("id", c.getString(0));
                    bundle.putStringArray("person", person);

                    Intent formMain = new Intent(ListActivity.this, MainActivity.class);
                    formMain.putExtras(bundle);
                    startActivity(formMain);

                }catch (Exception e){
                    Toast.makeText(ListActivity.this, "Error: " + e.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
                allData.add(c.getString(1) + " " + c.getString(2));
            }while (c.moveToNext());
            aData.notifyDataSetChanged();

            //hacer referencia al listview para mostrar el menu
            registerForContextMenu(listData);
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