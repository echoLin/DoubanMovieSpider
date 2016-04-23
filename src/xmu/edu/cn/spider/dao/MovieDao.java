package xmu.edu.cn.spider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import xmu.edu.cn.db.util.DBUtil;
import xmu.edu.cn.spider.entity.Movie;

public class MovieDao {
	public static void addMovie(Movie movie){
		try{
			//抓取数据调快会被禁IP！
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try{
			connection = DBUtil.openConnection();
			String sql = "insert into movie(doubanId, title, actors, scriptwriter, director,story, score, year) values(?,?,?,?,?,?,?,?) on duplicate key update score = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, movie.getDoubanId());
			prepareStatement.setString(2, movie.getTitle());
			prepareStatement.setString(3, movie.getActors());
			prepareStatement.setString(4, movie.getScriptwriter());
			prepareStatement.setString(5, movie.getDirector());
			prepareStatement.setString(6, movie.getStory());
			prepareStatement.setDouble(7, movie.getScore());
			prepareStatement.setString(8, movie.getYear());
			prepareStatement.setDouble(9, movie.getScore());
			prepareStatement.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
