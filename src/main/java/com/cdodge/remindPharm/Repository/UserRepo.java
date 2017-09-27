package com.cdodge.remindPharm.Repository;

import com.cdodge.remindPharm.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer>{
}
