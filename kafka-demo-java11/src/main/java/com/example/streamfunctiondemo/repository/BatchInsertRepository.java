/**
 * ===============================================================
 * File name : BatchInsertService.java
 * Created by injeahwang on 2021-04-07
 * ===============================================================
 */
package com.example.streamfunctiondemo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Transactional
@Repository
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    public BatchInsertRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Consume되는 대량의 메시지를 Insert 처리하기 위해 JDBCTemplate이용.
    *  batch insert 처리.
    * */
    public void batchInsertByJdbcTemplate(List<Person> messages) throws Exception{
        String sql = " insert into person (id, age, gender, name, phone_number) values (nextval('hibernate_sequence'),?,?,?,?)".trim();

        DataSource datasource = jdbcTemplate.getDataSource();
        Connection connection = datasource.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement(sql);
        //200 row를 batch로 처리.
        int batchSize = 200;
        int count = 0;

        for(Person person : messages){
            ps.setInt(1, person.getAge());
            ps.setString(2,person.getGender());
            ps.setString(3,person.getName());
            ps.setString(4,person.getPhoneNumber());
            ps.addBatch();
            count +=1;
            if(count % batchSize==0 || count == messages.size()){
//                System.out.println(">> "+ps.toString());
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        connection.commit();
        connection.close();
        ps.close();
    }

}
