package com.dzy.done.bean;

public class ThingItem
{
	private long id;

	private String name;
	private String img;
	private String content;

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getImg()
	{
		return img;
	}
	public void setImg(String img)
	{
		this.img = img;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}

    @Override
    public String toString()
    {
        return "ThingItem{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
