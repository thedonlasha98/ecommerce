package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.service.projection.ExcelProjection;
import com.ecommerce.ecommerceWeb.service.projection.ExcelTransProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductAndUserId(String product, Long userId);

    Product findByIdAndUserId(Long id, Long userId);

    Product getProductById(Long id);

    List<Product> findAllByStatusOrderByIdDesc(String status);

    @Query(value = "select t.transCount,t.sumAmount,t.comAmount, h.prodCount, a.userCount" +
            "                      from (select nvl(count(t.id), 0) as transCount," +
            "                                   nvl(sum(t.amount), 0) as sumAmount," +
            "                                   nvl(sum(t.com_amount), 0) as comAmount" +
            "                              from ecom_products_transactions t" +
            "                             where trunc(t.inp_sysdate) = :date) t," +
            "                           (select nvl(count(distinct(h.product)), 0) as prodCount" +
            "                             from ecom_products_hist h" +
            "                             where (trunc(h.inp_sysdate) = :date and" +
            "                                   h.event in ('ADD', 'ADD_CURRENT'))) h," +
            "                           (select nvl(count(distinct(a.user_id)), 0) as userCount" +
            "                              from ecom_user_authorizations a" +
            "                             where trunc(a.auth_date) = :date) a",nativeQuery = true)
    ExcelProjection getExelQueryData(LocalDate date);

    @Query(value = "  select p.product as product" +
            "          , p.product_name as productName" +
            "          , t.amount as amount" +
            "          , u.email as buyerEmail" +
            "          , a.pin as buyerPin" +
            "   from ecom_products_transactions t," +
            "            ecom_products              p," +
            "            ecom_users                 u," +
            "            ecom_accounts              a" +
            " where t.product_id = p.id" +
            "     and t.from_acct_id = a.id" +
            "     and a.user_id = u.id" +
            "     and trunc(t.inp_sysdate) = :date - 1",nativeQuery = true)
    List<ExcelTransProjection> getTransactionToday(LocalDate date);

}
