package com.nobel.rock_paper_scissors.model;

public record PlayRequest(Move playerMove, Move playerLastMove, Long gameId) {}
