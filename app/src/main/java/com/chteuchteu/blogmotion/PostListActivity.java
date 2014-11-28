package com.chteuchteu.blogmotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chteuchteu.blogmotion.hlpr.Util;

public class PostListActivity extends BMActivity implements PostListFragment.Callbacks {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

	    // Init BM
	    BM.getInstance(this);

        if (findViewById(R.id.post_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((PostListFragment) getFragmentManager()
                    .findFragmentById(R.id.post_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.

	    this.menuRes = R.menu.postlist;

	    super.afterOnCreate();

	    fetchArticles(false);
    }

	public void fetchArticles(boolean forceLoad) {
		// Load articles on first launch
		if (BM.getInstance(context).getPosts().isEmpty() || forceLoad)
			BM.getInstance(context).loadArticles(new Util.ProgressListener() {
				@Override
				public void onPreExecute() {
				}

				@Override
				public void onProgress(int progress, int total) {
				}

				@Override
				public void onPostExecute() {
					((PostListFragment) getFragmentManager().findFragmentById(R.id.post_list)).refreshList();
				}
			}, forceLoad);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				fetchArticles(true);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

    /**
     * Callback method from {@link PostListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(PostDetailFragment.ARG_ITEM_ID, id);
            PostDetailFragment fragment = new PostDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.post_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PostDetailActivity.class);
            detailIntent.putExtra(PostDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
