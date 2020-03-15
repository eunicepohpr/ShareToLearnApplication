package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainUserViewModel extends ViewModel {
    private MutableLiveData<User> mMainUser;

    public MainUserViewModel(){
        User mainUser = new User("user1", "My biography", "lam022@e.ntu.edu.sg", "Computer Science", "2021", "lam", "student");
        mMainUser = new MutableLiveData<>();
        mMainUser.setValue(mainUser);
    }

    public LiveData<User> getMainUser() { return mMainUser; }

    public void setMainUser(User user){
        mMainUser = new MutableLiveData<>();
        mMainUser.setValue(user);
    }
}
