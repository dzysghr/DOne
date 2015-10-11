package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzy.done.R;
import com.dzy.done.bean.PictureItem;
import com.dzy.done.config.OneApi;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PictureActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.num)
    TextView mTvNum;
    @Bind(R.id.author)
    TextView mTvAuthor;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_day)
    TextView mTvDay;
    @Bind(R.id.tv_mm_yy)
    TextView mTvMonth;

    PictureTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        WindowManager wm = this.getWindowManager();

        int height = wm.getDefaultDisplay().getHeight();
        mIv.getLayoutParams().height = height/2;

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTask = new PictureTask();
        mTask.execute(url);


    }

    private class PictureTask extends AsyncTask<String,Void,PictureItem>
    {

        @Override
        protected PictureItem doInBackground(String... params) {
            String url = params[0];
            Document doc;
            PictureItem item = null;
            try
            {
                doc = Jsoup.parse(new URL(url), 2000);
                item = new PictureItem();

                Element root = doc.getElementsByClass("d").get(0);

                String src = root.getElementsByClass("one-imagen").get(0).getElementsByTag("img").get(0).attr("src");
                item.setImg(OneApi.One+src);

                String num = root.getElementsByClass("one-titulo").get(0).ownText();
                item.setNum(num);

                String author = root.getElementsByClass("one-imagen-leyenda").get(0).ownText();
                item.setAuthor(author);

                String content = root.getElementsByClass("one-cita").get(0).ownText();
                item.setContent("     "+content);

                String day = root.getElementsByClass("dom").get(0).ownText();
                item.setDay(day);

                String year = root.getElementsByClass("may").get(0).ownText();

                item.setYear(year);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return item;
        }

        @Override
        protected void onPostExecute(PictureItem pictureItem) {
            ShowData(pictureItem);
        }
    }

    private void ShowData(PictureItem item) {
        Picasso.with(this).load(item.getImg()).fit().into(mIv);

        mTvContent.setText(item.getContent());
        mTvDay.setText(item.getDay());
        mTvMonth.setText(item.getYear());
        mTvAuthor.setText(item.getAuthor());
        mTvNum.setText(item.getNum());

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
