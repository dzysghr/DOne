package com.dzy.done.config;


/**
 *  过期
 */
@Deprecated
public class OneApi
{
	public static String One ="http://www.wufafuwu.com";

	public static String OneArticle = "http://www.wufafuwu.com/a/ONE_wenzhang/list_1_%d.html";
	public static String OneArticleHead = "http://www.wufafuwu.com/a/ONE_wenzhang/";
	
	public static String OnePictureHead="http://www.wufafuwu.com/a/ONE_tupian/";
	public static String OnePicture = "http://www.wufafuwu.com/a/ONE_tupian/list_11_%d.html";
	
	public static String OneThingHead="http://www.wufafuwu.com/a/ONE_dongxi/";
	public static String OneThing="http://www.wufafuwu.com/a/ONE_dongxi/list_10_%d.html";

	public static  String getConnectUrl(int Type,int page)
    {
        String constr = null;
        if (Type == 1)
            constr = OneApi.OneArticle.replace("%d", page + "");
        if (Type == 2)
            constr = OneApi.OnePicture.replace("%d", page + "");
        if (Type == 3)
            constr = OneApi.OneThing.replace("%d", page + "");

        return constr;
    }


    public static String Article= "http://dzyone.applinzi.com/article.php";


    public static String getArticleRequestString(String url)
    {
        return Article + "?url=" + url;
    }
}
