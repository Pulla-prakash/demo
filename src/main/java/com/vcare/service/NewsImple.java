
package com.vcare.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.News;
import com.vcare.repository.NewsRepository;

@Service
public class NewsImple implements NewsService {
	@Autowired
	NewsRepository newsrepository;

	@Override
	public List<News> getAllNews() {
		return newsrepository.findAll();
	}

	@Override
	public void deleteNewsById(int NewsId) {
		newsrepository.deleteById(NewsId);
	}

	@Override
	public void saveNews(News news) {
		newsrepository.save(news);
	}

	@Override
	public News GetNewsById(int NewsId) {
		return newsrepository.getById(NewsId);
	}

	@Override
	public void updateNews(News news) {
		newsrepository.save(news);
	}

	@Override
	public News addSaveNews(News news) {
		return newsrepository.save(news);
	}

	@Override
	public List<News> getLatestNews() {
// TODO Auto-generated method stub
		return newsrepository.findPresentNews();
	}

	@Override
	public void SaveMultimedia(MultipartFile file, News news) {
		newsrepository.save(news);
	}
}
/*
 * public List<News> getByKeyword(String keyword){ return
 * newsrepository.findByKeyword(keyword);
 *
 * }
 */
