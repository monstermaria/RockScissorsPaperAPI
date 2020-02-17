package rockScissorsPaper;

import java.util.Random;

/* game status:
 * 0 = initialized
 * 1 = player one added, waiting for second player
 * (2 = player two added)
 * 3 = awaiting moves
 * 4 = player one has made their move
 * 5 = player two has made their move
 * 6 = game finished
 */
public class GameLogicAndData {

	String gameID = "0", playerOneID = "0", playerTwoID = "1";
	String playerOneMove, playerTwoMove;
	int gameStatus = 0, playerOneScore = 0, playerTwoScore = 0, ties = 0;
	boolean computerOpponent = false;
	String[] moves = { "rock", "scissors", "paper" };
	Random random = new Random();
	
	public GameLogicAndData(String id) {
		
		gameID = id;
	}
	
	public void addPlayer(String playerID, boolean computerOpponent) {
		
		this.computerOpponent = computerOpponent;
		
		if (computerOpponent) {
			playerOneID = playerID;
			gameStatus = 3;
		} else {
			switch (gameStatus) {
			case 0:
				playerOneID = playerID;
				gameStatus = 1;
				break;
			case 1:
				playerTwoID = playerID;
				gameStatus = 3;
				break;
			default:
				break;
			}			
		}
	}
	
	public String status(String playerID) {
		
		int ownScore, otherScore;
		String pattern = "{ \"gameID\":\"%s\", \"playerID\":\"%s\", \"gameStatus\":\"%s\", \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
		String json;
		if (playerID.equals(playerOneID)) {
			ownScore = playerOneScore;
			otherScore = playerTwoScore;
		} else {
			ownScore = playerTwoScore;
			otherScore = playerOneScore;
		}
		
		json = String.format(pattern, gameID, playerID, gameStatus, ownScore, otherScore, ties);
		
		System.out.println(json);
		
		return json;
	}
	
	public void move(String playerID, String move) {
		
		if (gameStatus < 3) {
			return;
		}
		
		if (playerID.equals(playerOneID)) {
			if (playerOneMove == null) {
				playerOneMove = move;
			}
		} else {
			if (playerTwoMove == null) {
				playerTwoMove = move;
			}
		}
		
		if (computerOpponent) {
			
			
			playerTwoMove = moves[random.nextInt(3)];
			
			System.out.println("Computer chose " + playerTwoMove);
		}
		
		updateGameStatus();
	}
	
//	enum Move {
//		
//		rock, scissors, paper;
//		
//        public static String getRandomMove() {
//            Random random = new Random();
//            return values()[random.nextInt(values().length)].name();
//        }
//
//	}

	public void updateGameStatus() {
		
		if (playerOneMove == null && playerTwoMove == null) {
			gameStatus = 3;
			System.out.println("No player has made their move");
		} else if (playerOneMove != null && playerTwoMove != null) {
			checkResult();
			System.out.println("Both players have made their move");
		} else if (playerOneMove != null) {
			gameStatus = 4;
			System.out.println("Player 1 has made their move");
		} else if (playerTwoMove != null) {
			gameStatus = 5;
			System.out.println("Player 2 has made their move");
		}
//		return "Something is not right";
	}
	
	public void checkResult() {
		
        int playerOne = convertMoveStringToInt(playerOneMove);
        int playerTwo = convertMoveStringToInt(playerTwoMove);
        
        switch (winner(playerOne, playerTwo)) {
        case 0:
        	ties++;
            break;
        case 1:
        	playerOneScore++;
            break;
        case 2:
        	playerTwoScore++;
            break;
        case -1:
            System.out.println("Hoppsan! Nu har programmeraren gjort nåt fel...");
            break;
        default:
            System.out.println("Ojdå, det här ska inte kunna hända...");
        }
        
		playerOneMove = null;
		playerTwoMove = null;
		gameStatus = 3;
		
        if (playerOneScore + playerTwoScore >= 3) {
        	System.out.println("Game finished");
        	gameStatus = 6;
        }
	}
	
    int winner(int playerOneMove, int playerTwoMove) {
        
        int comparison = playerTwoMove - playerOneMove;

        switch (comparison) {
            case -2:
                return 1;
            case 1:
                return 1;
            case -1:
                return 2;
            case 2:
                return 2;
            case 0:
                return 0;
            default:
                System.out.println("Fel i metod winner");
        }

        return -1;
    }

	int convertMoveStringToInt(String move) {
		
		switch (move) {
		case "rock":
			return 1;
		case "scissors":
			return 2;
		case "paper":
			return 3;
		default:
			return -100;
		}
	}
}
