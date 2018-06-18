package com.hoonyeee.android.memo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText title, author, memo;
    Button post;
    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터베이스 초기화
        dbHelper = new DBHelper(this);
        //쓰기가능 상태로 연결한다
        db = dbHelper.getWritableDatabase();

        title = findViewById(R.id.editTitle);
        author = findViewById(R.id.editAuthor);
        memo = findViewById(R.id.editMemo);
        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mtitle = title.getText().toString();
                String mauthor = author.getText().toString();
                String mmemo = memo.getText().toString();
                long date = System.currentTimeMillis();

                String insertQuery = "insert into memo(title, memo,author,date)" +
                        " values('" +mtitle+"','"+mmemo+"','"+mauthor+"',"+date+")";
                db.execSQL(insertQuery);

                Toast.makeText(getBaseContext(), "memo has been inserted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), ListActivity.class);
                startActivity(intent);
                //쓰기 액티비티 종료
                finish();
            }
        });
    }
}
