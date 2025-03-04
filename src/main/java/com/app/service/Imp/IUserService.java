package com.app.service.Imp;

import java.util.List;

import com.app.Dto.EditProfileDTO;
import com.app.Dto.UserRegisterDTO;
import com.app.model.User;

public interface IUserService {

    // --
    public void updateUser(User user);
    public void editProfileUser(EditProfileDTO editProfileDTO, Integer idUserAuthenticado);
    
    // usado importante
    public User register(UserRegisterDTO userDTO);

    public List<User> findAllUsers();
    public User findUserById(Integer id);
    public void deleteUserById(Integer id);

}
