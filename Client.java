/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.collections.ValueCollection;
//import com.tt.thingworx.thing.TestRemTesmpalte;

public class Client extends ConnectedThingClient{

	
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);
 public static void main(String[] args){
     startConnection();
 }
	public Client(ClientConfigurator config) throws Exception {
		super(config);
	}
	
	public static void startConnection(){
		ClientConfigurator config = new ClientConfigurator();
		
		LOG.info("START");
		config.setUri("ws://127.0.0.1:8080/Thingworx/WS");
		config.setAppKey("1288056b-c45b-4d9e-9641-f51d5b973419");
		config.ignoreSSLErrors(true);
		try {
			Client client = new Client(config);
			
			
			client.start();
			
			while(!client.getEndpoint().isConnected()) {
				Thread.sleep(1000);
				LOG.info("WAIT");
			}
			ValueCollection params = new ValueCollection();
			
			//client.invokeService(ThingworxEntityTypes.Things, "Servis2", "test", params, 5000);
			
			TestRemTesmpalte thing1 = new TestRemTesmpalte("TestR2", "test conbection r1", client);
			
			client.bindThing(thing1);
			
			while(!client.isShutdown()) {
				// Loop over all the Virtual Things and process them
				if(client.isConnected()) {
					LOG.info("SEND");
					for(VirtualThing thing : client.getThings().values()) {
						try {
							thing.processScanRequest();
						}
						catch(Exception eProcessing) {
							System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
						}
					}
					LOG.info("SLEEP");
					Thread.sleep(5000);
				}
			}
			LOG.info("END");
			
			
		} catch (Exception e) {
			LOG.info("ERROR");
			e.printStackTrace();
		}
		
	}
	

}
