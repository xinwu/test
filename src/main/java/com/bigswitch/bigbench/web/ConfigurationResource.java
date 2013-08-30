package com.bigswitch.bigbench.web;

import java.io.File; 
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory; 
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload; 
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;  
import org.restlet.resource.Get;
import org.restlet.resource.Post; 
import org.restlet.resource.ServerResource;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.bigswitch.bigbench.BigBench;
import com.bigswitch.bigbench.Configuration;
 

public class ConfigurationResource extends ServerResource  {
	
    @Get
    synchronized public FileRepresentation getCurrentConfiguration() {
    	File f = new File(BigBench.CURRENT_CONFIG_FILE);
    	
    	if(!f.exists()) {
    		ObjectMapper mapper = new ObjectMapper();
    		Configuration emptyConfiguration = new Configuration();
    		try{
    			mapper.writeValue(f, emptyConfiguration);
    		}catch (Exception e){
    			e.printStackTrace();
    		}
    		
    	}
    	
    	FileRepresentation rep = new FileRepresentation(f, MediaType.APPLICATION_JSON);
        setStatus(Status.SUCCESS_OK);
        return rep;
    }
    
    @Post
    /**
     * following is the curl query to post a json file
     * curl -v -v -v -X POST -H 'content-type: multipart/form-data' -F "file=@/home/bsn/work/bigbench/net/two-rack-event-1.json" http://127.0.0.1:8082/bigbench/configuration
     * */
    synchronized public void postDeltaConfiguration(Representation entity) {
    	try{
    		if (entity == null) {
    			String failure = "Post json Error 1: entity is null";
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST, failure);
                return;
            }
            
            if (!MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
            	String failure = "Post json Error 2: entity media type is not multipart/form-data";
            	setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, failure);
                return;
            }
            
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1000240);
            RestletFileUpload upload = new RestletFileUpload(factory); 
            List<FileItem> items = upload.parseRepresentation(entity);
            for (FileItem fi : items) {  
                if (fi.getFieldName().equals("file")) {
                	System.out.println(BigBench.DELTA_CONFIG_FILE);
                    File file = new File(BigBench.DELTA_CONFIG_FILE);
                    fi.write(file); 
                    break;
                }
            }    
            
            String success = "delta configuration successfully posted";
            setStatus(Status.SUCCESS_OK, success);
            return;
            
    	}catch (Exception e){
			e.printStackTrace();
			return;
		}
    }
}