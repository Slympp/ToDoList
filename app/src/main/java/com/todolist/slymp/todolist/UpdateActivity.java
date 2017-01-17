package com.todolist.slymp.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = "UpdateActivity";
    private EditText            _title;
    private EditText            _desc;
    private int                 _id;
    private Button              cancel_button;
    private Button              update_button;
    private Button              delete_button;
    private TaskDbHelper        mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mHelper = new TaskDbHelper(this);

        _title = (EditText) findViewById(R.id.fieldTitle);
        _desc = (EditText) findViewById(R.id.fieldDesc);

        Intent intent = getIntent();
        _title.setText(intent.getStringExtra("title"));
        _desc.setText(intent.getStringExtra("desc"));
        _id = Integer.parseInt(intent.getStringExtra("id"));

        cancel_button = (Button) findViewById(R.id.cancelUpdateButton);
        update_button = (Button) findViewById(R.id.updateButton);
        delete_button = (Button) findViewById(R.id.deleteButton);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, String.valueOf(_title.getText()));
                values.put(TaskContract.TaskEntry.COL_TASK_DESC, String.valueOf(_desc.getText()));

                // TODO: Implement due-time update

                db.update(TaskContract.TaskEntry.TABLE, values, "_id="+ _id, null);
                db.close();
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mHelper.getWritableDatabase();
                db.delete(TaskContract.TaskEntry.TABLE, "_id="+ _id, null);
                db.close();
                finish();
            }
        });
        updateUpdateButtonState();
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
                updateUpdateButtonState();
            }
        };

        _title.addTextChangedListener(tw);
        _desc.addTextChangedListener(tw);
    }

    void updateUpdateButtonState() {
        update_button.setEnabled(!TextUtils.isEmpty(_title.getText()));
    }
}
