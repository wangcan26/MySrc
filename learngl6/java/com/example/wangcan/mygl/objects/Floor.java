package com.example.wangcan.mygl.objects;

import android.graphics.Shader;

import com.example.wangcan.mygl.Constants;
import com.example.wangcan.mygl.data.Data;
import com.example.wangcan.mygl.programs.ShaderProgram;
import com.example.wangcan.mygl.programs.TextureProgram;


import static android.opengl.GLES20.*;

/**
 * Created by wangcan on 2015/7/28.
 */
public class Floor {
    private Data vertexData;

    //constants sections
    private static final int NUM_POS_COMPONENT = 4;
    private static final int NUM_UV_COMPONENT = 2;
    private static final int STRIDE = (NUM_POS_COMPONENT+NUM_UV_COMPONENT)* Constants.BYTES_PER_FLOAT;

    // define the vertexdata of the floor
    private static final float[] floorData =
            {
                    // X,     Y,   Z,   W,  U,   V  //  R,    G,   B
                    -0.5f,  -0.8f, 0f, 1f, 0f, 1f,
                    0.5f,   -0.8f, 0f, 1f, 1f, 1f,
                    0.5f,  0.8f,   0f, 1f, 1f, 0f,  // triangle 1

                    0.5f,  0.8f,  0f, 1f,  1f, 0f,
                    -0.5f, 0.8f, 0f, 1f,  0f, 0f,
                    -0.5f,  -0.8f, 0f, 1f, 0f, 1f
            };


    public Floor()
    {
        vertexData = new Data(floorData);
    }

    public void bindData(TextureProgram shaderProgram)
    {
        // bind position data
        vertexData.setAttributePointerData(shaderProgram.getPosAttribute(),NUM_POS_COMPONENT, STRIDE,
                0);

        // bind UV data
        vertexData.setAttributePointerData(shaderProgram.getTexCoordsAttribute(),NUM_UV_COMPONENT,
                STRIDE, NUM_POS_COMPONENT);
    }


    public void draw()
    {
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }





}
