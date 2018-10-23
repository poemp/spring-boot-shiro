package org.poem.index;


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
