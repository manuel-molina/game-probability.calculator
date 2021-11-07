package uy.com.banca.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uy.com.banca.domain.GameBetNode;

import java.util.List;

@Repository
public interface GameBetRepository extends MongoRepository<GameBetNode, String> {
    List<GameBetNode> findByProbabilityToWinLessThanEqual(Double probability);

    List<GameBetNode> findByProbabilityToLoseLessThanEqual(Double probability);
}