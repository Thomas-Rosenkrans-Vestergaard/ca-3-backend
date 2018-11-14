package com.group3.ca3.rest.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


//makes the filter discovereble to JAX-RS during runtime
//@Provider
//
//This annotation makes the filter apply globally, to all resources
@PreMatching


//this class, overrides the default behavior of the X-Http-Method-Override,
//Typically servers have firewalls that prevent a specific resource using PUT or DELETE
// but this overrides, the application tunnels, to prevent such behavior

public class HttpMethodOverride implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String methodOverride = requestContext.getHeaderString("X-Http-Method-Override");
        if(methodOverride != null) requestContext.setMethod(methodOverride);
    }
}
