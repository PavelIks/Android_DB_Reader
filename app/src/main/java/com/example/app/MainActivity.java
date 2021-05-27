package com.example.app;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    Button button;
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
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        //Пропишем обработчик клика кнопки
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String product = "";
                Cursor cursor = mDb.rawQuery("SELECT * FROM Table_ID_1", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    product += cursor.getString(1) + " | ";
                    cursor.moveToNext();
                }
                cursor.close();
                textView.setText(product);
            }
        });
    }
}