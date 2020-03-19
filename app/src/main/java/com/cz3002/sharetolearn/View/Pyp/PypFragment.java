package com.cz3002.sharetolearn.View.Pyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.PYPAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PypFragment extends Fragment {

    private String mainUserKey;
    private PYPViewModel pypViewModel;
    private Course course;
    private ListView pypsListView;
    private PYPAdapter PYPAdapter;
    private FloatingActionButton fab;

    public PypFragment(){}

    public PypFragment(Course course){
        this.course = course;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pypViewModel = ViewModelProviders.of(this).get(PYPViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_pyp, container, false);
        pypsListView = root.findViewById(R.id.pyp_thread_list);
        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fab = root.findViewById(R.id.fab);

        pypViewModel.getUser(mainUserKey).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getDomain().equals("Staff")){
                    fab.show();
                }else{
                    fab.hide();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new Intent(getContext(), AddPYP.class);
                launchactivity.putExtra("course", course);
                launchactivity.putExtra("mainUserKey", mainUserKey);
                startActivity(launchactivity);
/*                Snackbar.make(view, "Create post", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        pypViewModel.getpyps().observe(this, new Observer<List<PYP>>() {
            @Override
            public void onChanged(@Nullable List<PYP> pyps) {
                List<PYP> PYPList = new ArrayList<>();
                for (PYP pyp: pyps){
                    if (pyp.getCourseKey().equals(course.getKey())) {
                        PYPList.add(pyp);
                    }
                }
                Collections.sort(PYPList, new Comparator<PYP>() {
                    @Override
                    public int compare(PYP t0, PYP t1) {
                        return t0.getPostedDateTime().compareTo(t1.getPostedDateTime());
                    }
                });
                PYPAdapter = new PYPAdapter(getActivity(), getActivity(),PYPList, mainUserKey);
                pypsListView.setAdapter(PYPAdapter);
                PYPAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pypsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PYP pyp = (PYP) adapterView.getItemAtPosition(position);
                Intent launchactivity = new  Intent(getContext(), PypActivity.class);
                launchactivity.putExtra("key", mainUserKey);
                PypActivity.pyp = pyp;
                startActivity(launchactivity);
                /*Toast toast = Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT);
                toast.show();*/
                //startActivity(new Intent(getActivity(), PYPPypChatActivity.class));
            }
        });
    }
    
}