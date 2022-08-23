
package com.vcare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.News;

public interface NewsService {
	List<News> getAllNews();

	void deleteNewsById(int NewsId);

	void saveNews(News news);

	News GetNewsById(int NewsId);

	void updateNews(News news);

	News addSaveNews(News news);
	void SaveMultimedia(MultipartFile file, News news);
	List<News> getLatestNews();
}
