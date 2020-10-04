package com.imgpulak.imus.utils;

import com.google.common.hash.Hashing;

import com.imgpulak.imus.interfaces.IdGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public final class Murmur3With32BitsHashGenerator implements IdGenerator {

  @Override
  public String generateId(String textToBeShortened) {
    return Hashing.murmur3_32().hashString(textToBeShortened.toString(), StandardCharsets.UTF_8).toString();
  }
}
