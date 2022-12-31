package com.tasc.apigw.model;

import com.tasc.apigw.security.UserDetailExtend;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.ArrayList;

public class TascUserAuthentication extends UsernamePasswordAuthenticationToken {

    public TascUserAuthentication(UserDetailExtend userDetailExtend) {
        super(userDetailExtend, null, new ArrayList<>());
    }
}
