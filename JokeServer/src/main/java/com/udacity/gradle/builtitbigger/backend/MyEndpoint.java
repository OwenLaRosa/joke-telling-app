/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builtitbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builtitbigger.gradle.udacity.com",
                ownerName = "backend.builtitbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * Get a randomly selected joke
     * @return A (not) random Halloween joke
     */
    @ApiMethod(name = "joke")
    public MyBean getJoke() {
        MyBean response = new MyBean();
        response.setData("Why did the ghost go into the bar?\n\nFor the Boos!");

        return response;
    }

}