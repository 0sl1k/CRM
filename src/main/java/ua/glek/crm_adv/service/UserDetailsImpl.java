package ua.glek.crm_adv.service;


import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import ua.glek.crm_adv.model.jpa.User;
import ua.glek.crm_adv.repository.Jpa.UserRepo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private User user;
    private UserRepo userRepo;

    private Long id;
    private String username;
    private String password;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public UserDetailsImpl(Long id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = authorities;
    }

    public UserDetailsImpl(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.getRoles()==null) {
            return Collections.emptyList();
        }
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    public String getEmail (){
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {return user;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        if (user.isBanned()){
            if (user.getBanEndDate()!=null && user.getBanEndDate().isBefore(LocalDateTime.now())){
                user.setBanned(false);
                user.setBanEndDate(null);
                return true;
            }
            throw new LockedException("User is locked" + user.getBanEndDate());
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
