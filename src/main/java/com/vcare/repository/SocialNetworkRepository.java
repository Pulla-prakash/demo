package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcare.beans.SocialNetworks;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetworks,Integer> {

}
