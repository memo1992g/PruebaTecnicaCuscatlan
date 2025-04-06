package backend.apiscart.controller;

import java.util.Collection;

import backend.apiscart.dao.IUsuariosDao;
import backend.apiscart.dto.base.GwTokenRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.apiscart.configuration.security.JwtTokenProvider;
import backend.apiscart.dto.base.AuthRequestDto;
import backend.apiscart.dto.base.AuthResponseDto;

import backend.apiscart.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private IUsuariosDao userDao;

    @Value("${jwt.user}")
    private String userName;

    @Value("${jwt.pass}")
    private String password;

    @PostMapping("/getToken")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto authRequest) {

        Integer userario = userDao.cantidad(authRequest.getUsername(), authRequest.getPassword());

        String cc = "";

        if(userario == 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Usuario no autorizado.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
        );

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String token = tokenProvider.generateToken(userName, authorities);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/gwToken")
    public ResponseEntity<?> renewToken(@RequestBody GwTokenRequestDto renewTokenRequest) {
        String token = renewTokenRequest.getToken();
        if (tokenProvider.validateToken(token)) {
            String dToken = tokenProvider.wToken(token);
            return ResponseEntity.ok(new AuthResponseDto(dToken));
        } else {
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }

}
