import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import core.Indexer;

@SuppressWarnings("unused")
public class Test {
    
    public static boolean isDateTwoAfterDateOne(String m_date_1, String m_date_2) {
        boolean isAfter = true;
        try {
            String date_format = "yyyy-mm-dd hh:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(date_format);
            Date d1 = format.parse(m_date_1);
            Date d2 = format.parse(m_date_2);
            if(d1.compareTo(d2)>0) {
                isAfter = false;
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return isAfter;
    }

	public static void main(String[] args) {
	    
//	    try {
//    	    String date_format = "yyyy-mm-dd hh:mm:ss";
//            SimpleDateFormat format = new SimpleDateFormat(date_format);
//            Date d1 = format.parse("2023-04-19 08:30:20");
//            System.out.println(d1);
//            String sd = format.format(d1);
////            d1 = format.parse(date_format);
//            System.out.println(sd);
//	    } catch(ParseException e) {
//            e.printStackTrace();
//        }
//	    
	    
	    
//	    try {
//	    
//    	    URL url = new URL("https://www.cse.ust.hk/~kwtleung/COMP4321/testpage.htm");
//            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//            int content_length = httpCon.getContentLength();
//            System.out.println("content_length: " + content_length);
//	    } catch(IOException e) {
//	        e.printStackTrace();
//	    }
	    
	    
//	    String str_d_1 = "2023-04-19 08:30:20";
//	    String str_d_2 = "2023-04-20 12:12:12";
//	    
//	    System.out.println("d2 > d1: " + isDateTwoAfterDateOne(str_d_1, str_d_2));
	    
//	    try {
//    	    
//    	    String str_d_1 = "2023-04-19 08:30:20";
//    	    String str_d_2 = "2023-04-20 12:12:12";
    	    
//    	    SimpleDateFormat format = new SimpleDateFormat(date_format);
//    	    Date d1 = format.parse(str_d_1);
//    	    Date d2 = format.parse(str_d_2);
//    	    
//    	    System.out.println("d1: " + d1);
//    	    System.out.println("d2: " + d2);
//    	    
//    	    if(d1.compareTo(d2)>0) {
//    	        System.out.println("d1 after d2");
//    	    } else if(d1.compareTo(d2)<0) {
//    	        System.out.println("d1 before d2");
//    	    } else if(d1.compareTo(d2)==0) {
//    	        System.out.println("d1 == d2");
//    	    }
//    	    
//	    } catch(ParseException e) {
//	        e.printStackTrace();
//	    }
	    
	    
//	    String s = "index-429";
//	    System.out.println();
	    
//	    Set<String> set = new LinkedHashSet<String>();
//	    set.add("aaa");
//	    set.add("aaa");
//	    set.add("aaa");
//	    set.add("aaa");
//	    set.add("bbb");
//	    set.add("bbb");
//	    set.add("bbb");
//	    set.add("ccc");
//	    set.add("ccc");
//	    for(String s: set) {
//	        System.out.println(s);
//	    }
	    
	    
//	    String s = "1:this is the test page";
//	    System.out.println(s.substring(0, s.indexOf(":")));
//	    System.out.println(s.substring(s.indexOf(":")+1));
	    
//	    HashMap<String, Integer> map = new HashMap<String, Integer>();
//	    map.put("a", 300);
//	    map.put("a", 900);
//	    map.put("a", 88888888);
//	    System.out.println(map.get("a"));
//	    System.out.println(map.get("b"));
		
//		double d1 = 0.1;
//		double d2 = 0.2;
//		System.out.println(Constants.df.format(d1+d2));
		
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("ccc");
//		list.add("ddd");
//		list.add("aaa"); // 2
//		list.add("ccc");
//		list.add("ddd");
//		list.add("aaa"); // 5
//		list.add("ccc");
//		list.add("kkk");
//		list.add("aaa"); // 8
//		list.add("ccc");
//		list.add("ddd");
//		list.add("aaa"); // 11
//		int idx = list.indexOf("aaa"); System.out.println(idx);
//		list.remove(idx);
//		list.add(idx, "111");
//		for(String s: list) {
//			System.out.println(s);
//		}
//		System.out.println(getAllIndexOfKey(list, "aaa"));
		
		
//		String s = "Dinosaur Planet•&nbsp;&nbsp;Characters•&nbsp;&nbsp;Plots•&nbsp;&nbsp;Biographies•&nbsp;&nbsp;Quotesmore&nbsp;»Note: some searches may not yield results";
//		s = s.replaceAll("&nbsp;", " ");
//		System.out.println(s);
		
//		String line = null;
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(Constants.stopword_dict_file));
//			while ((line = reader.readLine()) != null) {
//
//			    System.out.println("[" + line + "]");
//			   }
//			
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}

	}

}
