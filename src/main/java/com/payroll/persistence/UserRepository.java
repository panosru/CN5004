package com.payroll.persistence;

public class UserRepository
    extends BaseRepository<UserEntity>
{
    public UserEntity getUserByEmail(String email)
    {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", persistentClass)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    public UserEntity getUserByUsername(String username)
    {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", persistentClass)
                 .setParameter("username", username)
                 .getSingleResult();
    }

    public void updateToken(String token)
    {

    }
}
