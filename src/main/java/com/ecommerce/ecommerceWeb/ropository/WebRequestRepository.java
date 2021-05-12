package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.WebRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WebRequestRepository extends CrudRepository<WebRequest,Long> {

    @Query(value = "select * from ecom_web_requests wr where wr.ip_address = :ipAddress and wr.request_form=:form and wr.inp_sysdate=:date ",nativeQuery = true)
    WebRequest findByIpAddressAndRequestFormAndTruncInpSysdate(String ipAddress, String form, LocalDate date);
}
