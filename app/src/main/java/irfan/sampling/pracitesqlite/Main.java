package irfan.sampling.pracitesqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import irfan.sampling.pracitesqlite.helpers.BookHelper;

public class Main extends AppCompatActivity {

    Cursor c ;
    SimpleCursorAdapter aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        ListView lv = findViewById(R.id.lv);
        FloatingActionButton fab = findViewById(R.id.fab);

        BookHelper bhelper = new BookHelper(this);
        SQLiteDatabase database = bhelper.getWritableDatabase();

        String[] datas = {"_id","title", "author"};
        c = database.query("books", datas, null, null, null, null, null);


        aa = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, new String[]{"title", "author"},
                new int[]{android.R.id.text1, android.R.id.text2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

//        ArrayList<String> data = new ArrayList<>();
//        c.moveToFirst();
//        while (!c.isAfterLast()){
//            String title = c.getString(c.getColumnIndex("title")) +"\nauthor :"+ c.getString(c.getColumnIndex("author"));
//            data.add(title);
//            c.moveToNext();
//        }
//
//        if(data.isEmpty()){
//            data.add("tidak ada data/ data kosong");
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, data);

        lv.setAdapter(aa);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, Inserting.class));
            }
        });

        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete :{
                deletebooks(info.id);
            }break;
            case R.id.update:{
                updatebooks(info.id);
//                Toast.makeText(this, "Update data", Toast.LENGTH_SHORT).show();
            }break;
            default:
                //
        }
        return true;
    }

    public void deletebooks(long id){
        BookHelper helper = new BookHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("books","_id=?", new String[]{String.valueOf(id)});
        Toast.makeText(this, "data berhasil di delete", Toast.LENGTH_SHORT).show();
        Cursor x = db.query("books", new String[]{"_id","title","author"}, null, null, null, null, null);
        aa.changeCursor(x);
        aa.notifyDataSetChanged();
    }

    public void updatebooks(long id){
        BookHelper helper = new BookHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor e = db.query("books", new String[]{"title", "author"},"_id=?",
                new String[]{String.valueOf(id)}, null, null, null);
        e.moveToFirst();
        Intent ii = new Intent(this, Inserting.class);
        ii.putExtra("_id", id);
        ii.putExtra("title", e.getString(e.getColumnIndex("title")));
        ii.putExtra("author", e.getString(e.getColumnIndex("author")));

        startActivity(ii);
    }
}
