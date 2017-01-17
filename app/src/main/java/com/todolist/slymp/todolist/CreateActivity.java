package com.todolist.slymp.todolist;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.todolist.slymp.todolist.db.TaskContract;
import com.todolist.slymp.todolist.db.TaskDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";
    private EditText            _title;
    private EditText            _desc;
    private Button              cancel_button;
    private Button              create_button;
    private TaskDbHelper        mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mHelper = new TaskDbHelper(this);

        _title = (EditText) findViewById(R.id.fieldTitle);
        _desc = (EditText) findViewById(R.id.fieldDesc);

        cancel_button = (Button) findViewById(R.id.cancelCreateButton);
        create_button = (Button) findViewById(R.id.createButton);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                String textTitle = String.valueOf(_title.getText());
                String textDesc = String.valueOf(_desc.getText());

                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, textTitle);
                values.put(TaskContract.TaskEntry.COL_TASK_DESC, textDesc);
                values.put(TaskContract.TaskEntry.COL_TASK_STATUS, "progress");

                // TODO: remplacer getTime par input des DatePicker/TimePicker
                values.put(TaskContract.TaskEntry.COL_TASK_DUE_TIME, new Date().getTime());

                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
                finish();
            }
        });

        updateCreateButtonState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                updateCreateButtonState();
            }
        };

        _title.addTextChangedListener(tw);
        _desc.addTextChangedListener(tw);
    }

    void updateCreateButtonState() {
        create_button.setEnabled(!TextUtils.isEmpty(_title.getText()));
    }
}
