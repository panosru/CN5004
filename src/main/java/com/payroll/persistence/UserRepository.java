package com.payroll.persistence;

public class UserRepository
    extends BaseRepository<UserEntity>
{
    public UserEntity getUserByEmail(String email)
    {
        return findByX("email", email);
    }

    public UserEntity getUserByUsername(String username)
    {
        return findByX("username", username);
    }

    public void updateToken(String token)
    {

    }
}
