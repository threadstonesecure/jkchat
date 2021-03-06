package com.jkchat.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.jkchat.models.ChatMessage;
import com.jkchat.models.MessageDTO;
import com.jkchat.models.UserMessages;
import com.jkchat.service.UserService;

/**
 * @author Jebil Kuruvila
 *
 */
@Controller
public class SocketBasedChatController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	UserService userService;

	/**
	 * @param mdto
	 */
	@MessageMapping("/recieveMessage")
	public void chatHandler(@Payload MessageDTO mdto) {
		UserMessages um = new UserMessages();
		ChatMessage cm = new ChatMessage();
		cm.setFrom(mdto.getFrom().trim());
		cm.setMessage(mdto.getMessage().trim());
		um.setuName(mdto.getTo().trim());
		um.setCm(cm);
		userService.saveMessagesToDB(um);
		userService.putMessage(mdto.getTo().toLowerCase(), cm);
		this.simpMessagingTemplate
				.convertAndSend("/messageQueue/" + mdto.getTo(), mdto);
	}

	/**
	 * @param accessor
	 * @param dest
	 */
	@SubscribeMapping("/queue/{dest}")
	public void testMapping(Principal accessor,
			@DestinationVariable String dest) {
		// String user = accessor.getName();
		// System.out.println("******** testMapping" + user + "****" + dest);
	}
}
