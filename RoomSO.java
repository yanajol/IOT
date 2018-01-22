



import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;

import org.slf4j.Logger;

import com.thingworx.metadata.annotations.ThingworxEventDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinitions;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.primitives.DatetimePrimitive;
import com.thingworx.types.primitives.LocationPrimitive;
import com.thingworx.types.primitives.NumberPrimitive;
import com.thingworx.types.primitives.StringPrimitive;
import com.thingworx.types.primitives.structs.Location;
import com.thingworx.metadata.FieldDefinition;
import java.util.Random;

@ThingworxPropertyDefinitions(properties = {	
		@ThingworxPropertyDefinition(name="Temperature", description="", baseType="NUMBER",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
    @ThingworxPropertyDefinition(name="IsLightOn", description="", baseType="BOOLEAN",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
    @ThingworxPropertyDefinition(name="IsHeatingOn", description="", baseType="BOOLEAN",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="Humidity", description="", baseType="NUMBER",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		
})



public class RoomSO extends VirtualThing implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(RoomSO.class);
	
	private final static String TEST2_FIELD = "test2";
	private final static String TEST1_FIELD = "test1";
	private Double temperature,humidity;
	private Boolean light,heat;
	
	private String thingName = null;
	
	public RoomSO(String name, String description, ConnectedThingClient client) {
		super(name, description, client);
		thingName = name;
		// Populate the thing shape with the properties, services, and events that are annotated in this code
		super.initializeFromAnnotations();
		
	}
	
	// From the VirtualThing class
		// This method will get called when a connect or reconnect happens
		// Need to send the values when this happens
		// This is more important for a solution that does not send its properties on a regular basis
	/*public void synchronizeState() {
		// Be sure to call the base class
		super.synchronizeState();
		// Send the property values to ThingWorx when a synchronization is required
		super.syncProperties();
	}*/


	
	@Override
	public void run() {
		try {
			// Delay for a period to verify that the Shutdown service will return
			Thread.sleep(1000);
			// Shutdown the client
			this.getClient().shutdown();
		} catch (Exception ex) {
			LOG.error("Error " + thingName, ex);
		}
		
	}
	
	@Override
	public void processScanRequest() throws Exception {
            Random generator = new Random();
            
            
            
                        double temp = 25+(generator.nextDouble()*5)-2.5;
                        double hum = generator.nextDouble()*60;
                        boolean heat = generator.nextBoolean();
                        boolean light = generator.nextBoolean();
		        setProperty("Temperature", temp);
                       
		        
		        //params.SetNumberValue("Temperature", 10.5);
		        setProperty("Humidity", hum);
		      setProperty("IsLightOn", light);
                        setProperty("IsHeatingOn", heat);
                      //  Thread.sleep(5000);
		       this.updateSubscribedProperties(10000);
                        System.err.println("kkkkkkkkkkkkkssssssss");
		        
	}
	
}

