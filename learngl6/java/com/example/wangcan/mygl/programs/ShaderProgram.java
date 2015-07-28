package com.example.wangcan.mygl.programs;

import android.content.Context;

import com.example.wangcan.mygl.LoggerConfig;
import com.example.wangcan.mygl.utills.ShaderUtils;
import com.example.wangcan.mygl.utills.TextResourceRender;


import static android.opengl.GLES20.*;

/**
 * Created by wangcan on 2015/7/28.
 */





public class ShaderProgram {
// constants
   // uniforms
    protected  static final String U_TRANSFORM = "u_Transform";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

   // vertex attributes
    protected  static final String A_POSITION = "a_Position";
    protected  static final String A_COLOR = "a_color";
    protected static final String A_TEXCOORDS = "a_TexCoords";

    protected int program;

       protected ShaderProgram(Context context, int vertexResourceId, int fragResourceId)
       {
          program = ShaderUtils.buildProgram(TextResourceRender.readTextFromResource(context, vertexResourceId),
                   TextResourceRender.readTextFromResource(context, fragResourceId));
       }

       public void useProgram()
       {
           if(LoggerConfig.ON)
           {
               ShaderUtils.validateProgram(program);
           }
           glUseProgram(program);
       }


}
