package com.imgpulak.imus.service;


import com.imgpulak.imus.dto.ShortenedUrlDto;
import com.imgpulak.imus.interfaces.IdGenerator;

import com.imgpulak.imus.interfaces.ShortenedUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public final class URLShortenerService {
  private final ShortenedUrlRepository shortenedUrlRepository;
  private final IdGenerator idGenerator;

  @Autowired
  public URLShortenerService(final ShortenedUrlRepository shortenedUrlRepository, IdGenerator idGenerator) {
    this.shortenedUrlRepository = shortenedUrlRepository;
    this.idGenerator = idGenerator;
  }

  public String shorten(ShortenedUrlDto shortenedUrlDto) throws MalformedURLException {
    String clientId = shortenedUrlDto.getClientId();
    URL urlToBeShortened = shortenedUrlDto.getUrl();
    // <clientId>:<URL> is getting used to generate ID
    String text = clientId + ":" + urlToBeShortened.toString();
    String shortenedId = this.idGenerator.generateId(text);
    Optional<ShortenedUrlDto> shortenedUrlDtoOptional = findURL(shortenedId);
    if( !shortenedUrlDtoOptional.isPresent() ) {
      shortenedUrlDto.setShortenedId(shortenedId);
      shortenedUrlDto.setHitCount(0L); // Setting 0 for while getting generated
      shortenedUrlRepository.save(shortenedId, shortenedUrlDto);
    }
    return shortenedId;
  }

  public Optional<ShortenedUrlDto> findURL(String shortUrlId) {
     return shortenedUrlRepository.findURL(shortUrlId);
  }
}
