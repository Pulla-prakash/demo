package com.vcare.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
//Abhilash
@Entity
@Table(name = "News")             
public class News{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int NewsId;
	private String ContentName; 
	@Column(columnDefinition = "character(300000000)")
	private String NewsDescription;
	@Lob
	private String newsImage;
	private LocalDate created;
	@Lob
    private String video;
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getNewsId() {
		return NewsId;
	}

	public void setNewsId(int newsId) {
		NewsId = newsId;
	}

	
	 public String getContentName() { return ContentName; }
	 
	 public void setContentName(String contentName) { ContentName = contentName; }
	
	public String getNewsDescription() {
		return NewsDescription;
	}

	public void setNewsDescription(String newsDescription) {
		NewsDescription = newsDescription;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getNewsImage() {
		return newsImage;
	}

	public void setNewsImage(String newsImage) {
		this.newsImage = newsImage;
	}

	@Override
	public String toString() {
		return "News [NewsId=" + NewsId + ", ContentName=" + ContentName + ", NewsDescription=" + NewsDescription
				+ ", newsImage=" + newsImage + ", created=" + created + "]";
	}

	
	 
}
