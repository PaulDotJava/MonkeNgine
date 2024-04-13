///////////
//-vertex//
///////////
#version 330 core

layout(location = 0) in vec2 v_position;
layout(location = 1) in vec3 v_color;

uniform mat4 u_ProjMat;
uniform mat4 u_ViewMat;

out vec3 o_color;

void main()
{
    gl_Position = u_ViewMat * u_ProjMat *  vec4(v_position, 1, 1);

    o_color = v_color;
}

/////////////
//-fragment//
/////////////
#version 330 core

in vec3 o_color;

void main()
{
    gl_FragColor = vec4(0, 0, 1, 1);
}