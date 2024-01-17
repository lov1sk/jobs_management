package br.com.lov1sk.vacancy_manegement.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lov1sk.vacancy_manegement.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
  @Autowired
  private JWTCandidateProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    /**
     * Esse filtro só serve para rotas do tipo /candidate, portanto
     * devemos fazer a verificação
     */
    var isCandidateRoutes = request.getRequestURI().startsWith("/candidate");
    if(!isCandidateRoutes){
      // Como o next() do express, ele apenas passa a requisição adiante
      System.out.println("caiu no filtro candidate routes");
      filterChain.doFilter(request, response); 
      return ;
    }
    
    /**
     * Verificar se o header tem conteudo e se o header estiver vazio
     * passar a para o proximo.
     */
    String authHeader = request.getHeader("Authorization"); // Recupera o JWT do header
    if (authHeader == null){
      // Como o next() do express, ele apenas passa a requisição adiante
      System.out.println("caiu no filtro header");
      filterChain.doFilter(request, response); 
      return ;
    }
   
    /**
     * Verificar se o token JWT é valido!
     */
     var tokenDecoded = this.jwtProvider.validate(authHeader);
     if (tokenDecoded == null) {
        response.setStatus(401);
        return;
      }
     /**
      * Setar na requisição o subject do token JWT
      * 
      * candidateId = tokenSubject
      */
     request.setAttribute("candidateId", tokenDecoded.getSubject());




     /**
      * Setar roles para dentro do contexto de autenticação
      * Cada Role precisa obrigatoriamente ter o ROLE_
      * é tambem uma boa pratica definir todas as roles em caixa alta ex: CANDIDATE
      */
      var roles = tokenDecoded.getClaim("roles").asList(Object.class);
      var permissions = roles.stream()
                              .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                              .toList();
      UsernamePasswordAuthenticationToken auth = 
      new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(), null, permissions);
    
      SecurityContextHolder.getContext().setAuthentication(auth);
     
     // Como o next() do express, ele apenas passa a requisição adiante
     filterChain.doFilter(request, response); 
  }
}
