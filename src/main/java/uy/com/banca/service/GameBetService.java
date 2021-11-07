package uy.com.banca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.banca.domain.GameBetNode;
import uy.com.banca.repository.GameBetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameBetService {
    @Autowired
    GameBetRepository gameBetNodeRepo;

    /*Getting a specific gameBetNode by gameBetNode id from collection*/
    public GameBetNode getById(String gameBetId) {
        Optional<GameBetNode> gameBetNode = gameBetNodeRepo.findById(gameBetId);
        return gameBetNode.orElse(null);

    }

    public List<GameBetNode> getAllGameBetNodes() {
        return gameBetNodeRepo.findAll();
    }

    /*Getting a specific gameBetNode by category from collection*/
    public List<GameBetNode> getByProbabilityToWinLessThanEqual(Double probabilityToWin) {
        return gameBetNodeRepo.findByProbabilityToWinLessThanEqual(probabilityToWin);
    }

    /*Getting a specific gameBetNode by category from collection*/
    public List<GameBetNode> getByProbabilityToLoseLessThanEqual(Double probabilityToLose) {
        return gameBetNodeRepo.findByProbabilityToLoseLessThanEqual(probabilityToLose);
    }

    /*Adding/inserting an gameBetNode into collection*/
    public GameBetNode addGameBetNode(Double percent, Long races) {
        GameBetNode gameBetNode = new GameBetNode(percent, races);
        gameBetNode.generateChildRaceBets();

        return gameBetNodeRepo.save(gameBetNode);
    }

    /*delete an gameBetNode from collection*/
    public int deleteById(String nodeId) {
        Optional<GameBetNode> gameBetNode = gameBetNodeRepo.findById(nodeId);
        if (gameBetNode.isPresent()) {
            gameBetNodeRepo.delete(gameBetNode.get());
            return 1;
        }

        return -1;
    }
}