attribute vec4 a_Position;
//attribute vec4 a_Color;
attribute vec2 a_Tex;

//uniform mat4 model;
//uniform mat4 u_Projection;
uniform mat4 transform;

//varying vec4 v_Color;
varying vec2 v_Tex;

void main()
{
   // v_Color = a_Color;
    v_Tex = a_Tex;
    gl_Position = transform*a_Position;
   // gl_PointSize = 10.0;
}