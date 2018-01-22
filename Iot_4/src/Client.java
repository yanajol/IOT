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
		config.setAppKey("423bea10-0316-4efb-8bb4-8c80a17f1453");
		config.ignoreSSLErrors(true);
		try {
			Client client = new Client(config);
			
			
			client.start();
			
			while(!client.getEndpoint().isConnected()) {
				Thread.sleep(1000);
				LOG.info("WAIT");
			}
                        String[] room = new String[]{"a","b"};
                        for(String r: room){
                            ValueCollection p= new ValueCollection();
                            p.SetStringValue("RoomName", r);
                            client.invokeService(ThingworxEntityTypes.Things, "RoomCreator", "RoomCreator", p, 5000);
                        }
                         for(String r: room){
                             RoomSO th = new RoomSO(r, "", client);
                             client.bindThing(th);
                         }
			
			while(!client.isShutdown()) {
				// Loop over all the Virtual Things and process them
				if(client.isConnected()) {
					LOG.info("SEND");
					for(VirtualThing thing : client.getThings().values()) {
						try {
                                                    System.out.println(thing.getName()+"   dsadsadsadasdasdsadsadas");
							thing.processScanRequest();
						}
						catch(Exception eProcessing) {
							System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
						}
					}
					LOG.info("SLEEP");
					Thread.sleep(10000);
				}
			}
			LOG.info("END");
			
			
		} catch (Exception e) {
			LOG.info("ERROR");
			e.printStackTrace();
		}
		
	}
	

}
