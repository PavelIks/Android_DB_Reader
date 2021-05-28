package com.example.app;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    TextView textView;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try
        {
            mDBHelper.updateDataBase();
        }
        catch (IOException mIOException)
        {
            throw new Error("UnableToUpdateDatabase");
        }
        try
        {
            mDb = mDBHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }

        //Найдем компоненты в XML разметке
        textView = (TextView) findViewById(R.id.TextView_ID1);

        // Вывод данных с таблицы "Table_ID_1"
        String Table_from_DB_01 = "";
        Cursor cursor = mDb.rawQuery("SELECT * FROM Table_ID_1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Table_from_DB_01 += cursor.getString(1) + "\n";
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(Table_from_DB_01);
    }
}