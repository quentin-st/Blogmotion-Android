package com.chteuchteu.blogmotion.hlpr;

import com.chteuchteu.blogmotion.obj.MusicPost;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MusicMotionHelper {
	private static final String MUSICMOTION_FEED = "http://music.blogmotion.fr/rss";

	public static void parseFeed(List<MusicPost> posts) {
		posts.clear();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new URL(MUSICMOTION_FEED).openStream());

			NodeList nodes = doc.getElementsByTagName("item");
			for (int i=0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (!RSSHelper.readNode(element, "title").equals("")) {
					String title = RSSHelper.readNode(element, "title");
					String permalink = RSSHelper.readNode(element, "link");
					String description = RSSHelper.readNode(element, "description");

					String publishDate = RSSHelper.GMTDateToFrench3(RSSHelper.readNode(element, "pubDate"));

					posts.add(new MusicPost((long) i, title, description, permalink, publishDate));
				}
			}
		} catch (SAXException | ParserConfigurationException | IOException ex) {
			ex.printStackTrace();
		}
	}
}
