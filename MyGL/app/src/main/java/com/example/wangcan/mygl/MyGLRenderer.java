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
/**
 * Created by wangcan on 2015/7/22.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData ;
    private final  Context mContext;


    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private int program;
    public MyGLRenderer(Context context) {
        mContext = context;

        // define the vertices

        float[] tableVerticesWithTriangles = {
                -0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, 0.5f, // Triangle 1

                0.5f, 0.5f,
                0.5f, -0.5f,
                -0.5f, -0.5f , // Triangle 2

                -0.5f, 0f,
                0.5f, 0f,    // line

                //a  punck
                -0f, 0f,


                0f, -0.25f,
                0f, 0.25f  // mallets
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
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // it can be called when the app is firstly created and whenever its size is changed
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // we must draw something here, otherwise it only clears the screen
        // and the rendering buffer will be swapped and displayed on the screen
        // which results in a flicking effect
       glClear(GL_COLOR_BUFFER_BIT);

        // draw a table
        glUniform4f(uColorLocation, 1.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        //draw a line
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        //draw a punck
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8,1);

        //draw two points
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 10, 1);


    }


}
