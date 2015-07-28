package com.example.wangcan.mygl;


import android.content.Context;
import android.opengl.GLSurfaceView;


import com.example.wangcan.mygl.objects.Floor;
import com.example.wangcan.mygl.programs.TextureProgram;
import com.example.wangcan.mygl.utills.TextureUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;
/**
 * Created by wangcan on 2015/7/22.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private final  Context mContext;




    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] transformMatrix = new float[16];
    private Floor floor;
    private TextureProgram textureProgram;
    private int texture;

    public MyGLRenderer(Context context) {
        mContext = context;

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // it can be called when the application first runs or
        // the device wakes up or the user switches back to the activity^
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        floor = new Floor();

        textureProgram = new TextureProgram(mContext);


        texture = TextureUtil.loadTexture(mContext, R.drawable.floor);





    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // it can be called when the app is firstly created and whenever its size is changed
        glViewport(0, 0, width, height);
        final float aspectRatio = width>height ?
                (float) width / (float) height:
                (float) height/(float) width;

        perspectiveM(projectionMatrix, 0, 45f, aspectRatio, 1f, 10f);

        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -2.5f);  // translate in case the object is disappear
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new  float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, transformMatrix, 0, temp.length);


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // we must draw something here, otherwise it only clears the screen
        // and the rendering buffer will be swapped and displayed on the screen
        // which results in a flicking effect
        glClear(GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(transformMatrix,
                texture);

        floor.bindData(textureProgram);

        floor.draw();

    }


}
