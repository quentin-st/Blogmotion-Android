package com.chteuchteu.blogmotion.hlpr;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RSSHelper {
	public static String readNode(Node _node, String _path) {
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

	public static Node getChildByName(Node _node, String _name) {
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

	public static String GMTDateToFrench3(String gmtDate) {
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
