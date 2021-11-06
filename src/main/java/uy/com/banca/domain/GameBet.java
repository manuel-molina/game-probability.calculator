package uy.com.banca.domain;

import java.util.List;

public class GameBet {
  private List<List<Float>> bets;

  public GameBet() {
  }

  /*
  [
    [J1C1p, J1C2p, ..., J1Cnp],
    [J2C1p, J2C2p, ..., J2Cnp],
    ...
  ]
   */
  public List<List<Float>> getBets() {
    return bets;
  }

  public void setBets(List<List<Float>> bets) {
    this.bets = bets;
  }
}
