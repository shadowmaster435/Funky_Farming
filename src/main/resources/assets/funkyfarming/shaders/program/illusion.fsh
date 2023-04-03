#version 130

uniform sampler2D DiffuseSampler;


uniform vec4 ColorModulate;

uniform mat4 ProjMat;
uniform vec2 OutSize;

uniform float STime;


out vec2 texCoord;
out vec4 out_Color;


vec2 wobble2(vec2 xy, vec2 uv ) {
    return vec2((xy.y * 0.0005) + fract(sin((xy.x * 0.005) * sin(xy.y * 0.005) + STime) ), uv.y);
}
void main() {

    vec4 out1 = texture(DiffuseSampler, texCoord.xy);


    out_Color = out1 * ColorModulate;


}