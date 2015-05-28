package com.apo45ty.flappy.utils;

import java.nio.*;

public class BufferUtils {
	private BufferUtils() {
	}
	
	public static ByteBuffer createByteBuffer(byte[] elements) {
		ByteBuffer result = ByteBuffer.allocateDirect(elements.length).order(ByteOrder.nativeOrder());
		result.put(elements).flip();
		return result;
	}
	
	public static FloatBuffer createFloatBuffer(float[] elements) {
		FloatBuffer result = ByteBuffer.allocateDirect(elements.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(elements).flip();
		return result;
	}
	
	public static IntBuffer createIntBuffer(int[] elements) {
		IntBuffer result = ByteBuffer.allocateDirect(elements.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(elements).flip();
		return result;
	}
}
