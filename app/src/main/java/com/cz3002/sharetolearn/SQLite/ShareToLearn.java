package com.cz3002.sharetolearn.SQLite;

import android.provider.BaseColumns;

public interface ShareToLearn extends BaseColumns {
    public static final String TABLE_NAME = "ShareToLearn";

    // columns
    public static final String COURSES = "Courses";
    public static final String COURSEREVIEWS = "CourseReview";
    public static final String DISCUSSIONS = "Discussion";
    public static final String DISCUSSIONRESPONSES = "DiscussionResponses";
    public static final String PYPS = "Pyps";
    public static final String PYPRESPONSES = "PypResponses";
    public static final String USERS = "Users";
}
