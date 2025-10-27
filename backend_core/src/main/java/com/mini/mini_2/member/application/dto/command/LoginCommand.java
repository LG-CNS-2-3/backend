package com.mini.mini_2.member.application.dto.command;

public record LoginCommand(
    String email,
    String password
) {}
