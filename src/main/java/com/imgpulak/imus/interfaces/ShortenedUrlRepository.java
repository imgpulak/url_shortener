package com.imgpulak.imus.interfaces;

import com.imgpulak.imus.dto.ShortenedUrlDto;

import java.net.URL;
import java.util.Optional;

public interface ShortenedUrlRepository {
  void save(String shortenedId, ShortenedUrlDto urlDetailsDTO);
  Optional<ShortenedUrlDto> findURL(String shortenedId);
}
