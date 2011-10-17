package rpc.server;

import org.apache.xmlrpc.server.*;
import org.apache.xmlrpc.webserver.*;

public class RpcServer {
	
	public static void main(String []args){
		try{
			int port = Integer.parseInt(args[0]);
			// start the webserver
			WebServer webServer = new WebServer(port);
			
			// get the xmlrpc server stream
			XmlRpcServer xmlServer = webServer.getXmlRpcServer();
			
			// define our class definitions. for us, just the one class for finding the average
			PropertyHandlerMapping properties = new PropertyHandlerMapping();
			properties.addHandler("Worker", rpc.server.Worker.class);
			
			// set our handler mappings to the contents of our property handling mapping object
			xmlServer.setHandlerMapping(properties);
			
			// configure the server to allow the passing of serializable classes
			XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlServer.getConfig();
			config.setEnabledForExtensions(true);
			
			// start the webserver 
			webServer.start();
			System.out.println("RPC Server listening on port: "+port);
		}catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
	
	}
}
