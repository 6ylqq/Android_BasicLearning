package com.example.experiment_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.experiment_1.model.FeedReaderContract;
import com.example.experiment_1.model.FeedReaderDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ylqq
 */
public class SQLiteActivity extends AppCompatActivity {

    private static final String TAG = "SQLiteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
        final SQLiteDatabase database = feedReaderDbHelper.getWritableDatabase();
        setContentView(R.layout.activity_s_q_lite);

        final Button createDb = findViewById(R.id.createDataBase);
        final Button addData = findViewById(R.id.addData);
        final Button updateData = findViewById(R.id.updateData);
        final Button deleteData = findViewById(R.id.deleteData);
        final Button queryData = findViewById(R.id.queryData);


        createDb.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"DataBase has been created!",Toast.LENGTH_LONG).show());

        addData.setOnClickListener(v -> {

            //新建一个Content，然后存入待插入数据
            ContentValues values = new ContentValues();
            String title = "MyOldTitle";
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
            String subtitle = "hello android";
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

            //插入输入，返回新插入行的主键
            long newRowId = database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
            if(newRowId!=0){
                Toast.makeText(getApplicationContext(),"Data has been inserted!",Toast.LENGTH_LONG).show();
            }
        });

        updateData.setOnClickListener(v -> {
            String title = "MyNewTitle";
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);

            //定义好selection和selectionArgs
            String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE?";
            String[] selectionArgs = {"MyOldTitle"};

            int count = database.update(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                        values,
                    selection,
                    selectionArgs
                    );
            if (count!=0){
                Toast.makeText(getApplicationContext(),"Data has been updated!",Toast.LENGTH_LONG).show();
            }

        });

        deleteData.setOnClickListener(v -> {
            //定义query的where条件部分
            String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE?";
            String[] selectionArgs = {"MyNewTitle"};

            //返回删除的行数
            int deletedRows = database.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
            if (deletedRows!=0){
                Toast.makeText(getApplicationContext(),"Data has been deleted!",Toast.LENGTH_LONG).show();
            }
        });

        queryData.setOnClickListener(v -> {
            //定义一个投影，该投影指定数据库中的哪些列
            String[] projection = {
                    BaseColumns._ID,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
            };

            //过滤title=“my title”的查询结果
            String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + "=?";
            String[] selectionArgs = {"MyNewTitle"};

            String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

            Cursor cursor = database.query(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );

            List<Long> itemIds = new ArrayList();
            while (cursor.moveToNext()) {
                //尝试取一下主键ID
                long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                itemIds.add(itemId);
                System.out.println(itemId);
            }

            //释放资源
            cursor.close();
        });
    }

    @Override
    protected void onDestroy() {
        //关闭数据库连接
        //database.close();
        super.onDestroy();
    }
}