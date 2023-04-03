#version 130

in vec4 Position;
in vec2 texCoordIn;

uniform mat4 ProjMat;
uniform vec2 OutSize;
uniform float STime;

out vec2 texCoord;

void main() {


    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);

    texCoord = Position.xy / OutSize;
}