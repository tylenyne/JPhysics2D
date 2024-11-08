#version 400 core

in vec3 color;

uniform vec3 light;//COLOR

out vec4 fragColor;


void main(){
    fragColor = vec4(color, 1.0);
}