package by.boldysh.api.calculatedistance.controller;


import by.boldysh.api.calculatedistance.dto.AccountDto;
import by.boldysh.api.calculatedistance.security.UserDetailServiceImpl;
import by.boldysh.api.calculatedistance.security.utils.JwtUtils;
import by.boldysh.api.calculatedistance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("api/signin")
    public ResponseEntity<?> singIn(@RequestBody AccountDto accountDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(accountDto.getUserName(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailServiceImpl userDetails = (UserDetailServiceImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
    }


    @PostMapping("api/signup")
    public ResponseEntity<?> singUp(@RequestBody AccountDto accountDto) {
        if (accountService.existByLoginUser(accountDto.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
        }
        AccountDto savedUser = accountService.saveUser(accountDto);
        return ResponseEntity.ok().body(savedUser);
    }

    @PostMapping("api/signout")
    public ResponseEntity<?> singOut() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }


    @GetMapping("api/delete-user")
    public @ResponseBody AccountDto deleteUser(@RequestBody AccountDto accountDto) {
        return accountService.deleteUser(accountDto);
    }
}
