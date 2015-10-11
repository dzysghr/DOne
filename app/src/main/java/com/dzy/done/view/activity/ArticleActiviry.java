package com.dzy.done.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dzy.done.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActiviry extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_content)
    TextView mContent;

    @Bind(R.id.tv_date)
    TextView mDate;
    @Bind(R.id.tv_title)
    TextView mTitle;

    GetArticleTask mTask;


    String mUrl = "default url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");

        mTitle.setText(intent.getStringExtra("title"));
        mDate.setText(intent.getStringExtra("date"));


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTask = new GetArticleTask();
        mTask.execute(mUrl);

    }


    private class GetArticleTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Document doc;
            StringBuilder sb = new StringBuilder();
            try {
                doc = Jsoup.parse(new URL(params[0]), 2000);
                Elements div = doc.getElementsByClass("articulo-contenido");
                Elements ps = div.get(0).getElementsByTag("p");
                int size = ps.size();
                for (int i = 0; i < size; i++) {
                    if (isCancelled())
                        return "";
                    sb.append(ps.get(i).ownText());
                    sb.append("\r\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (!this.isCancelled())
                mContent.setText(s);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mTask != null) {
                mTask.cancel(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
