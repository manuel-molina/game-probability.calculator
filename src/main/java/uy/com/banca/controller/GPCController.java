package uy.com.banca.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.banca.domain.GameBetNode;

@RestController
public class GPCController {

  @GetMapping(value = "/bets", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<GameBetNode> addRacePlayer(@RequestParam Double probability, @RequestParam Integer races) {
    GameBetNode gbn = new GameBetNode(probability, races);

    gbn.generateChildRaceBets();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");

    return new ResponseEntity<>(gbn, httpHeaders, HttpStatus.OK);
  }
}
