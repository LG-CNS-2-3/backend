package com.mini.mini_2.member.application.dto.command;

public record RegisterMemberCommand(
    String email,
    String password,
    String nickname
) {}
