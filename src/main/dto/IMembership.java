package main.dto;

import main.enums.PermissionLevel;

public interface IMembership {
	void addMember(User user);
	void addMember(User user, PermissionLevel pl);
	void removeMember(User user);
	PermissionLevel getPermissionLevel(User user);
}
