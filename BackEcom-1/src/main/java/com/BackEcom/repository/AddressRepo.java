package com.BackEcom.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.Address;
public interface AddressRepo extends JpaRepository<Address, Long>{

}
