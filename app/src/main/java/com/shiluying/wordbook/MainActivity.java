package com.shiluying.wordbook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shiluying.wordbook.Word.WordContent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shiluying.wordbook.database.*;
import com.shiluying.wordbook.enity.Record;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LeftFragment.OnListFragmentInteractionListener,RightFragment.OnFragmentInteractionListener {
    private AlertDialog.Builder builder;
    SQLiteDatabase db;
    SQLHelper sqlHelper;
    private String fuzzyword="";
    private LeftFragment leftfragment;
    private RightFragment rightfragment;
    private FragmentTransaction transaction;
    FragmentManager fragmentManager;
    public static void showActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db=new DBHelper(this).getWritableDatabase();
        sqlHelper  = new SQLHelper();

        fragmentManager = getSupportFragmentManager();//fragment管理器
        leftfragment=new LeftFragment();
        rightfragment = new RightFragment();
        Configuration configuration = getResources().getConfiguration();
        setLayout();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInput();
            }
        });

        Button search = findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edittext = (EditText) findViewById(R.id.search_edit_text);
                fuzzyword = edittext.getText().toString();
                setLayout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
    }


    @Override
    public void onListFragmentInteraction(WordContent.WordItem item) {
        transaction = fragmentManager.beginTransaction();//开启一个事务
        transaction.hide(rightfragment);
        transaction.commit();
        Bundle arguments = new Bundle();
        arguments.putString("word", item.word);
        rightfragment = new RightFragment();
        rightfragment.setArguments(arguments);
        transaction = fragmentManager.beginTransaction();//开启一个事务
        transaction.replace(R.id.content_main, rightfragment);//添加一个fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getApplicationContext(), "横屏x", Toast.LENGTH_SHORT).show();
            MainLandActivity.showActivity(this);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "竖屏x", Toast.LENGTH_SHORT).show();
        }
    }

    public void setLayout(){
        Bundle arguments = new Bundle();
        Log.i("TEST",fuzzyword);
        arguments.putString("word",fuzzyword);
        fuzzyword="";
        leftfragment = new LeftFragment();
        leftfragment.setArguments(arguments);
        transaction = fragmentManager.beginTransaction();//开启一个事务
        transaction.replace(R.id.content_main, leftfragment);//添加fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void showInput() {
        final View layout = View.inflate(this, R.layout.word_add,
                null);
        builder = new AlertDialog.Builder(this)
                .setTitle("添加单词")
                .setView(layout)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edittext;
                        edittext = (EditText) layout.findViewById(R.id.addword);
                        String word = edittext.getText().toString();
                        edittext = (EditText) layout.findViewById(R.id.addmeaning);
                        String meaning = edittext.getText().toString();
                        edittext = (EditText) layout.findViewById(R.id.addsample);
                        String sample = edittext.getText().toString();
                        sqlHelper.insertData(db,word,meaning,sample);
                        Configuration configuration = getResources().getConfiguration();
                        setLayout();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onRightFragmentInteraction() {
        Configuration configuration = getResources().getConfiguration();
        setLayout();
    }

    @Override
    public void OnRightFragmentInteractionListener(Uri uri) {

    }
}