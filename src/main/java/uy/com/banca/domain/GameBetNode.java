package uy.com.banca.domain;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Document(collection = "gameBets")
@JsonPropertyOrder({"id", "raceCounter", "betsForWin", "probabilityToWin", "betsForLose", "probabilityToLose", "nodeWinner", "nodeLoser"})
public class GameBetNode {
    private static final Logger logger = Logger.getLogger(GameBetNode.class.getName());

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("ganador")
    public GameBetNode nodeWinner;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("perdedor")
    public GameBetNode nodeLoser;
    @Id
    @JsonIgnore
    private String id;
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
        logger.log(Level.INFO, "new GameBetNode -> parentProbability: " + pWinBet + " raceCounter: " + raceCounter);
        this.probabilityToWin = new BigDecimal(String.valueOf(pWinBet)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        this.probabilityToLose = new BigDecimal(String.valueOf(1 - pWinBet)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        this.raceCounter = raceCounter;

        this.betsForWin = ((Double) (pWinBet * raceCounter.doubleValue())).intValue();
        this.betsForLose = raceCounter - this.betsForWin;
    }

    public GameBetNode(Double parentProbability, Boolean isWinner, Long bets) {
        logger.log(Level.INFO, "new GameBetNode -> parentProbability: " + parentProbability + " isWinner: " + isWinner + " bets: " + bets);
        this.raceCounter = bets;
        if (bets == 1) {
            this.betsForWin = isWinner ? 1 : 0;
            this.betsForLose = isWinner ? 0 : 1;
        } else {
            if (isWinner) {
                this.betsForWin = new BigDecimal(String.valueOf(parentProbability * bets)).setScale(0, BigDecimal.ROUND_FLOOR).longValue();
                this.betsForWin = this.betsForWin == 0d ? 1 : this.betsForWin;
                this.betsForLose = this.raceCounter - this.betsForWin;
            } else {
                this.betsForLose = new BigDecimal(String.valueOf(parentProbability * bets)).setScale(0, BigDecimal.ROUND_FLOOR).longValue();
                this.betsForLose = this.betsForLose == 0d ? 1 : this.betsForLose;
                this.betsForWin = this.raceCounter - this.betsForLose;
            }
        }

        if (this.raceCounter != 0) {
            Double winLevelProbability = parentProbability * (this.betsForWin / this.raceCounter.doubleValue());
            Double loseLevelProbability = parentProbability * (this.betsForLose / this.raceCounter.doubleValue());

            this.probabilityToWin = new BigDecimal(String.valueOf(winLevelProbability)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
            this.probabilityToLose = new BigDecimal(String.valueOf(loseLevelProbability)).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
        } else {
            this.probabilityToWin = 0d;
            this.probabilityToLose = 0d;
        }
    }

    public void generateChildRaceBets() {
        logger.log(Level.INFO, "generateChildRaceBets -> raceCounter: " + raceCounter + " betsForWin: " + betsForWin + " betsForLose: " + betsForLose);
        if (this.raceCounter > 1) {
            // Winner levels
            if (this.betsForWin > 0) {
                this.nodeWinner = new GameBetNode(this.probabilityToWin, true, this.betsForWin);
                this.nodeWinner.generateChildRaceBets();
            }

            // Looser levels
            if (this.betsForLose > 0) {
                this.nodeLoser = new GameBetNode(this.probabilityToLose, false, this.betsForLose);
                this.nodeLoser.generateChildRaceBets();
            }
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

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "###0.0000")
    public Double getProbabilityToWin() {
        return probabilityToWin;
    }

    public void setProbabilityToWin(Double probabilityToWin) {
        this.probabilityToWin = probabilityToWin;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "###0.0000")
    public Double getProbabilityToLose() {
        return probabilityToLose;
    }

    public void setProbabilityToLose(Double probabilityToLose) {
        this.probabilityToLose = probabilityToLose;
    }
}