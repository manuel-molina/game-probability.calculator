package uy.com.banca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Random;

@Document(collection = "gameBets")
@JsonPropertyOrder({"id", "raceCounter", "betsForWin", "probabilityToWin", "betsForLose", "probabilityToLose", "nodeWinner", "nodeLoser"})
public class GameBetNode {
    @Id
    @JsonIgnore
    private String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("ganador")
    public GameBetNode nodeWinner;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("perdedor")
    public GameBetNode nodeLoser;

    // número de carreras
    @JsonProperty("carreras")
    private Long raceCounter;
    // Numero de apuestas para ganar
    @JsonProperty("apuestasParaGanar")
    private long betsForWin;
    // Numero de apuestas para perder
    @JsonProperty("apuestasParaPerder")
    private long betsForLose;
    // Probabilidad de ganar n carreras
    @JsonProperty("probabilidadGanar")
    private Double probabilityToWin;
    // Probabilidad de ganar total - n carreras
    @JsonProperty("probabilidadPerder")
    private Double probabilityToLose;

    public GameBetNode() {
    }

    public GameBetNode(Double pWinBet, Long raceCounter) {
        this.probabilityToWin = new BigDecimal(String.valueOf(pWinBet)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        this.probabilityToLose = new BigDecimal(String.valueOf(1 - pWinBet)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        this.raceCounter = raceCounter;

        this.betsForWin = ((Double) (pWinBet * raceCounter.doubleValue())).intValue();
        this.betsForLose = raceCounter - this.betsForWin;
    }

    public GameBetNode(Double parentProbability, Long races, Long bets) {
        this.raceCounter = bets;
        if (bets == 1) {
            this.betsForWin = 1;
            this.betsForLose = 0;
        } else {
            this.betsForWin = Math.round(parentProbability * bets);
            this.betsForLose = this.raceCounter - this.betsForWin;
        }

        Double winLevelProbability = parentProbability * (this.betsForWin / this.raceCounter.doubleValue());
        Double loseLevelProbability = parentProbability * (this.betsForLose / this.raceCounter.doubleValue());

        this.probabilityToWin = new BigDecimal(String.valueOf(winLevelProbability)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        this.probabilityToLose = new BigDecimal(String.valueOf(loseLevelProbability)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public void generateChildRaceBets() {
        if (this.raceCounter > 1) {
            // Winner levels
            this.nodeWinner = new GameBetNode(this.probabilityToWin, this.raceCounter - 1, this.betsForWin);
            this.nodeWinner.generateChildRaceBets();

            // Looser levels
            this.nodeLoser = new GameBetNode(this.probabilityToLose, this.raceCounter, this.betsForLose);
            this.nodeLoser.generateChildRaceBets();
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(raceCounter)
              .append(" G[").append(betsForWin).append(": ").append(probabilityToWin)
              .append("] P[").append(betsForLose).append(": ").append(probabilityToLose).append("]");
        buffer.append('\n');
        if (nodeWinner != null) {
            nodeWinner.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        }
        if (nodeLoser != null) {
            nodeLoser.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getRaceCounter() {
        return raceCounter;
    }

    public void setRaceCounter(Long raceCounter) {
        this.raceCounter = raceCounter;
    }

    public Long getBetsForWin() {
        return betsForWin;
    }

    public void setBetsForWin(Integer betsForWin) {
        this.betsForWin = betsForWin;
    }

    public Long getBetsForLose() {
        return betsForLose;
    }

    public void setBetsForLose(Integer betsForLose) {
        this.betsForLose = betsForLose;
    }

    public Double getProbabilityToWin() {
        return probabilityToWin;
    }

    public void setProbabilityToWin(Double probabilityToWin) {
        this.probabilityToWin = probabilityToWin;
    }

    public Double getProbabilityToLose() {
        return probabilityToLose;
    }

    public void setProbabilityToLose(Double probabilityToLose) {
        this.probabilityToLose = probabilityToLose;
    }
}