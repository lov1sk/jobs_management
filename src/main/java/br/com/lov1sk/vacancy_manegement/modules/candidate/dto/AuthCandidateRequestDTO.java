package br.com.lov1sk.vacancy_manegement.modules.candidate.dto;

// Entrada de dados em controllers e use cases podemos representar com o record
public record AuthCandidateRequestDTO(String username, String password) {}
