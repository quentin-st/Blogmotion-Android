package com.chteuchteu.blogmotion;

import android.os.Bundle;


/**
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PostDetailFragment}.
 */
public class PostDetailActivity extends BMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

	    this.context = this;

        // Show the Up button in the action bar.
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PostDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(PostDetailFragment.ARG_ITEM_ID));
            PostDetailFragment fragment = new PostDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.post_detail_container, fragment)
                    .commit();
        }

	    this.menuRes = R.menu.postdetail;

	    super.afterOnCreate();
    }
}
