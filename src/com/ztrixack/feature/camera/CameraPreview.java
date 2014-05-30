package com.ztrixack.feature.camera;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ztrixack.utils.Boast;
import com.ztrixack.utils.ZLog;

/** * Surface on which the camera projects it's capture results. */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private static final String DEBUG_TAG = CameraPreview.class.getName();

	private Context mContext;
	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraPreview(Context context) {
		this(context, null);
		if (mCamera == null) {
			mCamera = Camera.open();
		}
	}

	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mContext = context;
		mCamera = camera;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void onPause() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null) {
				Boast.showText(mContext, "Image retrieval failed.");
			} else {
				Boast.showText(mContext, "Image is taken.");
			}
			mCamera.release();

		}
	};

	public void snapIt(View view) {
		mCamera.takePicture(null, null, mPicture);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			ZLog.i(DEBUG_TAG, e.getLocalizedMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.
		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}
		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			ZLog.i(DEBUG_TAG, e.getLocalizedMessage());
		}
	}
}
