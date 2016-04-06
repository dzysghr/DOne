package com.dzy.done.presenter;

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

}
