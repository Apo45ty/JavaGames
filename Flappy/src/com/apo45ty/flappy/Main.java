package com.apo45ty.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.apo45ty.flappy.graphics.*;
import com.apo45ty.flappy.input.*;
import com.apo45ty.flappy.level.*;
import com.apo45ty.flappy.math.*;

public class Main implements Runnable{
	private int width = 1200;
	private int height = 720;
	private Thread thread;
	private boolean running = false;
	private long window;
	private Level level ;
	
	
	public  void start() {
		running = true;
		thread = new Thread(this,"Game");
		thread.start();
		
	}

	private void init() {
		if(glfwInit() != GL_TRUE){
			//TODO handle
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "flappy", NULL, NULL);
		if(window == NULL){
			//TODO handle
			return;
		}
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode)-width)/2, (GLFWvidmode.height(vidmode)-height)/2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GLContext.createFromCurrent();
		  
		glClearColor(1.0f,1.0f,1.0f,1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		System.out.println("OpenGL: "+glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f*9.0f/16.0f, 10.0f*9.0f/16.0f, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1f("tex", 1);
		
		level = new Level();
	}
	
	public void run() {
		init();
		while(running){
			update();
			render();
			
			if(glfwWindowShouldClose(window) == GL_TRUE){
				running = false;
			}
		}
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		int error=glGetError();
		if(error!=GL_NO_ERROR){
			System.err.println("Error"+error);
		}
		glfwSwapBuffers(window);
	}
	
	private void update() {
		glfwPollEvents();
		if(Input.keys[GLFW_KEY_SPACE]){
			System.out.println("flap");
		}
	}
	public static void main(String[]  args) {
		new Main().start();
	}
}