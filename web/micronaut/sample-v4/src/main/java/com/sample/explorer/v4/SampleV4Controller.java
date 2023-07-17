package com.sample.explorer.v4;

import io.micronaut.http.annotation.*;

@Controller("/sampleV4")
public class SampleV4Controller {

  @Get(uri = "/", produces = "text/plain")
  public String index() {
    return "Example Response";
  }
}
