package com.cz3002.sharetolearn.ui.courseconfig;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.CourseAdapter;
import com.cz3002.sharetolearn.MainFeed;
import com.cz3002.sharetolearn.R;

import java.util.ArrayList;

public class CourseConfigFragment extends Fragment {

    private CourseConfigViewModel courseConfigViewModel;
    private ListView courseList;
    private ImageView courseSelected;
    private CourseAdapter courseAdapter;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        courseConfigViewModel = ViewModelProviders.of(this).get(CourseConfigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_courseconfig, container, false);
        ((MainFeed) getActivity()).hideFloatingActionButton();

        courseList = (ListView) root.findViewById(android.R.id.list);
        courseSelected = (ImageView) root.findViewById(R.id.listitem_courseselected);

        courseConfigViewModel.getCourseList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> courseArrayList) {
                courseAdapter = new CourseAdapter(getActivity(), courseArrayList);
                courseList.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), msg,Toast.LENGTH_LONG).show();
/*                if(courseSelected.getVisibility() == View.INVISIBLE){
                    msg = adapterView.getItemAtPosition(position).toString();
                    Toast.makeText(getActivity(), msg,Toast.LENGTH_LONG).show();
                }else{
                    msg = "NOT INVISIBLE";
                    Toast.makeText(getActivity(), msg,Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }
}