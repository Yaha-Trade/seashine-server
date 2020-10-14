package com.seashine.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.ShowRoom;
import com.seashine.server.repositories.ShowRoomRepository;

@Service
public class ShowRoomService {

	@Autowired
	private ShowRoomRepository showRoomRepository;

	public List<ShowRoom> findAll() {
		return showRoomRepository.findAll();
	}

}
