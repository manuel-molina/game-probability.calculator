package uy.com.banca.domain;

import java.util.List;

/*
  [
    {player: 1, race: 1, bet: 1},
    {player: 3, race: 2, bet: 1},
    ...
    {player: N, race: m, bet: 0},
  ]
*/

public class PlyerProbability {
  private List<PlayerBet> bets;

  private Integer totalGames;

  public PlyerProbability() {
  }

  public List<PlayerBet> getBets() {
    return bets;
  }

  public void setBets(List<PlayerBet> bets) {
    this.bets = bets;
  }

  public Integer getTotalGames() {
    return totalGames;
  }

  public void setTotalGames(Integer totalGames) {
    this.totalGames = totalGames;
  }
}
