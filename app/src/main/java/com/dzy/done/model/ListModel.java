package com.dzy.done.model;

import com.dzy.done.bean.ListItem;
import com.dzy.done.config.app;
import com.dzy.done.network.ApiServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * model
 * Created by dzysg on 2015/10/9 0009.
 */
public class ListModel
{

    private ApiServer mApiServer;
    private Call<?> mCall;


    private static class SingleHolder
    {
        public static ListModel mSingle=new ListModel();
    }

    public static ListModel getInstant()
    {
        return SingleHolder.mSingle;
    }

    public ListModel()
    {
        mApiServer = app.getApiServer();
    }


    /**
     * 取消最后一次的请求
     */
    public void cancel()
    {
        if (mCall!=null)
            mCall.cancel();
        mCall =null;
    }


    /** 加载列表数据
     * @param page 页数
     * @param mType 类型
     * @param mCallback 结果回调
     */
    public void LoadDatas(int page,int mType,final ListModelCallback mCallback)
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

        mCall = call;
        call.enqueue(new Callback<List<ListItem>>()
        {
            @Override
            public void onResponse(Call<List<ListItem>> call, Response<List<ListItem>> response)
            {
                mCallback.onFinish(response.body());
                mCall = null;
            }

            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t)
            {
                mCallback.OnFalure(t.getMessage());
                mCall = null;
            }
        });
    }

}
