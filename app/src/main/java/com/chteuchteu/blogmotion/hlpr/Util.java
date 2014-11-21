package com.chteuchteu.blogmotion.hlpr;

public class Util {
	public static interface ProgressListener {
		public void onPreExecute();
		public void onProgress(int progress, int total);
		public void onPostExecute();
	}
}
