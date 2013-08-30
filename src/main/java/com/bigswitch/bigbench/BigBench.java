package com.bigswitch.bigbench;

import org.restlet.Component;
import org.restlet.data.Protocol;

import com.bigswitch.bigbench.web.BigBenchRestApplication;

public class BigBench 
{
	public static Component restComponent;
	public static String CURRENT_CONFIG_FILE = "/tmp/current-configuration.json";
	public static String DELTA_CONFIG_FILE = "/tmp/delta-configuration.json";
	
    public static void main( String[] args )
    {
        startRESTApplication();
    }
    
    private static void startRESTApplication() {
        restComponent = new Component();
        restComponent.getServers().add(Protocol.HTTP, 8082);
        restComponent.getDefaultHost().attach(new BigBenchRestApplication());
        try {
            restComponent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
