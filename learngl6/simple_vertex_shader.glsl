attribute vec4 a_Position;
attribute vec2 a_TexCoords;


uniform mat4 u_Transform;

//varying vec4 v_Color;
varying vec2 v_TexCoords;

void main()
{
    v_TexCoords = a_TexCoords;
    gl_Position = u_Transform*a_Position;
}