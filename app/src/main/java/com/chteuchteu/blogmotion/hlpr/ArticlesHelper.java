package com.chteuchteu.blogmotion.hlpr;


import com.chteuchteu.blogmotion.obj.Post;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ArticlesHelper {
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
				if (!RSSHelper.readNode(element, "title").equals("")) {
					String title = RSSHelper.readNode(element, "title");
					String permalink = RSSHelper.readNode(element, "link");
					String description = RSSHelper.readNode(element, "description");
					String publishDate = RSSHelper.GMTDateToFrench3(RSSHelper.readNode(element, "pubDate"));

					String content = RSSHelper.readNode(element, "content:encoded");
					if (content.contains("<![CDATA["))
						content = content.substring("<![CDATA[".length(), content.length() - "]]>".length());

					// Remove articles footer
					if (content.contains(ARTICLES_FOOTER_SEP))
						content = content.split(ARTICLES_FOOTER_SEP)[0];

					content = ARTICLES_BEFORE + content + ARTICLES_AFTER;

					List<String> categories = new ArrayList<>();

					posts.add(new Post((long) i, title, permalink, publishDate, categories, description, content));
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}
}
