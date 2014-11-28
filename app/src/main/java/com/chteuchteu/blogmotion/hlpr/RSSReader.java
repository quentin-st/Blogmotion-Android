package com.chteuchteu.blogmotion.hlpr;


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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSReader {
	private static final String ARTICLES_FOOTER_SEP = "<br />Vous devriez me suivre sur Twitter : <strong><a href=\"http://twitter.com/xhark\">@xhark</a></strong>";
	private static final String ARTICLES_BEFORE =
			"<html><head>" +
				"<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\" />" +
				"<style>body { width: 100%; padding:10px 0 20px 0; margin:0; } img, iframe { max-width:100%; height:auto; } p { text-align:justify; } </style>" +
			"</head><body>";
	private static final String ARTICLES_AFTER = "</body></html>";

	public static void parse(String feedurl, List<Post> posts) {
		posts.clear();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new URL(feedurl).openStream());

			NodeList nodes = doc.getElementsByTagName("item");
			for (int i=0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (!readNode(element, "title").equals("")) {
					String title = readNode(element, "title");
					String permalink = readNode(element, "link");
					String description = readNode(element, "description");
					String publishDate = GMTDateToFrench3(readNode(element, "pubDate"));

					String content = readNode(element, "content:encoded");
					if (content.contains("<![CDATA["))
						content = content.substring("<![CDATA[".length(), content.length() - "]]>".length());

					// Remove articles footer
					if (content.contains(ARTICLES_FOOTER_SEP))
						content = content.split(ARTICLES_FOOTER_SEP)[0];

					content = ARTICLES_BEFORE + content + ARTICLES_AFTER;

					List<String> categories = new ArrayList<String>();

					posts.add(new Post((long) i, title, permalink, publishDate, categories, description, content));
				}
			}
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	private static String readNode(Node _node, String _path) {
		String[] paths = _path.split("\\|");
		Node node = null;

		if (paths.length > 0) {
			node = _node;

			for (String path : paths)
				node = getChildByName(node, path.trim());
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
			ex.printStackTrace();
		}
		return "";
	}
}