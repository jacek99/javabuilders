package org.javabuilders.fx.handler;

import org.apache.commons.lang.NotImplementedException;
import org.javabuilders.BuildResult;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.IBackgroundProcessingHandler;

import java.lang.reflect.Method;

/**
 * Common handler for processing long running events on a background thread
 */
public class FXBackgroundProcessingHandler implements IBackgroundProcessingHandler{
    @Override
    public void doInBackground(BuildResult result, Object target, Method method, BackgroundEvent event, IBackgroundCallback callbackWhenFinished) throws Exception {
        throw  new NotImplementedException("Not implemented yet");
    }
}
