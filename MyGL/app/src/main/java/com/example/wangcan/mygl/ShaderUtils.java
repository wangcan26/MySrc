package com.example.wangcan.mygl;
/**
 * Created by wangcan on 2015/7/23.
 */

import android.util.Log;


import static android.opengl.GLES20.*;
public class ShaderUtils {
    private static final String TAG = "ShaderHelper";


    public static int compileVertexShader(String shaderCode)
    {
        return compileShader(GL_VERTEX_SHADER,shaderCode);
    }

    public static int compileFragShader(String shaderCode)
    {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int compileShader(int type, String shaderCode)
    {
        final int shaderObjectId = glCreateShader(type);

        if(shaderObjectId == 0)
        {
            if(LoggerConfig.ON)
            {
                Log.w(TAG, "Could not create new shader.");
            }

            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if(LoggerConfig.ON)
        {
            Log.v(TAG, "Results of compiling source: "+"\n" + shaderCode +"\n:"
            + glGetShaderInfoLog(shaderObjectId));
        }

        if(compileStatus[0] == 0)
        {
            glDeleteShader(shaderObjectId);

            if(LoggerConfig.ON)
            {
                Log.w(TAG, "Compliation of shader failed");
            }

            return 0;
        }

        return shaderObjectId;
    }

    // link the shaders to program
    public static int LinkProgram(int vertexShaderId, int fragShaderId)
    {
        final int programObjectId = glCreateProgram();
        if(programObjectId ==0)
        {
            if(LoggerConfig.ON)
            {
                Log.w("TAG", "Could not create new program");
            }

            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragShaderId);

        glLinkProgram(programObjectId);

        //check any error
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if(LoggerConfig.ON)
        {
            Log.v(TAG, "Results of linking program:\n" + glGetProgramInfoLog(programObjectId));
        }

        if(linkStatus[0] == 0)
        {
            glDeleteProgram(programObjectId);
            if(LoggerConfig.ON)
            {
                Log.w(TAG, "Linking of program valid");
            }
            return 0;
        }

        return programObjectId;

    }

    // validate the program when the glSurfaceView created
    public static boolean validateProgram(int programObjected)
    {
        glValidateProgram(programObjected);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjected, GL_VALIDATE_STATUS, validateStatus, 0 );
        Log.v(TAG, "Results of validating program: "+ validateStatus[0] +"\nLog: "+glGetProgramInfoLog(programObjected));

        return validateStatus[0] !=0;
    }





}
