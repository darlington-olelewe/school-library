package edu.schoollibrary.service;

public interface NumberCodec {
  String encode(long number);
  long decode(String encoded);
}
