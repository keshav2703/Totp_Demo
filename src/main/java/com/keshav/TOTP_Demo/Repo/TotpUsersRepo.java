package com.keshav.TOTP_Demo.Repo;

import com.keshav.TOTP_Demo.Model.TotpUsersDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotpUsersRepo extends JpaRepository<TotpUsersDAO, String> {
}
