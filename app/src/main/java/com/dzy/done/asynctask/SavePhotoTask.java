package com.dzy.done.asynctask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.dzy.done.config.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Created by dzysg on 2016/5/5 0005.
 */
public class SavePhotoTask extends AsyncTask<Object,Void,String>
{

    @Override
    protected String doInBackground(Object... params)
    {
        Bitmap bitmap = (Bitmap) params[0];
        String fileName = (String) params[1]+".jpg";

        return saveCroppedImage(bitmap,fileName);
    }

    private String saveCroppedImage(Bitmap bmp,String fname)
    {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),fname);

        FileOutputStream fos = null;
        try {

            if(file.createNewFile())
            {
                fos = new FileOutputStream(file);
                String re = "保存失败";
                if(bmp.compress(Bitmap.CompressFormat.JPEG,100,fos))
                {
                    re = "保存成功";
                    //通知媒体库更新
                    app.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                }
                fos.flush();
                fos.close();
                return  re;

            }else
            {
                return "图片已经存在";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String aVoid)
    {
        super.onPostExecute(aVoid);
            Toast.makeText(app.getContext(),aVoid,Toast.LENGTH_SHORT).show();

    }
}
