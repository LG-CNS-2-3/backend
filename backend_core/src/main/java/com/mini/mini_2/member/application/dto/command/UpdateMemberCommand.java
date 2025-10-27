package com.mini.mini_2.member.application.dto.command;

public record UpdateMemberCommand(
    String email,      
    String nickname,
    String password
) {}
