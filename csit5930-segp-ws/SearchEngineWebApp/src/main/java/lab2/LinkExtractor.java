package lab2;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class LinkExtractor {
	
private static String test_url = "https://cse.hkust.edu.hk/~dlee/4321/";
	
	private static StringBean stringBean;
	private static Parser parser;
	private static NodeList list;
	
	private static int linkCount = 0;
	
	public LinkExtractor(String m_url) {
		try { 
			stringBean = new StringBean();
			stringBean.setURL(m_url);
			parser = new Parser(m_url);
			list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			
		} catch(ParserException e) {
			e.printStackTrace();
		}
	};
	
	public void printExtractedLinks() {
		
		// loop through the nodes using the BodyTag class
		for(int i=0; i<list.size(); i++) {
			LinkTag link = (LinkTag)list.elementAt(i);
			System.out.println("[" + link.getLink() + "]");
			linkCount++;
		}
		
		System.out.println("Total Link Count: " + linkCount);
	}

    public static void main(String[] args) {
    	LinkExtractor se = new LinkExtractor(test_url);
    	se.printExtractedLinks();

    }

}
