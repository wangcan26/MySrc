attribute vec4 a_Position;
attribute vec4 a_Color;


uniform mat4 u_Projection;

varying vec4 v_Color;
void main()
{
    v_Color = a_Color;
    gl_Position = u_Projection*a_Position;
    gl_PointSize = 10.0;
}