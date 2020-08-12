package org.jhipster.space.security;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;

import static io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN;

@Filter(patterns = {MATCH_ALL_PATTERN})
@Requires(env = Environment.HEROKU)
public class StrictTransportSecurityHeaderFilter extends OncePerRequestHttpServerFilter {

    private static final String STRICT_TRANSPORT_SECURITY_HEADER = "Strict-Transport-Security";

    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request, ServerFilterChain chain) {

        return Publishers.map(chain.proceed(request), mutableHttpResponse -> {
            mutableHttpResponse.header(STRICT_TRANSPORT_SECURITY_HEADER, "max-age=31536000; includeSubDomains");
            return mutableHttpResponse;
        });
    }
}
