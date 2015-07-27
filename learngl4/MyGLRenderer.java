package com.example.wangcan.mygl;


import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;
/**
 * Created by wangcan on 2015/7/22.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData ;
    private final  Context mContext;

    private static final int NUM_POSITION_COMPONENT = 4;
    private static final int NUM_COLOR_COMPONENT = 3;

    private static final int STRIDE = (NUM_POSITION_COMPONENT + NUM_COLOR_COMPONENT)*BYTES_PER_FLOAT;

    private static final String A_COLOR = "a_Color";
    private int aColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private static final String U_PROJECTION ="u_Projection";
    private int uProjectionLocation;

    private static final String U_MODEL = "model";
    private int uModelLocation ;

    private static final String U_TRANSFORM = "transform";
    private int uTransform;


    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] transformMatrix = new float[16];

    private int program;
    public MyGLRenderer(Context context) {
        mContext = context;

        // define the vertices

        float[] tableVerticesWithTriangles = {

               // X,     Y,   Z,   W,    R,    G,   B
                0f,     0f,   0f, 1f, 1.0f, 1.0f, 1.0f,
                -0.5f, -0.8f, 0f,  1f, 0.7f, 0.7f, 0.7f,
                0.5f,  -0.8f, 0f,  1f,  0.7f, 0.7f, 0.7f,
                0.5f,  0.8f,  0f,  1f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f,  0f,  1f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0f,  1f,  0.7f, 0.7f, 0.7f,


        };


        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);



    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // it can be called when the application first runs or
        // the device wakes up or the user switches back to the activity^
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // obtain the shader source
        String vertexShaderSource = TextResourceRender.readTextFromResource(mContext, R.raw.simple_vertex_shader);
        String fragShaderSource = TextResourceRender.readTextFromResource(mContext, R.raw.simple_frag_shader);

        int vertexShader = ShaderUtils.compileVertexShader(vertexShaderSource);
        int fragShader = ShaderUtils.compileFragShader(fragShaderSource);

        program = ShaderUtils.LinkProgram(vertexShader, fragShader);

        if(LoggerConfig.ON)
        {
            ShaderUtils.validateProgram(program);
        }

        glUseProgram(program);

        //get the location of the variables in program
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        // prosition
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, NUM_POSITION_COMPONENT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

        // color
        vertexData.position(NUM_POSITION_COMPONENT);
        glVertexAttribPointer(aColorLocation, NUM_COLOR_COMPONENT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);

        // get projection location
        uProjectionLocation = glGetUniformLocation(program, U_PROJECTION);

        // get model location
        uModelLocation = glGetUniformLocation(program, U_MODEL);

        //get the transform matrix
        uTransform = glGetUniformLocation(program, U_TRANSFORM);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // it can be called when the app is firstly created and whenever its size is changed
        glViewport(0, 0, width, height);
        final float aspectRatio = width>height ?
                (float) width / (float) height:
                (float) height/(float) width;

       /* if(width > height)
        {
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f );
        }else{
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }*/
        perspectiveM(projectionMatrix, 0, 45f,   aspectRatio, 1f,  10f);

        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -2.5f);  // translate in case the object is disappear
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new  float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, transformMatrix, 0, temp.length );

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // we must draw something here, otherwise it only clears the screen
        // and the rendering buffer will be swapped and displayed on the screen
        // which results in a flicking effect
       glClear(GL_COLOR_BUFFER_BIT);
      //  glUniformMatrix4fv(uProjectionLocation, 1, false, projectionMatrix, 0);
      //  glUniformMatrix4fv(uModelLocation, 1, false, modelMatrix, 0);
        glUniformMatrix4fv(uTransform, 1, false, transformMatrix, 0);

        // draw a table
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);




    }


}
