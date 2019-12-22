package com.shiluying.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiluying.wordbook.Word.WordContent;
import com.shiluying.wordbook.Word.WordContent.WordItem;
import com.shiluying.wordbook.database.DBHelper;
import com.shiluying.wordbook.database.SQLHelper;
import com.shiluying.wordbook.enity.Record;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LeftFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private static final String ARG_PARAM1 = "word";
    private String mParam1="";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private WordContent.WordItem item;
    private LeftRecyclerViewAdapter mAdapter = null;
    ArrayList<WordItem> wordList=new ArrayList<WordItem>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
//    public LeftFragment(WordContent.WordItem item ) {
//        this.item=item;
//    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LeftFragment newInstance(int columnCount) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }
    public void RefreshData(){
        wordList=new ArrayList<WordItem>();
        SQLiteDatabase db = new DBHelper(getActivity()).getWritableDatabase();
        SQLHelper sqlHelper = new SQLHelper();
        ArrayList<Record> recordList;
        Log.i("TEST",mParam1);
        if(!"".equals(mParam1)){
            recordList=sqlHelper.fuzzyQuery(db,mParam1);
        }else{
            recordList=sqlHelper.queryData(db,"");
        }
        for(int i=0;i<recordList.size();i++){
            WordItem word = new WordItem(recordList.get(i).getWord());
            wordList.add(word);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        savedInstanceState.getBinder()
        RefreshData();
        View view = inflater.inflate(R.layout.fragment_left_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mAdapter=new LeftRecyclerViewAdapter(wordList, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(WordContent.WordItem item);
    }
}
