package com.example.wangcan.mygl.data;

import com.example.wangcan.mygl.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static android.opengl.GLES20.*;

/**
 * Created by wangcan on 2015/7/28.
 */
  public class Data
{
    private FloatBuffer floatBuffer;

    public Data(float[] data)

    {
        floatBuffer = ByteBuffer.allocateDirect(data.length* Constants.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(data);
    }

    public void setAttributePointerData(int attributePos, int numOfComponent, int stride, int offset)
    {
        floatBuffer.position(offset);
        glVertexAttribPointer(attributePos, numOfComponent, GL_FLOAT, false,
                stride, floatBuffer);
        glEnableVertexAttribArray(attributePos);  // the attribute is disabled by default

        floatBuffer.position(0);
    }
}
