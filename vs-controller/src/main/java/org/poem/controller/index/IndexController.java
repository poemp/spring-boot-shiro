package org.poem.controller.index;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** @author poem */
@RestController
@RequestMapping("/v1/")
public class IndexController {

  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping("/index")
  @ResponseBody
  public String index() {
    logger.info("index index.");
    return "index index";
  }
}
