#version 400 core

in vec3 position;

out vec3 color;

uniform mat4 transformationMatrix;

uniform vec3 uColor;

uniform mat4 projectionMatrix;//Move to different file if needed

void main(void){//projectionMatrix *
    gl_Position = transformationMatrix * vec4(position, 1.0);//*transformationMatrix applies to position
    color = uColor;
}