package service;

import com.cloudinary.Cloudinary;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import dto.UserDto;
import entity.User;
import util.CloudinaryUtil;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();
    private final Cloudinary cloudinary = CloudinaryUtil.getCloudinary();


    public List<UserDto> getAll() {
        List<User> list = userDao.getAll();
        List<UserDto> res = new ArrayList<>();
        for (User user : list) {
            res.add(new UserDto(user.getName(), user.getLastName(), user.getLogin(), user.getImage()));
        }
        return res;
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User getByLogin(String login) {
        Optional<User> wrap = userDao.getByLogin(login);
        return wrap.orElse(null);
    }

    public String saveImage(Part part) throws IOException {
        int available = part.getInputStream().available();
        byte[] bytes = new byte[available];
        part.getInputStream().read(bytes);
        cloudinary.uploader().upload(bytes, new HashMap<>());

        return (String) cloudinary.uploader().upload(bytes, new HashMap<>()).get("secure_url");
    }
}
