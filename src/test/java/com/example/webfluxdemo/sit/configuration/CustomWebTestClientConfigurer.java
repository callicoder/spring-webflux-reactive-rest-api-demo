package com.example.webfluxdemo.sit.configuration;

import com.example.webfluxdemo.domain.ObjectMapperConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.net.ssl.SSLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.ProxyProvider;
import reactor.netty.tcp.TcpClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebTestClientConfigurer {

    private static final Integer maxInMemorySizeInMb = 10;
    private static final Long    responseTimeout     = 1L;

    @Value("${connection.proxy.host:#{null}}")
    private String proxyHost;

    @Value("${connection.proxy.no_proxy:#{null}}")
    private String noProxy;

    @Value("${connection.proxy.port:#{null}}")
    private Integer proxyPort;

    @Value("${connection.timeout:#{null}}")
    private Integer timeout;

    private ExchangeStrategies getExchangeStrategies() {
        final ObjectMapper objectMapper = new ObjectMapperConfiguration(new ObjectMapper()).getObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return ExchangeStrategies
            .builder()
            .codecs(clientDefaultCodecs -> {
                clientDefaultCodecs
                    .defaultCodecs()
                    .jackson2JsonEncoder(
                        new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON));
                clientDefaultCodecs
                    .defaultCodecs()
                    .jackson2JsonDecoder(
                        new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON));
                clientDefaultCodecs
                    .defaultCodecs()
                    .maxInMemorySize(maxInMemorySizeInMb * 1024 * 1024);
            })
            .build();
    }

    private ReactorClientHttpConnector connector() throws SSLException {
        final SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();
        final Consumer<? super HttpHeaders> consumer = con -> con.add("connection", "keep-alive");
        final HttpClient httpClient = HttpClient
            .create()
            .keepAlive(true)
            .headers(consumer)
            .tcpConfiguration(this::getTcpConfiguration)
            .secure(t -> t.sslContext(sslContext));
        return new ReactorClientHttpConnector(httpClient);
    }

    private TcpClient getTcpConfiguration(final TcpClient client) {
        return Optional
            .ofNullable(proxyHost)
            .filter(result -> StringUtils.isNotBlank(proxyHost))
            .map(result -> client
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)))
                .proxy(proxy -> proxy
                    .type(ProxyProvider.Proxy.HTTP)
                    .host(proxyHost)
                    .port(proxyPort)
                    .nonProxyHosts(noProxy))
            )
            .orElseGet(() -> client
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)))
            );
    }

    @Bean
    @Qualifier("webTestClient")
    public WebTestClient webTestClient() throws SSLException {
        return WebTestClient
            .bindToServer(connector())
            .exchangeStrategies(getExchangeStrategies())
            .responseTimeout(Duration.ofMinutes(responseTimeout))
            .filter(logRequest())
            .build();
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {} {}", clientRequest.method(), clientRequest.url(), clientRequest.headers());
            return Mono.just(clientRequest);
        });
    }
}
