#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords);
    float gray = dot(color.rgb, vec3(0.3, 0.59, 0.11)); // luminance formula
    gl_FragColor = vec4(vec3(gray), color.a);
}
