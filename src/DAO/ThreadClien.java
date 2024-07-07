/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.*;
import GUI.chat_gui_nth;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author binkyo
 */
public class ThreadClien extends Thread{
  private boolean run;
	private DataInputStream dis;
	private chat_gui_nth chat_gui_nth;

	public ThreadClien(chat_gui_nth chat_gui_nth,DataInputStream dis){
		run=true;
		this.chat_gui_nth=chat_gui_nth;
		this.dis=dis;

		this.start();
	}
	public void run(){
		String msg1,msg2;
		while(run){
			try {
				msg1=dis.readUTF();
				msg2=dis.readUTF();
				chat_gui_nth.getMSG(msg1,msg2);
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		try {
			dis.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	public void stopThread(){
		this.run=false;
	}  
}
