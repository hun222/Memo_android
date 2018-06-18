package com.hoonyeee.android.memo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hoonyeee.android.memo.adapter.BoardAdapter;
import com.hoonyeee.android.memo.domain.Board;
import com.hoonyeee.android.memo.util.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnPost;
    DBHelper dbHelper;
    SQLiteDatabase db;
    List<Board> boardLIst;
    BoardAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        boardLIst = new ArrayList<>();

        //list에 목록을 뿌려준다.
        String selectQuery = "select * from memo";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                Board board = new Board();
                String tmp = "";
                int index = cursor.getColumnIndex("id"); //column번호 가져오기
                int id = cursor.getInt(index);

                index = cursor.getColumnIndex("title");
                String title = cursor.getString(index);

                index = cursor.getColumnIndex("memo");
                String memo = cursor.getString(index);

                index = cursor.getColumnIndex("author");
                String author = cursor.getString(index);

                index = cursor.getColumnIndex("date");
                long date = cursor.getLong(index);

                tmp = id+" : "+title+" : "+date;
                //list에 tmp담기
                board.title = title;
                board.no = id;
                board.author = author;
                board.date = date;
                board.memo = memo;
                boardLIst.add(board);
            }
        }
        //recyclerview 연결
        boardAdapter = new BoardAdapter(boardLIst);
        recyclerView.setAdapter(boardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //boardAdapter.notifyDataSetChanged();

        //구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        //글쓰기
        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
