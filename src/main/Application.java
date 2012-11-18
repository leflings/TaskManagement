package main;

import java.util.concurrent.atomic.AtomicBoolean;

import main.dto.User;
import main.menu.WelcomeMenu;

public class Application {

	private static User authenticatedUser;
	private static final AtomicBoolean AUTHENTICATED = new AtomicBoolean(false);

	public static User User() {
		return authenticatedUser;
	}

	public static boolean isAuthenticated() {
		return AUTHENTICATED.get();
	}

	public static void setAuthenticatedUser(User user) {
		if (user != null) {
			authenticatedUser = user;
			AUTHENTICATED.set(true);
		}
	}

	public static void clearAuthenticatedUser() {
		authenticatedUser = null;
		AUTHENTICATED.set(false);
	}

	public Application() {
	}

	public void Run() {
		new WelcomeMenu().run();
	}

}
