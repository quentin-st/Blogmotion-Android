package com.chteuchteu.blogmotion.hlpr;


import android.util.Log;

import com.chteuchteu.blogmotion.obj.Post;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSReader {
	public static void parse(String feedurl, List<Post> posts) {
		posts.clear();

		try {
			//if (thread != null)
			//	thread.manualPublishProgress(10);

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			URL url = new URL(feedurl);
			Document doc = builder.parse(url.openStream());
			Element element;

			//if (thread != null)
			//	thread.manualPublishProgress(50);

			//nodes = doc.getElementsByTagName("title");

			NodeList nodes = doc.getElementsByTagName("item");
			for (int i = 0; i < nodes.getLength(); i++) {
				element = (Element) nodes.item(i);
				if (!readNode(element, "title").equals("")) {
					String title = readNode(element, "title");
					String permalink = readNode(element, "link");
					String description = readNode(element, "description");
					String publishDate = GMTDateToFrench3(readNode(element, "pubDate"));

					String content = readNode(element, "content:encoded");
					if (content.contains("<![CDATA["))
						content = content.substring("<![CDATA[".length(), content.length() - "]]>".length());

					List<String> categories = new ArrayList<String>();

					posts.add(new Post((long) i, title, permalink, categories, description, content));

					int percentage = i * 100 / nodes.getLength() / 2 + 50;
					//if (thread != null)
					//	thread.manualPublishProgress(percentage);
				}
			}
			//if (thread != null)
			//	thread.manualPublishProgress(100);
		} catch (SAXException ex) {
			Log.e("", ex.toString());
		} catch (IOException ex) {
			Log.e("", ex.toString());
		} catch (ParserConfigurationException ex) {
			Log.e("", ex.toString());
		}
	}

	private static String readNode(Node _node, String _path) {
		String[] paths = _path.split("\\|");
		Node node = null;

		if (paths.length > 0) {
			node = _node;

			for (int i=0; i<paths.length; i++)
				node = getChildByName(node, paths[i].trim());
		}

		if (node != null)
			return node.getTextContent();
		else
			return "";
	}

	private static Node getChildByName(Node _node, String _name) {
		if (_node == null) {
			return null;
		}
		NodeList listChild = _node.getChildNodes();

		if (listChild != null) {
			for (int i = 0; i < listChild.getLength(); i++) {
				Node child = listChild.item(i);
				if (child != null) {
					if ((child.getNodeName() != null && (_name.equals(child.getNodeName()))) || (child.getLocalName() != null && (_name.equals(child.getLocalName())))) {
						return child;
					}
				}
			}
		}
		return null;
	}

	private static String GMTDateToFrench3(String gmtDate) {
		try {
			SimpleDateFormat dfGMT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
			dfGMT.parse(gmtDate);
			SimpleDateFormat dfFrench = new SimpleDateFormat("d/MM", Locale.FRANCE);
			return dfFrench.format(dfGMT.getCalendar().getTime());
		} catch (ParseException ex) {
			Logger.getLogger(RSSReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
}