package rockScissorsPaper;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameFunctions {
	
	private static Integer numberOfPlayers = 0;
	private static ArrayList<GameLogicAndData> games = new ArrayList<GameLogicAndData>();
	private static Integer numberOfGames = games.size();
	private GameLogicAndData currentGame = new GameLogicAndData(numberOfGames.toString());
	
	
	@GetMapping("/start-game")
	public String setupGame(String opponent) {
		
		String playerID = numberOfPlayers.toString();
		String status;
		boolean computerOpponent = opponent.equals("computer");
		
		if (computerOpponent) {
			currentGame = new GameLogicAndData("1");
		}
		
		currentGame.addPlayer(playerID, computerOpponent);
		numberOfPlayers++;
		
		status = currentGame.status(playerID);
		
		if (currentGame.gameStatus >= 2) {
//			currentGame = new GameLogicAndData("2");
		}
		
		return status;
	}

	@GetMapping("/status")
	public String getStatus(String gameID, String playerID) {

		return currentGame.status(playerID);
	}
		
	@PostMapping("/move")
	public String makeMove(String gameID, String playerID, String move) {
		
		System.out.println("Player ID: " + playerID + " move: " + move);
		
		currentGame.move(playerID, move);
		
		return currentGame.status(playerID);
	}
}
