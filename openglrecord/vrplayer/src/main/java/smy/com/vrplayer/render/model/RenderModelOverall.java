package smy.com.vrplayer.render.model;

import android.animation.PropertyValuesHolder;
import android.opengl.GLES20;
import android.opengl.Matrix;

import smy.com.vrplayer.common.VrConstant;
import smy.com.vrplayer.render.RenderMatrix;

import static android.animation.PropertyValuesHolder.ofFloat;

/**
 * Created by SMY on 2017/5/17.
 */

public class RenderModelOverall extends AbstractRenderModel {

    public RenderModelOverall() {
        super();
    }

    @Override
    public void init(int width, int height) {
        super.init(width,height);
    }

    @Override
    public void setProjectionMatrix() {
        Matrix.setIdentityM(mRenderMatrix.mProjectionMatrix,0);
    }

    @Override
    public void setViewMatrix() {
        Matrix.setIdentityM(mRenderMatrix.mViewMatrix,0);
    }

    @Override
    protected void setMVPMatrix(){
        Matrix.multiplyMM(mRenderMatrix.mMVPMatrix, 0, mRenderMatrix.mProjectionMatrix, 0, mRenderMatrix.mViewMatrix, 0);
    }

    @Override
    public void update(RenderMatrix data){
        setViewPort();
        super.update(data);
    }


    private void setViewPort(){
        GLES20.glViewport(0, (mHeight - mWidth / 2) / 2, mWidth, mWidth / 2 );
    }

    @Override
    public void setViewPortYUV(boolean pano) {
        setViewPort();
        if (!pano){
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
        }
    }

    @Override
    public PropertyValuesHolder getValuesHolder(String type, RenderMatrix matrix) {
        switch (type){
            case PROPERTY_FIELD_OF_VIEW:
                return ofFloat(PROPERTY_FIELD_OF_VIEW, matrix.mFieldOfView, VrConstant.INIT_FIELD_VIEW);
            case PROPERTY_EYE_Y:
                return ofFloat(PROPERTY_EYE_Y, matrix.mEyeY, VrConstant.INIT_POSITION);
            case PROPERTY_GESTURE_X:
                return ofFloat(PROPERTY_GESTURE_X, 0.0f, 0.0f);
            case PROPERTY_GESTURE_Y:
                return ofFloat(PROPERTY_GESTURE_Y, 0.0f, 0.0f);
        }
        return null;
    }

    @Override
    public int getAnimationDuration() {
        return  10;
    }
}
