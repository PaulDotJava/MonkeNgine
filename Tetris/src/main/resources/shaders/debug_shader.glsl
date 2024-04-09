///////////
//-vertex//
///////////
#version 330 core

layout(location = 0) in vec2 v_position;
layout(location = 1) in vec4 v_color;
layout(location = 2) in float v_radius;

uniform mat4 u_ProjMat;
uniform mat4 u_ViewMat;

out float o_radius;
out vec4 o_color;
out vec2 o_center;

void main()
{
    gl_PointSize = v_radius * 2;
    gl_Position = u_ViewMat * u_ProjMat *  vec4(v_position, 1, 1);

    o_center = v_position;

    o_color = v_color;
    o_radius = v_radius;
}

/////////////
//-fragment//
/////////////
#version 330 core

out vec4 fragColor;

in float o_radius;
in vec4 o_color;
in vec2 o_center;

void main()
{
    /*
    vec2 d = o_center - gl_FragCoord.xy;
    float dist = sqrt(d.y * d.y + d.x * d.x);
    float val = smoothstep(40.0, 40.0 + 0.1, dist);

    fragColor = vec4(val, 0.0, 0.0, 1.0);
    */

    vec2 d = (o_center - gl_FragCoord.xy);
    float dist = sqrt(d.y * d.y + d.x * d.x);

    float val = smoothstep(o_radius, o_radius - 0.1, dist);

    //vec4 color = o_color * val;

    fragColor = vec4(o_color.xyz, val);

    /*if (dist < 50)
    {
        fragColor = vec4(0, 1, 0, 1);
    }else
    {
        fragColor = vec4(dist, 0, 0, 1);
    }*/

    /*
    if (dist < o_radius) {
        fragColor = vec4(1, 0, 0, 1);
    }
    else {
        fragColor = vec4(0, 1, 0, 1);
    }
    */

    /*
    if (gl_FragCoord.x < 100)
    {
        fragColor = vec4(1, 0, 0, 1);
    }else
    {
        fragColor = vec4(0, 1, 0, 1);
    }
    */
}