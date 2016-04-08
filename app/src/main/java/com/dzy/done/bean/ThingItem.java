package com.dzy.done.bean;

import com.dzy.easydao.dborm.annotation.Column;
import com.dzy.easydao.dborm.annotation.ID;
import com.dzy.easydao.dborm.annotation.Table;

@Table
public class ThingItem
{
	@ID
	private long id;

	@Column
	private String name;
    @Column
	private String img;
    @Column
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
