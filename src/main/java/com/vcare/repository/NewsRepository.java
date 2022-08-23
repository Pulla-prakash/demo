
package com.vcare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vcare.beans.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
	@Query(value = "SELECT news_id, content_name, news_description, created,news_image,video\r\n" + " FROM public.news\r\n"
			+ " WHERE CURRENT_DATE-INTERVAL '30 DAY'< created;", nativeQuery = true)
	List<News> findPresentNews();
	/*
	 * @Query(value =
	 * "SELECT news_id, content_name, news_description, created, news_image\r\n" +
	 * " FROM public.news\r\n" + " WHERE content_name::text LIKE '%keyword%';",
	 * nativeQuery = true) List<News> findByKeyword(@Param("keyword") String
	 * keyword);
	 */}
