package org.jhipster.space.config;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.HttpServerConfiguration;
import io.micronaut.http.server.util.DefaultHttpHostResolver;
import io.micronaut.runtime.server.EmbeddedServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Singleton;

@Replaces(DefaultHttpHostResolver.class)
@Singleton
@Requires(env = Environment.HEROKU)
public class SSLEnforcingHostResolver extends DefaultHttpHostResolver {

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public SSLEnforcingHostResolver(HttpServerConfiguration serverConfiguration, Provider<EmbeddedServer> embeddedServer) {
        super(serverConfiguration, embeddedServer);
    }

    @Nonnull
    @Override
    public String resolve(@Nullable HttpRequest request) {
        String host = super.resolve(request);
        if (host.startsWith(HTTP)) {
            return host.replace(HTTP, HTTPS);
        }
        return host;
    }
}
