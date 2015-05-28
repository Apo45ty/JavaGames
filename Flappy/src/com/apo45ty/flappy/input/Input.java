package com.apo45ty.flappy.input;

import org.lwjgl.glfw.*;

public class Input extends GLFWKeyCallback {
	
	public static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key]= action != GLFW.GLFW_RELEASE;
		
	}
	
}
