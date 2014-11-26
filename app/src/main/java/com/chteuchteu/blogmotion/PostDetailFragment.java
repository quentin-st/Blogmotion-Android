package com.chteuchteu.blogmotion;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.chteuchteu.blogmotion.obj.Post;

public class PostDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private Post mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (getArguments().containsKey(ARG_ITEM_ID))
			mItem = BM.getInstance(activity).getPost(getArguments().getLong(ARG_ITEM_ID));
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);

        if (mItem != null) {
	        WebView webView = (WebView) rootView.findViewById(R.id.post_detail);
	        webView.setVerticalScrollBarEnabled(true);
	        webView.getSettings().setDefaultTextEncodingName("utf-8");
	        webView.setBackgroundColor(0x00000000);
	        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

	        webView.loadDataWithBaseURL(null, mItem.getContent(), "text/html", "utf-8", null);
        }

        return rootView;
    }
}
