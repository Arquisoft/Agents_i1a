package controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChangePassword {

    String changeGet();
    String changePost(Model model
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "kind") String kind
            , @RequestParam(value = "old") String old
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "password2") String password2);

}
