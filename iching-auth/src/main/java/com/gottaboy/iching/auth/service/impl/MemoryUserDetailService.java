//package com.gottaboy.iching.auth.service.impl;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsPasswordService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//@Service
//public class MemoryUserDetailService implements UserDetailsManager, UserDetailsPasswordService {
//
//    protected final Log logger = LogFactory.getLog(getClass());
//    private final Map<String, MutableUserDetails> users = new HashMap<>();
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Object object = users.get(username);
//        if(Objects.nonNull(object)){
//
//        } else {
//            throw new UsernameNotFoundException("");
//        }
//    }
//
//    @Override
//    public UserDetails updatePassword(UserDetails user, String newPassword) {
//        return null;
//    }
//
//    @Override
//    public void createUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void deleteUser(String username) {
//
//    }
//
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        return false;
//    }
//}
