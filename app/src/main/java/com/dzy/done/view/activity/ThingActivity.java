package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzy.done.R;
import com.dzy.done.bean.ThingItem;
import com.dzy.done.config.OneApi;
import com.dzy.done.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThingActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    ThingTask mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);
        ButterKnife.bind(this);

        WindowManager wm = this.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        mIv.getLayoutParams().height = height/2;



        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!NetworkUtils.isNetworkConnected())
        {
            onFalure();
            return;
        }

        mTask = new ThingTask();
        mTask.execute(url);

    }

    public void onFalure()
    {
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    private class ThingTask extends AsyncTask<String,Void,ThingItem>
    {

        @Override
        protected ThingItem doInBackground(String... params) {
            String url = params[0];

            Document doc;
            ThingItem item = null;
            try
            {
                doc = Jsoup.parse(new URL(url), 2000);
                item = new ThingItem();

                Element root = doc.getElementsByClass("d").get(0);
                String src = root.getElementsByClass("cosas-imagen").get(0).getElementsByTag("img").get(0).attr("src");
                item.setSrc(OneApi.One+src);

                String name = root.getElementsByClass("cosas-titulo").get(0).ownText();
                item.setName(name);

                String content = root.getElementsByClass("cosas-contenido").get(0).ownText();
                item.setContent(content);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return item;
        }

        @Override
        protected void onPostExecute(ThingItem Item) {
            ShowData(Item);
        }
    }

    private void ShowData(ThingItem item) {
        if (item==null)
            Log.i("tag", "item is null");
        else {
            Picasso.with(this).load(item.getSrc()).fit().into(mIv);
            if (TextUtils.isEmpty(item.getName()))
                mTvTitle.setVisibility(View.GONE);
            else
            mTvTitle.setText(item.getName());
            mTvContent.setText(item.getContent());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTask!=null)
            mTask.cancel(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return true;
    }
}
