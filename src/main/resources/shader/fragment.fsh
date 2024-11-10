#version 400 core

in vec3 color;
in vec3 pos2Light;

uniform vec3 lightcolor;//COLOR
out vec4 fragColor;


void main(){
    vec3 lightc = vec3(2, 7, 8.5);
    float len = min(length(pos2Light), 1);
    vec4 threshold = vec4(color, 1) * 7;
    fragColor = vec4(color / ((lightc * len * 6) + lightc/10), 0);
}