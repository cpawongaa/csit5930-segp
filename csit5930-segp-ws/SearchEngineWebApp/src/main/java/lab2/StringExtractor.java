package lab2;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class StringExtractor {
	
	private static String test_url = "https://cse.hkust.edu.hk/~dlee/4321/";
	
	private static StringBean stringBean;
	private static Parser parser;
	private static NodeList list;
	
	private static int wordCount = 0;
	
	public StringExtractor(String m_url) {
		try { 
			stringBean = new StringBean();
			stringBean.setURL(m_url);
			parser = new Parser(m_url);
			list = parser.extractAllNodesThatMatch(new NodeClassFilter(BodyTag.class));
			
		} catch(ParserException e) {
			e.printStackTrace();
		}
	};
	
	public void printExtractedString() {
		
		// loop through the nodes using the BodyTag class
		for(int i=0; i<list.size(); i++) {
			BodyTag tag = (BodyTag)list.elementAt(i);
			String bodyText = tag.getBody().replaceAll("\\<.*?>",""); // using a regular expression <.*?> to remove all HTML tags
			String[] words = bodyText.split("\\s+");
			
			for(String word: words) {
				System.out.println("[" + word + "]");
				wordCount++;
			}
		}
		
		System.out.println("Total Word Count: " + wordCount);
	}

    public static void main(String[] args) {
    	StringExtractor se = new StringExtractor(test_url);
    	se.printExtractedString();

    }

}
