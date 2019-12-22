package com.shiluying.wordbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shiluying.wordbook.Word.WordContent;
import com.shiluying.wordbook.database.DBHelper;
import com.shiluying.wordbook.database.SQLHelper;
import com.shiluying.wordbook.enity.Record;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "word";
    private AlertDialog.Builder builder;
    private String mParam1;
    private SQLiteDatabase db;
    private SQLHelper sqlHelper;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Record> recordList = new ArrayList<Record>();
    View view;
    public RightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @return A new instance of fragment RightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RightFragment newInstance(String param1) {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i("TEST","onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        db = new DBHelper(getActivity()).getWritableDatabase();
        sqlHelper = new SQLHelper();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("TEST","onCreateView");
        view= inflater.inflate(R.layout.fragment_right, container, false);
        refreshData();
        return view;
    }
    public void refreshData(){
        recordList=sqlHelper.queryData(db,mParam1);
        TextView textViewWord=(TextView)view.findViewById(R.id.word);
        TextView textViewMeaning=(TextView)view.findViewById(R.id.wordmeaning);
        TextView textViewSample=(TextView)view.findViewById(R.id.wordsample);
        for(int i=0;i<recordList.size();i++){
            textViewWord.setText(recordList.get(i).getWord());
            textViewMeaning.setText(recordList.get(i).getWordMeaning());
            textViewSample.setText(recordList.get(i).getWordSample());
    }
}
    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {

//        Log.i("TEST","onButtonPressed");
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        Log.i("TEST","onAttach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }
    @Override
    public void onResume() {
        Log.i("TEST","onResume");
        super.onResume();
    }
    @Override
    public void onDetach() {
        Log.i("TEST","onDetach");
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("TEST","onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Button delbutton = (Button)getActivity().findViewById(R.id.delbutton);
        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlHelper.deleteData(db,mParam1);
                getFragmentManager().popBackStack();
            }
        });
        Button changebutton = (Button)getActivity().findViewById(R.id.changebutton);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWord();
            }
        });
    }
    public void changeWord(){
        final View layout = View.inflate(getActivity(), R.layout.word_add,
                null);
        ArrayList<Record> changeList=sqlHelper.queryData(db,mParam1);
        if(changeList.size()==1){
            Record record=changeList.get(0);
            EditText edittext;
            edittext = (EditText) layout.findViewById(R.id.addword);
            edittext.setText(record.getWord());
            edittext = (EditText) layout.findViewById(R.id.addmeaning);
            edittext.setText(record.getWordMeaning());
            String meaning = edittext.getText().toString();
            edittext = (EditText) layout.findViewById(R.id.addsample);
            edittext.setText(record.getWordSample());
        }
        builder = new AlertDialog.Builder(getActivity())
                .setTitle("修改单词")
                .setView(layout)
                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edittext;
                        edittext = (EditText) layout.findViewById(R.id.addword);
                        String word = edittext.getText().toString();
                        mParam1=word;
                        edittext = (EditText) layout.findViewById(R.id.addmeaning);
                        String meaning = edittext.getText().toString();
                        edittext = (EditText) layout.findViewById(R.id.addsample);
                        String sample = edittext.getText().toString();
                        sqlHelper.updateData(db,word,meaning,sample);
                        refreshData();
                        Configuration configuration = getResources().getConfiguration();
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
                            mListener.onRightFragmentInteraction();
                        }else{
                            //                            setLayout();
                        }
                    }
                });
        builder.create().show();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRightFragmentInteraction();

        void OnRightFragmentInteractionListener(Uri uri);
    }
}







