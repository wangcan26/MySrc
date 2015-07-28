precision mediump float;
//varying vec4 v_Color;
varying vec2 v_TexCoords;

//uniform vec4 u_Color;
uniform sampler2D u_TextureUnit;


void main()
{
    gl_FragColor =  texture2D(u_TextureUnit, v_TexCoords);
}