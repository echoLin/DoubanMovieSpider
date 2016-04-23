package xmu.edu.cn.spider.entity;

public class Movie {
	private int id;
	private String doubanId;
	private String title;
	private String year;
	private String actors;
	private String scriptwriter;
	private String director;
	private String story;
	private Double score;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDoubanId() {
		return doubanId;
	}
	public void setDoubanId(String doubanId) {
		this.doubanId = doubanId;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getScriptwriter() {
		return scriptwriter;
	}
	public void setScriptwriter(String scriptwriter) {
		this.scriptwriter = scriptwriter;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String toString(){
		return this.doubanId + " " + this.title + " " + this.year + " " + this.actors + " " + this.scriptwriter + " " + this.director + " " + this.story + " " + this.score;
 	}
	
}
