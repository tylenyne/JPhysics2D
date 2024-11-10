#version 400 core

in vec3 position;

out vec3 color;
out vec3 pos2Light;

uniform mat4 transformationMatrix;

uniform vec3 uColor;

uniform vec3 light;//POSITION

uniform mat4 projectionMatrix;//Move to different file if needed

void main(void){//projectionMatrix *
    vec4 worldpos = (transformationMatrix * vec4(position, 1.0));
    pos2Light = vec3(worldpos) - light;//Because it thinks that the pixel close arent scaled when they actually are
    worldpos.x *= (9/16.0);
    gl_Position = worldpos;
    color = uColor;

}