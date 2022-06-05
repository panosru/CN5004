package com.cn5004ap.payroll.common;

import com.cn5004ap.payroll.persistence.UserEntity;
import com.cn5004ap.payroll.persistence.UserRepository;

import java.util.List;

public class Demo
{
    public static void run()
    {
        /*
        String password = "1234";
        String hash = Utils.hashString(password);

        String password2 = "P@n0$K0$m!d!$87";
        String hash2 = Utils.hashString(password2);

        String password3 = "P@n0$K0$m!d!$87";
        String hash3 = Utils.hashString(password3);

        System.out.println(hash);
        System.out.println(hash2);
        System.out.println(hash3);

        System.out.println(Utils.verifyHash(password, UserModel.DefaultPasswordHash));
        System.out.println(Utils.verifyHash(password2, hash2));
        System.out.println(Utils.verifyHash(password3, hash3));
        System.out.println(Utils.verifyHash("P@n0$K0$m!d!$86", hash3));

        System.exit(0);


         */

        UserRepository userRepository = Multiton.getInstance(UserRepository.class);

        UserEntity  user  = new UserEntity("Ramesh", "Fadatare", "rameshfadatare@javaguides.com");
        UserEntity  user1 = new UserEntity("John", "Cena", "john@javaguides.com");

        userRepository.save(user);
        userRepository.save(user1);

        List<UserEntity> users = userRepository.findAll();
        //users.forEach(s -> System.out.println(s.getFirstName()));

        UserEntity       user2 = userRepository.findById(user.getId());
        userRepository.remove(user2);

        UserEntity       user3 = userRepository.getUserByEmail("john@javaguides.com");
        user3.setFirstName("Panagiotis");
        userRepository.save(user3);

        UserEntity       user4 = userRepository.getUserByEmail("john@javaguides.com");


        List<UserEntity> users2 = userRepository.findAll();

        System.out.println(user4);

        System.out.println();
        System.exit(0);
    }
}
