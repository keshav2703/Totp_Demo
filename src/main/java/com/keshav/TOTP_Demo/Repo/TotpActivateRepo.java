package com.keshav.TOTP_Demo.Repo;

import com.keshav.TOTP_Demo.Model.TotpActivateJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotpActivateRepo extends JpaRepository<TotpActivateJPA, String> {
}
