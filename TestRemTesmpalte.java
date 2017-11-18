



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

@ThingworxPropertyDefinitions(properties = {	
		@ThingworxPropertyDefinition(name="test1", description="", baseType="NUMBER",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="test2", description="", baseType="STRING",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="New", description="", baseType="STRING",
		aspects={"dataChangeType:VALUE",
                "dataChangeThreshold:0",
                "cacheTime:0", 
                "isPersistent:FALSE", 
                "isReadOnly:FALSE", 
                "pushType:ALWAYS", 
                "isFolded:FALSE",
                "defaultValue:wow"})
})



public class TestRemTesmpalte extends VirtualThing implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(TestRemTesmpalte.class);
	
	private final static String TEST2_FIELD = "test2";
	private final static String TEST1_FIELD = "test1";
	private Double test1;
	private String test2;
	
	private String thingName = null;
	
	public TestRemTesmpalte(String name, String description, ConnectedThingClient client) {
		super(name, description, client);
		thingName = name;
		// Populate the thing shape with the properties, services, and events that are annotated in this code
		super.initializeFromAnnotations();
		this.init();
	}
	
	// From the VirtualThing class
		// This method will get called when a connect or reconnect happens
		// Need to send the values when this happens
		// This is more important for a solution that does not send its properties on a regular basis
	public void synchronizeState() {
		// Be sure to call the base class
		super.synchronizeState();
		// Send the property values to ThingWorx when a synchronization is required
		super.syncProperties();
	}

	
	private void init() {
		
        //get
		try{
			test1 = getTest1();
		}catch(Exception ex){
			test1 = new Double(0);
			
		}
        if(test1 == null){
        	test1 = new Double(0);
        }
        try {
			setTest1();
		} catch (Exception ex) {
			LOG.error("Error " + thingName, ex);
		}
        try {
			this.setPropertyValue("test2", new StringPrimitive("WORK"));
		} catch (Exception ex) {
			LOG.error("Could not ser default value for test2", ex);
		}
	}
	
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
		//get
				try{
					test1 = getTest1();
					
				}catch(Exception ex){
					LOG.error("Error " + thingName, ex);
				}
		        if(test1 == null){
		        	test1 = new Double(0);
		        }
		        test1+=1;
		        LOG.info("Sending " + thingName + " double " + test1.doubleValue());
		        try {
					setTest1();
				} catch (Exception ex) {
					LOG.error("Error " + thingName, ex);
				}
		        
		        ValueCollection params = new ValueCollection();
		        params.SetStringValue("text", "text");
		        params.SetNumberValue("num1", 2.3);
		        params.SetNumberValue("num2", 3.4);
		        this.getClient().invokeService(ThingworxEntityTypes.Things, thingName, "TestSer", params, 1000);
		        
		        
	}
	
	
	public Double getTest1() {
		return (Double) getProperty(TEST1_FIELD).getValue().getValue();
	}

	public void setTest1() throws Exception {
		setProperty(TEST1_FIELD, new NumberPrimitive(this.test1));
	}
	
	public void setTest2() throws Exception {
		setProperty(TEST2_FIELD, new StringPrimitive(this.test2));
	}
	
	@ThingworxServiceDefinition(name="AddTwoNumbers", description="add operation")
	@ThingworxServiceResult(name="result", description="Result", baseType="NUMBER")
	public Double DeliveriesCalc( 
		@ThingworxServiceParameter( name="num1", description="Value 1", baseType="NUMBER") Double num1,
		@ThingworxServiceParameter( name="num2", description="Value 2", baseType="NUMBER") Double num2) throws Exception {
		LOG.info("Add two numbers: " + num1.doubleValue() + " and " + num2.doubleValue());
		
		return num1 + num2;
	}
	
}

