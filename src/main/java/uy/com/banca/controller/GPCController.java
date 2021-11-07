package uy.com.banca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.banca.domain.GameBetNode;
import uy.com.banca.service.GameBetService;

@RestController
public class GPCController {

    @Autowired
    GameBetService gameBetService;

    @PostMapping(value = "/bets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postRacePlayer(@RequestParam Double probability, @RequestParam Long races) {
        GameBetNode gbn = gameBetService.addGameBetNode(probability, races);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");

        return new ResponseEntity<>(gbn.getId(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/bets/{nodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GameBetNode> getRacePlayerByNodeId(@PathVariable String nodeId) {
        GameBetNode gbn = gameBetService.getById(nodeId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");

        if (gbn != null) {
            return new ResponseEntity<>(gbn, httpHeaders, HttpStatus.OK);
        }

        return new ResponseEntity<>(gbn, httpHeaders, HttpStatus.NOT_FOUND);
    }
}
