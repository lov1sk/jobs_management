package br.com.lov1sk.vacancy_manegement.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lov1sk.vacancy_manegement.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * SecurityFilter, filtro criado para recuperar do header Authorization
 * o token JWT
 * 
 * Este é como se fosse um middleware que permite ou não, acessarmos nossa aplicação
 * com base no jwt passado
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

  @Autowired
  private JWTCompanyProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization"); // Recupera o JWT do header

    /**
     * Esse filtro só serve para rotas do tipo /company, portanto
     * devemos fazer a verificação
     */
    var isCompanyRoutes = request.getRequestURI().startsWith("/company");
    if(!isCompanyRoutes){
      // Como o next() do express, ele apenas passa a requisição adiante
      filterChain.doFilter(request, response); 
      return ;
    }
     
    /**
     * Verificar se existe um header de autorização e se não houver
     * retornar.
     */
    if(authHeader == null) {   
      // Como o next() do express, ele apenas passa a requisição adiante
      filterChain.doFilter(request, response); 
      return ; 
    }
     
    /**
     * Validar o token
     * 
     * Se der ruim na validação, retorna um 401
     */
    var tokenDecoded = this.jwtProvider.validate(authHeader);
    if(tokenDecoded == null){
      response.setStatus(401);
      return;
    }

    /**
     * Se der certo a validação, retorna passa adiante a requisição com
     * a informação adicional do id da company
     */
    request.setAttribute("companyId", tokenDecoded.getSubject());
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


 