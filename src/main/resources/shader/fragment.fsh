#version 400 core

in vec3 color;
in vec3 pos2Light;

uniform vec3 lightcolor;//COLOR
out vec4 fragColor;


void main(){
    vec3 lightc = vec3(.85, .7, .2);
    float len = length(pos2Light);
    vec4 threshold = vec4(color, 1) * 7;
    fragColor = max(vec4(1 * color / (len * 1), 1), vec4(color/2, 1));//vec4(color, 1);//
    fragColor += vec4((vec3(.5, .5, .8) / (len * 8)) * lightc, 1);
}