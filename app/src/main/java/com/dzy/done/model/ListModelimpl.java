package com.dzy.done.model;

import com.dzy.done.bean.ListItem;
import com.dzy.done.network.ApiServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * model
 * Created by dzysg on 2015/10/9 0009.
 */
public class ListModelimpl implements IListModel
{

    int mType = 1;
    ListModelCallback mCallback;
    private ApiServer mApiServer;


    public ListModelimpl(int type,ListModelCallback callback,ApiServer api)
    {
        mType = type;
        mCallback = callback;
        mApiServer = api;
    }

    @Override
    public void LoadDatas(int page)
    {
        Call<List<ListItem>> call;
        if (mType == ListItem.ARTICLE)
            call = mApiServer.getArticleList(page);
        else if (mType == ListItem.PICTURE)
            call = mApiServer.getPictureList(page);
        else if(mType==ListItem.THING)
            call = mApiServer.getThingList(page);
        else
            call = mApiServer.getQAList(page);


        call.enqueue(new Callback<List<ListItem>>()
        {
            @Override
            public void onResponse(Call<List<ListItem>> call, Response<List<ListItem>> response)
            {
                mCallback.onFinish(response.body());
            }

            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t)
            {
                mCallback.OnFalure(t.getMessage());
            }
        });
    }
}
