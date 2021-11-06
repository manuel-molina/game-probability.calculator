package uy.com.banca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class GameBetNode {
  public GameBetNode nodeWinner;
  public GameBetNode nodeLoser;

  // número de carreras
  @JsonIgnore
  private Integer raceCounter;
  // Numero de apuestas para ganar
  @JsonIgnore
  private Integer betsForWin;
  // Numero de apuestas para perder
  @JsonIgnore
  private Integer betsForLose;
  // Probabilidad de ganar n carreras
  private Double probabilitytoWin;
  // Probabilidad de ganar total - n carreras
  private Double probailityToLose;

  public GameBetNode(Double pWinBet, Integer raceCounter) {
    this.probabilitytoWin = pWinBet;
    this.probailityToLose = 1 - pWinBet;
    this.raceCounter = raceCounter;
    this.betsForWin = ((Double) (pWinBet * raceCounter.doubleValue())).intValue();
    this.betsForLose = raceCounter - this.betsForWin;
  }

  public GameBetNode(Double parentProbability, GameBetNode parent) {
    this.raceCounter = parent.getRaceCounter() - 1;
    this.betsForWin = parent.getBetsForWin() > 0 ? parent.getBetsForWin() - 1 : 0;
    this.betsForLose = this.raceCounter - this.betsForWin;

    Double winLevelProbability = parentProbability * (this.betsForWin / this.raceCounter.doubleValue());
    Double loseLevelProbability = parentProbability * (this.betsForLose / this.raceCounter.doubleValue());

    this.probabilitytoWin = new BigDecimal(String.valueOf(winLevelProbability)).setScale(5, BigDecimal.ROUND_FLOOR).doubleValue();
    this.probailityToLose = new BigDecimal(String.valueOf(loseLevelProbability)).setScale(5, BigDecimal.ROUND_FLOOR).doubleValue();
  }

  public void generateChildRaceBets() {
    if (this.raceCounter > 1) {
      // Winner levels
      this.nodeWinner = new GameBetNode(this.probabilitytoWin, this);
      this.nodeWinner.generateChildRaceBets();

      // Looser levels
      this.nodeLoser = new GameBetNode(this.probailityToLose, this);
      this.nodeLoser.generateChildRaceBets();
    }
  }

  public GameBetNode getNodeWinner() {
    return nodeWinner;
  }

  public void setNodeWinner(GameBetNode nodeWinner) {
    this.nodeWinner = nodeWinner;
  }

  public GameBetNode getNodeLoser() {
    return nodeLoser;
  }

  public void setNodeLoser(GameBetNode nodeLoser) {
    this.nodeLoser = nodeLoser;
  }

  public Integer getRaceCounter() {
    return raceCounter;
  }

  public void setRaceCounter(Integer raceCounter) {
    this.raceCounter = raceCounter;
  }

  public Integer getBetsForWin() {
    return betsForWin;
  }

  public void setBetsForWin(Integer betsForWin) {
    this.betsForWin = betsForWin;
  }

  public Integer getBetsForLose() {
    return betsForLose;
  }

  public void setBetsForLose(Integer betsForLose) {
    this.betsForLose = betsForLose;
  }

  public Double getProbabilitytoWin() {
    return probabilitytoWin;
  }

  public void setProbabilitytoWin(Double probabilitytoWin) {
    this.probabilitytoWin = probabilitytoWin;
  }

  public Double getProbailityToLose() {
    return probailityToLose;
  }

  public void setProbailityToLose(Double probailityToLose) {
    this.probailityToLose = probailityToLose;
  }
}