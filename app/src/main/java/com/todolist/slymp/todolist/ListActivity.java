package com.todolist.slymp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.todolist.slymp.todolist.db.TaskContract;
import com.todolist.slymp.todolist.db.TaskDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private static final String     TAG = "ListActivity";
    private TaskDbHelper            mHelper;
    private ListView                mTaskListView;
    private MyAdapter               mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(R.id.task_list);

        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TaskContract.TaskEntry.TABLE
                                    + " ORDER BY status DESC, title COLLATE NOCASE", null);

        while(cursor.moveToNext()) {
            Item _item = new Item(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)),
                            cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DESC)),
                            null,
                            cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_STATUS)));

            // Format and fill date from Unix timeStamp
            long timeStamp = Long.parseLong(cursor.getString(cursor.getColumnIndex(
                                            TaskContract.TaskEntry.COL_TASK_DUE_TIME)));

            // TODO : If due_time < 24h, afficher format hh:mm, sinon dd/MM/yyyy
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date cleanDate = (new Date(timeStamp));
            _item.setDue_time(sdf.format(cleanDate));

            items.add(_item);
        }

        cursor.close();
        db.close();
        return items;
    }

    protected void updateList() {

        mAdapter = new MyAdapter(this, generateData());
        mTaskListView.setAdapter(mAdapter);
    }

    // TODO : Implementer filtering bar (http://envyandroid.com/align-tabhost-at-bottom/)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(ListActivity.this, CreateActivity.class);
                startActivity(intent);
                return true;

                // TODO : Implement swap order (alphabetical <-> due-time)
                /*
                case R.id.action_swap_order:
                Log.d(TAG, "Open order by menu");
                return true;
                */

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


