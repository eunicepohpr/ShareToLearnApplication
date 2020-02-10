package com.cz3002.sharetolearn.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;

import java.util.ArrayList;

public class ReviewQuestionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> questionList;
    private static LayoutInflater inflater = null;

    public ReviewQuestionAdapter(Context context, ArrayList<String> questionList) {
        this.context = context;
        this.questionList = questionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = questionList.get(position);
        if (convertView == null) convertView = inflater.inflate(R.layout.listitem_question, null);
        TextView courseCodeTV = convertView.findViewById(R.id.question);
        courseCodeTV.setText(questionList.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
