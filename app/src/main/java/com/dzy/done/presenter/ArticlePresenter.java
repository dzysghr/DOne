package com.dzy.done.presenter;

import com.dzy.done.bean.ListItem;
import com.dzy.done.view.ArticleContentView;

/**
 *
 * Created by dzysg on 2016/4/6 0006.
 */
public interface ArticlePresenter
{
    void onAttach(ArticleContentView view);
    void onDetach();
    void LoadArticleContent(String url);
    void LoadQAContent(String url);
    void saveToFavorite(ListItem item);
    void ExistfromFavorite(ListItem url);
    void deleteFromFavorite(ListItem url);

}
