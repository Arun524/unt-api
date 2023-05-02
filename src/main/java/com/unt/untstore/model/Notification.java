package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends BaseModel{

	@Column(name = "SEND_TO")
	@Setter
	@Getter
	private String sendTo;
	@Column(name = "SUBJECT")
	@Setter
	@Getter
	private String subject;	
	@Column(name = "MESSAGE")
	@Setter
	@Getter
	private String message;
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	@Setter
	@Getter
	private NotificationStatus status;
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	@Setter
	@Getter
	private NotificationType type;
	@Column(name = "CHANEL")
	@Enumerated(EnumType.STRING)
	@Setter
	@Getter
	private NotificationChanel chanel;


}
