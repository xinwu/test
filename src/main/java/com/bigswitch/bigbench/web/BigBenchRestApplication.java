package com.bigswitch.bigbench.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class BigBenchRestApplication extends Application {
    @Override
    public Restlet createInboundRoot() {
        Router baseRouter = new Router(this.getContext());
        baseRouter.attach("/bigbench/configuration", ConfigurationResource.class);
        return baseRouter;
    }
}
