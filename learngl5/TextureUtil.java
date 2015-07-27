package com.example.wangcan.mygl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;

/**
 * Created by wangcan on 2015/7/27.
 */
public class TextureUtil {

    private static String TAG = "TextureUtil";

    public static int loadTexture(Context context, int resourceId)
    {
        final int[] textureIds = new int[1];

        glGenTextures(1, textureIds, 0);
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);

        // load
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap img = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        if(img == null)
        {
            if(LoggerConfig.ON)
            {
                Log.w(TAG, "Resource ID " + resourceId + " could not be decoded");
            }

            glDeleteTextures(1, textureIds, 0);
            return 0;
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);



        texImage2D(GL_TEXTURE_2D, 0, img, 0);
        img.recycle();

        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureIds[0];
    }

}
