package com.payroll.persistence;

public class UserRepository
    extends BaseRepository<UserEntity>
{
    public UserEntity getUserByEmail(String email)
    {
        return session.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public UserEntity getUserByUsername(String username)
    {
        return session.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public void updateToken(String token)
    {

    }
}
