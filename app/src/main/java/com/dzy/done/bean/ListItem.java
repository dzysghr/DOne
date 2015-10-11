package com.dzy.done.bean;

public class ListItem
{
	private String Title;
	private String Content;
	private String url;
	private String Date;
	private String img;
	private int TYPE = 1;


	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getTYPE() {
		return TYPE;
	}

	public void setTYPE(int TYPE) {
		this.TYPE = TYPE;
	}


		
}
