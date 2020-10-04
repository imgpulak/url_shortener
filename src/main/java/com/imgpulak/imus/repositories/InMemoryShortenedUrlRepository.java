package com.imgpulak.imus.repositories;

import com.imgpulak.imus.dto.ShortenedUrlDto;
import com.imgpulak.imus.interfaces.ShortenedUrlRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryShortenedUrlRepository implements ShortenedUrlRepository {
  private final static Logger LOG = LoggerFactory.getLogger(InMemoryShortenedUrlRepository.class);
  private Map<String, ShortenedUrlDto> shortenedUriStore = new ConcurrentHashMap<String, ShortenedUrlDto>();

  @Override
  public void save(String shortenedId, ShortenedUrlDto shortenedUrlDto) {
    shortenedUriStore.put(shortenedId, shortenedUrlDto);
    LOG.debug("Entry [{}, {}] successfully saved", shortenedId, shortenedUrlDto.getUrl().toString());
  }

  @Override
  public Optional<ShortenedUrlDto> findURL(String shortenedId) {
    return Optional.ofNullable(shortenedUriStore.get(shortenedId));
  }
}
