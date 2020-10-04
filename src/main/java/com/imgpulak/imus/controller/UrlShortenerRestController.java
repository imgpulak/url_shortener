package com.imgpulak.imus.controller;

import com.imgpulak.imus.dto.ShortenedUrlDto;
import com.imgpulak.imus.request.model.CreateShortURLRequest;
import com.imgpulak.imus.service.URLShortenerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class UrlShortenerRestController {
  private final static Logger LOG = LoggerFactory.getLogger(UrlShortenerRestController.class);
  private final URLShortenerService urlShortenerService;

  @Autowired
  public UrlShortenerRestController(URLShortenerService urlShortenerService) {
    this.urlShortenerService = urlShortenerService;
  }

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Pong!";
  }

  @RequestMapping(value = "/{shortUrlId}", method = RequestMethod.GET)
  void redirectToFullUrl(@PathVariable String shortUrlId, HttpServletResponse response) throws IOException {
    LOG.debug("Received request to redirect to '{}'", shortUrlId);
    Optional<ShortenedUrlDto> shortenedUrlDtoFound = urlShortenerService.findURL(shortUrlId);
    if (shortenedUrlDtoFound.isPresent()) {
      shortenedUrlDtoFound.get().setHitCount(shortenedUrlDtoFound.get().getHitCount() + 1); // +1 while getting hit
      LOG.info("Redirecting to {}...", shortenedUrlDtoFound.get().getUrl().toString());
      response.sendRedirect(shortenedUrlDtoFound.get().getUrl().toString());
    } else {
      LOG.error("The id '{}' was not found", shortUrlId);
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> createShortUrl(@RequestBody CreateShortURLRequest payload, HttpServletRequest request) {
    LOG.debug("URL shorting API has been called.");
    try {
      String clientId = payload.getClientId();
      String urlStr = payload.getUrlStr();
      ShortenedUrlDto shortenedUrlDto = new ShortenedUrlDto();
      shortenedUrlDto.setClientId(clientId);
      URL url = URI.create(urlStr).toURL();
      shortenedUrlDto.setUrl(url);
      String shortenedUrlId = urlShortenerService.shorten(shortenedUrlDto);
      String shortenedUrl = request.getRequestURL() + shortenedUrlId;
      return new ResponseEntity<String>(shortenedUrl, HttpStatus.CREATED);
    } catch (IllegalArgumentException | MalformedURLException ex) {
      LOG.error("Error when trying to create a shortened URL for {}", ex.getMessage());
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/hitCount/{shortUrlId}", method = RequestMethod.GET)
  ResponseEntity<String> getHitCount(@PathVariable String shortUrlId, HttpServletRequest request) throws IOException {
    try {
      LOG.debug("Received request to find hit count for '{}'", shortUrlId);
      Optional<ShortenedUrlDto> shortenedUrlDtoFound = urlShortenerService.findURL(shortUrlId);
      if (shortenedUrlDtoFound.isPresent()) {
        return new ResponseEntity<String>(shortenedUrlDtoFound.get().getHitCount().toString(), HttpStatus.OK);
      } else {
        String msg = "The is '" + shortUrlId + ", was not found";
        LOG.error(msg);
        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
      }
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
