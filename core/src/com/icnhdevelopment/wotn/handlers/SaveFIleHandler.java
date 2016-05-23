package com.icnhdevelopment.wotn.handlers;

import com.icnhdevelopment.wotn.world.World;

/**
 * Created by kyle on 5/20/16.
 */
public class SaveFIleHandler {

	public static void serialize(World w, File file){
		try {
            		FileOutputStream fileOut = new FileOutputStream(file);
            		ObjectOutputStream out = new ObjectOutputStream(fileOut);
            		out.writeObject(w);
            		out.close();
            		fileOut.close();
        	}
        	catch(IOException i) {
            		i.printStackTrace();
        	}
	}
	
	public static World deserialize(File file){
		World w = null;
        	try {
            		FileInputStream fileIn = new FileInputStream(file);
            		ObjectInputStream in = new ObjectInputStream(fileIn);
            		w = (World) in.readObject();
            		in.close();
            		fileIn.close();
            		return w;
        	}
        	catch(IOException i) {
            		i.printStackTrace();
            		return null;
        	}
        	catch(ClassNotFoundException c) {
            		System.out.println("World class not found");
            		c.printStackTrace();
            		return null;
        	}
	}
}
