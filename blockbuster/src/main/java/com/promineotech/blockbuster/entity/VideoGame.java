package com.promineotech.blockbuster.entity;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder
public class VideoGame {
  private int videoGamePK;
  private String videoGameID;
  private String title;
  private BigDecimal price;
  private MediaType mediaType;
  private Console console;
  private Gameplay gameplay;
}
