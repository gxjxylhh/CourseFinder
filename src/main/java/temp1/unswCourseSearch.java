package temp1;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class unswCourseSearch {
	public static final String mainpage = "https://www.handbook.unsw.edu.au";
	public static final String className = "a-browse-tile  a-browse-tile--header-only";
	public static ArrayList<ArrayList<String>> programList = new ArrayList<ArrayList<String>>();

	
	public static ArrayList<ArrayList<String>> getUNSWSearchResults(String query){
		Document doc = null;
		Integer a = 0;
	 
		try {
	   
	
			doc = Jsoup.connect(query).get();
			//System.out.println(doc);
	
			Elements programResults = doc.getElementsByClass(unswCourseSearch.className);
			//System.out.println(programResults);
			
			for (Element programs : programResults) {
				String program = programs.attr("href");
				String programAbb = programs.getElementsByClass("h4").text();
				//System.out.println(programAbb);
				if(a<programResults.size()) {
					ArrayList<String> programName = new ArrayList<String>();
					programName.add(program);
					programName.add(programAbb);
					String findCourseQuery = "https://www.handbook.unsw.edu.au/"+program;
					
					programName = getUNSWCourses(findCourseQuery,programName);
					programList.add(programName);
					a++;
					//System.out.println(programList);
					
				}else {
					System.out.println("oho");
				}
				
			}
			
			/*
			//Find course code following format
			Elements coursehrefResults = doc.getElementsByClass("b-tag-list__item col-md-2 col-xs-6");
			//System.out.println("ahaha"+coursehrefResults);
			ArrayList<String> hrefLinks = new ArrayList<String>();
			String courseCodes = "";
	
			for (Element link : coursehrefResults) {
				String hrefLink = link.text();
				
				
				if(hrefLink.matches(".*\\s\\w{4}\\d{4}.*")) {
					String[] onlycodes = hrefLink.split("\\s");
					for(String onlycode : onlycodes) {
						if(onlycode.matches("\\w{4}\\d{4}")) {
							System.out.println(onlycode);
							courseCodes = courseCodes.concat(onlycode+",");
						}
					}
				}
				hrefLinks.add(courseCodes);
				System.out.println(courseCodes); 
			}
	    		*/
	   		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		return programList;
	}
	
	public static ArrayList<String> getUNSWCourses(String query,ArrayList<String> temp) {
		Document doc = null;
		try {
			doc = Jsoup.connect(query).get();
			Elements courseResults = doc.getElementsByClass("section");
			for(Element course: courseResults) {
				String tempCourseName = course.text();
				if(tempCourseName.matches(".*\\w{4}\\d{4}.*")) {
					//System.out.println(tempCourseName);
					temp.add(tempCourseName);
				}
			}
			//System.out.println(courseResults);
			
		}catch (IOException e) {
			e.printStackTrace(); 
		}
		return temp;
	}
	
}