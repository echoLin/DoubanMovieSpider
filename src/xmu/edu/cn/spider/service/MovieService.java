package xmu.edu.cn.spider.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xmu.edu.cn.spider.dao.MovieDao;
import xmu.edu.cn.spider.entity.Movie;

public class MovieService {
	public static void run(int number){
		List<String> movieList = getMovieList(number);
		for(int i=0; i<movieList.size(); i++){
			Movie movie = getMovie(movieList.get(i));
			//System.out.println(movie.toString());
			MovieDao.addMovie(movie);
		}
	}
	private static List<String> getMovieList(int number){
		List<String> list = new ArrayList<String>();
		try {
			//获取排行榜（JSON数据）
			String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit="+number+"&page_start=0";
			StringBuilder stringBuilder = new StringBuilder();
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while((inputLine = in.readLine()) != null)
				stringBuilder.append(inputLine);
			in.close();
			String jsonString = stringBuilder.toString();
			JSONObject json;
			json = new JSONObject(jsonString);
			JSONArray jsonArray = json.getJSONArray("subjects");
			for(int i = 0; i<jsonArray.length(); i++){
				JSONObject subject = (JSONObject)jsonArray.get(i);
				list.add((String)subject.get("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	private static Movie getMovie(String id){
		String url = "https://movie.douban.com/subject/"+id;
		Movie movie = new Movie();
		try {
			Document doc = Jsoup.connect(url).get();
			if(doc == null)
				return null;
			Element content = doc.getElementById("content");
			if(content == null)
				return null;
			movie.setDoubanId(id);
			Elements h1 = content.getElementsByTag("h1");
			//获取电影名称和年份
			Elements spans = h1.get(0).getElementsByTag("span");
			movie.setTitle(spans.get(0).text());
			movie.setYear(spans.get(1).text().substring(1, 5));
			
			Element article = content.getElementById("info");
			//获取导演
			Elements directors = article.getElementsByIndexEquals(0);
			spans = directors.get(0).getElementsByClass("attrs");
			movie.setDirector(spans.get(0).getElementsByTag("a").text());
			//获取编剧
			Elements scriptwriter = article.getElementsByIndexEquals(1);
			spans = scriptwriter.get(0).getElementsByClass("attrs");
			Elements as = spans.get(0).getElementsByTag("a");
			String scriptwriterStr = "";
			for(Element a : as){
				scriptwriterStr += a.text() + "/";
			}
			scriptwriterStr = scriptwriterStr.substring(0, scriptwriterStr.length()-1);
			movie.setScriptwriter(scriptwriterStr);
			//获取主演
			Elements actors = article.getElementsByIndexEquals(2);
			spans = actors.get(0).getElementsByClass("attrs");
			as = spans.get(0).getElementsByTag("a");
			String actorsStr = "";
			for(Element a : as){
				actorsStr += a.text() + "/";
			}
			actorsStr = actorsStr.substring(0, actorsStr.length()-1);
			movie.setActors(actorsStr);
			
			
			//获取评分
			movie.setScore(Double.valueOf((content.getElementsByClass("rating_num").get(0).text())));
			
			//获取剧情
			movie.setStory(content.getElementById("link-report").getElementsByTag("span").get(0).text());
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return movie;
	}
}
