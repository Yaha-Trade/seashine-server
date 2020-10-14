package com.seashine.server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seashine.server.domain.ShowRoom;
import com.seashine.server.services.ShowRoomService;

@RestController
@RequestMapping(value = "/showrooms")
public class ShowRoomResource {

	@Autowired
	private ShowRoomService showRoomService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ShowRoom>> findAll() {
		List<ShowRoom> showRooms = showRoomService.findAll();

		return ResponseEntity.ok().body(showRooms);
	}

}
