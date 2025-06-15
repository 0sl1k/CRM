package ua.glek.crm_adv.service;


import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import ua.glek.crm_adv.model.jpa.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private User user;

    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean banned;
    private LocalDateTime banEndDate;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public UserDetailsImpl(Long id, String username, String password, String email,boolean banned,LocalDateTime banEndDate, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.banned = banned;
        this.banEndDate = banEndDate;
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
                user.isBanned(),
                user.getBanEndDate(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        if (user == null || user.getRoles() == null) {
            return Collections.emptyList();
        }
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    public String getEmail (){
        return email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User getUser() {return user;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        if (banned){
            if (banEndDate != null && banEndDate.isBefore(LocalDateTime.now())){
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
