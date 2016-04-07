package com.dzy.done.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ListItem implements Serializable
{
	public static int PICTURE = 2; //图片
    public static int ARTICLE = 1; //文章
    public static int THING = 3;  //东西
	public static int QA = 4;  //问答

    @JsonIgnore
    private int id=0;

	private String title;
	private String content;
	private String url;
	private String date;
	private String img;
	private int type = 1;



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


		
}
