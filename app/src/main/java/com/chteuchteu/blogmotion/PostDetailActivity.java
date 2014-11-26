package com.chteuchteu.blogmotion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;


/**
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PostDetailFragment}.
 */
public class PostDetailActivity extends BMActivity {
	private long postId;

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
	        this.postId = getIntent().getLongExtra(PostDetailFragment.ARG_ITEM_ID, -1);

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(PostDetailFragment.ARG_ITEM_ID, postId);
            PostDetailFragment fragment = new PostDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.post_detail_container, fragment)
                    .commit();
        }

	    this.menuRes = R.menu.postdetail;

	    super.afterOnCreate();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_open:
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BM.getInstance(this.context).getPost(postId).getPermalink())));

				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
