#version 130

uniform sampler2D DiffuseSampler;


uniform vec4 ColorModulate;

uniform mat4 ProjMat;
uniform vec2 OutSize;

uniform float STime;


out vec2 texCoord;
out vec4 out_Color;


void main() {

    vec4 out1 = texture(DiffuseSampler, texCoord.xy);


    out_Color = out1 * ColorModulate;


}