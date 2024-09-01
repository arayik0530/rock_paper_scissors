package com.nobel.rock_paper_scissors.model;

public record PlayResponse(Move playerMove, Move computerMove, Outcome outcome) {}
