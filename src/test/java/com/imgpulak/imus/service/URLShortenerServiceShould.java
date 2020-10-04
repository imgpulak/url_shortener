package com.imgpulak.imus.service;


import com.imgpulak.imus.dto.ShortenedUrlDto;
import com.imgpulak.imus.interfaces.IdGenerator;

import com.imgpulak.imus.interfaces.ShortenedUrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class URLShortenerServiceShould {
  private static final String CLIENT_ID = "test";
  private static final URI OSOCO_URI = URI.create("http://www.osoco.es");
  public static final String OSOCO_URL_MURMUR3_HASH = "339d3b53";
  private URLShortenerService urlShortenerService;
  @Mock private ShortenedUrlRepository shortenedUrlRepository;
  @Mock private IdGenerator idGenerator;

  @Before
  public void setUp() {
    urlShortenerService = new URLShortenerService(shortenedUrlRepository, idGenerator);
  }

  /*
  @Test public void
  create_one_short_url_from_valid_url() throws MalformedURLException {
    given(idGenerator.generateId(OSOCO_URI.toString())).willReturn(OSOCO_URL_MURMUR3_HASH);
    ShortenedUrlDto shortenedUrlDto = new ShortenedUrlDto();
    shortenedUrlDto.setClientId(CLIENT_ID);
    shortenedUrlDto.setUrl(OSOCO_URI.toURL());
    given(shortenedUrlRepository.findURL((OSOCO_URL_MURMUR3_HASH))).willReturn(Optional.of(shortenedUrlDto));
    urlShortenerService.shorten(shortenedUrlDto);
    InOrder inOrder = inOrder(idGenerator, shortenedUrlRepository);
    inOrder.verify(idGenerator).generateId(OSOCO_URI.toString());
    inOrder.verify(shortenedUrlRepository).save(OSOCO_URL_MURMUR3_HASH, shortenedUrlDto);
  }*/

  @Test public void
  return_nothing_if_id_does_not_exist() throws MalformedURLException {
    ShortenedUrlDto shortenedUrlDto = new ShortenedUrlDto();
    shortenedUrlDto.setUrl(OSOCO_URI.toURL());
    shortenedUrlDto.setClientId(CLIENT_ID);
    given(shortenedUrlRepository.findURL((OSOCO_URL_MURMUR3_HASH))).willReturn(Optional.of(shortenedUrlDto));
    urlShortenerService.findURL(OSOCO_URL_MURMUR3_HASH);
    verify(idGenerator, never()).generateId(anyString());
    verify(shortenedUrlRepository).findURL(OSOCO_URL_MURMUR3_HASH);
  }
}
