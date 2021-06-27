package com.ecommerce.web.ropository;

import com.ecommerce.web.domain.WebRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WebRequestRepository extends CrudRepository<WebRequest,Long> {

    @Query(value = "select * from ecom_web_requests wr where wr.ip_address = :ipAddress and wr.inp_sysdate = :date ",nativeQuery = true)
    WebRequest findByIpAddressAndInpSysdate(String ipAddress, LocalDate date);
}
