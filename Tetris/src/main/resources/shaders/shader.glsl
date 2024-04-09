///////////
//-vertex//
///////////
#version 330 core

/* Taking in the vertex attributes, specified in the vertex layout */

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 uv;

/* Declaring the variables that get parsed down to the fragment shader (output variables) */

out vec2 uvCoord;

/* Taking in variables parsed in by the CPU (C++) */

uniform mat4 u_ProjMat;
uniform mat4 u_ModelMat;
uniform mat4 u_ViewMat;

void main()
{
    /* Calculating the vertex position on the screen */

    //gl_Position = u_ProjectionMatrix * u_ViewMatrix * u_ModelMatrix * position;
    gl_Position =  u_ViewMat * u_ProjMat * u_ModelMat * position;

    /* Filling output variables with data */

    uvCoord = uv;
}
/////////////
//-fragment//
/////////////
#version 330 core

/* Declaring "FragColor" as the final color of this fragment */

//out vec4 FragColor;

/* Receiving variables parsed in by the vertex shader */

in vec2 uvCoord;
/* Receiving variables parsed in by the CPU (C++) */

uniform sampler2D u_Sampler;

void main()
{
    gl_FragColor = vec4(texture(u_Sampler, uvCoord).xyz, 1.0);
    //gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
}
