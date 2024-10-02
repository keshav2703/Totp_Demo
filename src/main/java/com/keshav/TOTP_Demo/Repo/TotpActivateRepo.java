package com.keshav.TOTP_Demo.Repo;

import com.keshav.TOTP_Demo.Model.TotpActivateDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotpActivateRepo extends JpaRepository<TotpActivateDAO, String> {
}
