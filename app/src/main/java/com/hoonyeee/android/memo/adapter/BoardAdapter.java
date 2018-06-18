package com.hoonyeee.android.memo.adapter;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hoonyeee.android.memo.DBHelper;
import com.hoonyeee.android.memo.ListActivity;
import com.hoonyeee.android.memo.MainActivity;
import com.hoonyeee.android.memo.R;
import com.hoonyeee.android.memo.domain.Board;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.Holder>{
    List<Board> boardList;

    public BoardAdapter(List<Board> boardList){
        this.boardList = boardList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Board board = boardList.get(position);
        holder.setBoard(board);
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        //private TextView textMemo;
        private TextView textAuthor;
        private TextView textDate;
        private Button buttonDelete;
        private TextView textNo;
        private DBHelper dbHelper;
        private SQLiteDatabase db;
        private String deleteQuery;


        public Holder(final View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            //textMemo = itemView.findViewById(R.id.textMemo);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textDate = itemView.findViewById(R.id.textDate);
            textNo = itemView.findViewById(R.id.textNo);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            dbHelper = new DBHelper(itemView.getContext());
            db = dbHelper.getWritableDatabase();

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //해당 no data 지우기.
                    String sno = textNo.getText().toString().trim();
                    int no = Integer.parseInt(sno);

                    deleteQuery = "delete from memo where id = " + no;
                    db.execSQL(deleteQuery);

                    //삭제된 데이터 list에서 제거
                    for(int i=0; i<boardList.size(); i++){
                        if(boardList.get(i).no == no) {
                            boardList.remove(i);
                            break;
                        }
                    }
                    refreshAdapter();
                    Toast.makeText(itemView.getContext(), textNo.getText().toString()+". deleted",Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Holder에서 접근안되서 함수로 만듬
        public void refreshAdapter(){
            notifyDataSetChanged();
        }
        public void setBoard(Board board){
            textTitle.setText(board.title);
            textAuthor.setText(board.author);
            //textMemo.setText(board.memo);
            textDate.setText(board.date+"");
            textNo.setText(board.no+"");
        }
    }
}
