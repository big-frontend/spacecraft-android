#extension GL_OES_EGL_image_external : require
precision mediump float;


struct Rect {
    vec4 color;
    float left;
    float top;
    float right;
    float bottom;
};


// important to include in order to use rendered Android View to gl texture
uniform samplerExternalOES u_Texture;

uniform float u_GlobalTime;


const int MAX_RECTS = 4;
uniform Rect u_Rects[MAX_RECTS];

varying vec2 v_TexCoordinate;

void main()
{

    vec2 uv = v_TexCoordinate.xy;

    vec4 primary_color = texture2D(u_Texture, uv);

    vec4 tmpColor = vec4(0.0, 0.0, 0.0, 0.0);

    bool set = false;

    for(int idx = 0; idx < MAX_RECTS; idx++) {
        if(uv.x > u_Rects[idx].left && uv.x < u_Rects[idx].right && uv.y > u_Rects[idx].top && uv.y < u_Rects[idx].bottom) {
            tmpColor += u_Rects[idx].color;
            set = true;
        }
    }

    if(set) {
        primary_color *= tmpColor;
    }

    gl_FragColor = primary_color;
}