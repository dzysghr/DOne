package com.dzy.done.presenter;

import com.dzy.done.bean.ArticleItem;
import com.dzy.done.bean.ListItem;
import com.dzy.done.view.StringContentView;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public interface StringContentPresenter
{
    void onAttach(StringContentView view);
    void onDetach();
    void LoadArticleContent(String url);
    void LoadQAContent(String url);
    void saveToFavorite(ListItem item,ArticleItem content);
    void ExistfromFavorite(ListItem url);
    void deleteFromFavorite(ListItem url);

}
