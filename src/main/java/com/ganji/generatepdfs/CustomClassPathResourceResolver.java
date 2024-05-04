package com.ganji.generatepdfs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;

/**
 * Read resource file from class path for standalone java application.
 */
public class CustomClassPathResourceResolver implements ResourceResolver {

    private final ResourceResolver wrapped;

    public CustomClassPathResourceResolver() {
        this.wrapped = ResourceResolverFactory.createDefaultResourceResolver();
    }


    @Override
    public Resource getResource(URI uri) throws IOException {
        if (uri.getScheme().equals("classpath")) {
            URL url = getClass().getClassLoader().getResource(uri.getSchemeSpecificPart());
            assert url != null;
            return new Resource(url.openStream());
        }
        else {
            return wrapped.getResource(uri);
        }
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException {
        return wrapped.getOutputStream(uri);
    }

}