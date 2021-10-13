package com.example.webfluxdemo;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.repository.TweetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
    TweetRepository tweetRepository;

	@Test
	public void testCreateTweet() {
		Tweet tweet = new Tweet("This is a Test Tweet");

		webTestClient.post().uri("/tweets")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(tweet), Tweet.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.text").isEqualTo("This is a Test Tweet");
	}

	@Test
    public void testGetAllTweets() {
	    webTestClient.get().uri("/tweets")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Tweet.class);
    }

    @Test
    public void testGetSingleTweet() {
        Tweet tweet = tweetRepository.save(new Tweet("Hello, World!")).block();

        webTestClient.get()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateTweet() {
        Tweet tweet = tweetRepository.save(new Tweet("Initial Tweet")).block();

        Tweet newTweetData = new Tweet("Updated Tweet");

        webTestClient.put()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(newTweetData), Tweet.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.text").isEqualTo("Updated Tweet");
    }

    @Test
    public void testDeleteTweet() {
	    Tweet tweet = tweetRepository.save(new Tweet("To be deleted")).block();

	    webTestClient.delete()
                .uri("/tweets/{id}", Collections.singletonMap("id",  tweet.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}
