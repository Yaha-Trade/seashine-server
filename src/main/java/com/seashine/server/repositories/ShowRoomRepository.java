package com.seashine.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seashine.server.domain.ShowRoom;

@Repository
public interface ShowRoomRepository extends JpaRepository<ShowRoom, Integer> {

}
