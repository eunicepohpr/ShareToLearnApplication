package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
        String question = (position+1)+". "+questionList.get(position);
        if (convertView == null) convertView = inflater.inflate(R.layout.listitem_question, null);
        TextView questionTextView = convertView.findViewById(R.id.question);
        EditText answerEditText = convertView.findViewById(R.id.answer);
        questionTextView.setText(question);
        answerEditText.setText("Answer");
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
