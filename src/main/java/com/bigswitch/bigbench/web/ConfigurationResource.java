package com.bigswitch.bigbench.web;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ConfigurationResource extends ServerResource  {
    @Get
    public String getConfiguration() {
        return "Hello World";
    }
}