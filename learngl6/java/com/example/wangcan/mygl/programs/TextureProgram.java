package com.example.wangcan.mygl.programs;

import android.content.Context;
import android.graphics.Shader;

import com.example.wangcan.mygl.R;
import static android.opengl.GLES20.*;

/**
 * Created by wangcan on 2015/7/28.
 */
public class TextureProgram extends ShaderProgram{
    // location of the variables in shaders
    private int aPositionLocation;
    private int aTextureCoordsLocation;

    private int uTransformLocation;
    private int uTextureUnitLocation;

    public TextureProgram(Context context)
    {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_frag_shader);

        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        uTransformLocation = glGetUniformLocation(program, U_TRANSFORM);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordsLocation = glGetAttribLocation(program, A_TEXCOORDS);

    }

    public void setUniforms(float[] matrix , int textureId){

        glUniformMatrix4fv(uTransformLocation, 1, false, matrix, 0);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);

        glUniform1i(uTextureUnitLocation, 0);

    }

    public int getPosAttribute()
    {
        return aPositionLocation;
    }

    public int getTexCoordsAttribute()
    {
        return aTextureCoordsLocation;
    }




}
