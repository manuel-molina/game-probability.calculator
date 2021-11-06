package uy.com.banca.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlayerBet {
  @Id
  private String player;
  private Integer bet;
  private Integer race;

  public PlayerBet() {
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public Integer getBet() {
    return bet;
  }

  public void setBet(Integer bet) {
    this.bet = bet;
  }

  public Integer getRace() {
    return race;
  }

  public void setRace(Integer race) {
    this.race = race;
  }
}

